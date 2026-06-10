package com.hermes.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hermes.exception.exceptions.AtributosInvalidosException;
import com.hermes.exception.exceptions.ProductWithoutNameException;
import com.hermes.models.Atributo;
import com.hermes.models.Product;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Service
public class ProductValidator {
	
	private static final Set<String> UNIDADES_INTEIRAS = new HashSet<>(Arrays.asList(
		"UNID", "CX", "CX2", "CX3", "CX5", "CX10", "CX15", "CX20", "CX25",
		"CX50", "CX100", "PC", "PACOTE", "DUZIA", "CENTO", "MILHEI", "KIT", "CJ", "FD",
		"RESMA", "ROLO", "GF", "LATA", "FRASCO", "AMPOLA", "BISNAG", "BALDE", "VIDRO",
		"VASIL", "TAMBOR", "PALETE"
	));

	private static final Set<String> UNIDADES_FRACIONADAS = new HashSet<>(Arrays.asList(
		"KG", "G", "TON", "L", "ML", "M", "M2", "M3", "CM", "CM2"
	));

	private static final String MSG_UNIDADES_VALIDAS = String.join(", ", UNIDADES_INTEIRAS);

	private final ObjectMapper mapper;

	public ProductValidator(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	public Product validate(String nome, List<Atributo> atributos) {
		StringBuffer erros = new StringBuffer();
		
		Product produto = new Product();
		
		if(nome == null || nome.isBlank()) {
			erros.append("Sem nome.\n");
			throw new ProductWithoutNameException(erros.toString());
		}
		
		produto.setName(nome);
		
		atributos.forEach(atributo -> {
			String errorString = "Error: ";
			
			switch (atributo.getTipo()) {
			case "double":
				
				String texto = atributo.getValor().toString();
				

				if(texto == null || texto.isBlank()) {
					erros.append(errorString + "Inserir um valor.\n");
				}

				for(int i = 0; i < texto.length() ; i++) {
					if(Character.isDigit(texto.charAt(i))) {
						continue;	
					} else if((texto.charAt(i) == ',' || texto.charAt(i) == '.')) {
						
						if((texto.length() - 1 - i) > 2) {
							erros.append(errorString + "Inserir \".\" ou \",\" somente para casas decimais.\n");
						//verifica se a vírgula ou o ponto não são o ultimo caractere AND se falta um ou no máximo dois caracters para o fim da sequência e nega para falhar rápido
						}else if( !((texto.length() - 1 - i) > 0 && (texto.length() - 1 - i) <= 2)) {
							erros.append(errorString + "Inserir um valor com apenas duas casas decimais.\n");
						}else if((texto.charAt(i - 1) == '.' || texto.charAt(i - 1) == ',')) {
							erros.append(errorString + "Formato inválido.\n");
						} else {
							texto = texto.replace(texto.charAt(i - 1) + "," + texto.charAt(i + 1),
									texto.charAt(i - 1) + "." + texto.charAt(i + 1));
						}
					}else{
						erros.append(errorString + "Formato inválido.\n");
					}	
				}
				
				atributo.setValor(Double.valueOf(texto));
				produto.getAtributos().add(atributo);
				break;
			case "data":
				String data = atributo.getValor().toString();
				if(data == null || data.isBlank()) {
					erros.append(errorString + "Sem data.\n");
				}
				produto.getAtributos().add(atributo);
				break;	
			case "GTIN":
				String gtin = atributo.getValor().toString();
				int gtintamanho = gtin.length();
				boolean soNumero = true;
				
				
				
				if(gtin == null || gtin.isEmpty() || gtin.isBlank()) {
					erros.append(errorString + "Vazia.\n");
				} else if(gtintamanho != 8 && gtintamanho != 12 && gtintamanho != 13 && gtintamanho != 14) {
					erros.append(errorString + "Tamanho do GTIN inválido, deve ter: 8, 12, 13, 14 digitos.\n");
				}
				
				for(int k = 0; k < gtin.length(); k++) {
					if(Character.isDigit(gtin.charAt(k))) {
						continue;
					} else {
						soNumero = false;
						break;
					}
				}
				
				if(soNumero) {
					 	int soma = 0;
					    boolean multiplicaPorTres = true;

					    for (int j = gtin.length() - 2; j >= 0; j--) {
					        int digito = Character.getNumericValue(gtin.charAt(j));
					        soma += digito * (multiplicaPorTres ? 3 : 1);
					        multiplicaPorTres = !multiplicaPorTres;
					    }

					    int mod = soma % 10;
					    int checkDigitCalculado = (mod == 0) ? 0 : 10 - mod;

					    int checkDigitInformado = Character.getNumericValue(gtin.charAt(gtin.length() - 1));
					    
					    if(checkDigitCalculado != checkDigitInformado) {
					    	erros.append(errorString + "GTIN inválido, verificar se redigiu corretamente o GTIN.\n");
					    }
					
					
				} else {
					erros.append(errorString + "GTIN deve conter apenas números.\n");
				}
				produto.getAtributos().add(atributo); 
				break;
			case "peso":
				String pesostr = atributo.getValor().toString();
				if(pesostr == null || pesostr.isEmpty() || pesostr.isBlank()) {
					erros.append(errorString + "Vazio.\n");
				}
				
				try {
					JsonNode node = mapper.valueToTree(atributo.getValor());
					
					double massa = node.path("massa").asDouble();
					String unidadePeso = node.path("unidade").asString();
							
					
					if(unidadePeso != null && (unidadePeso.equalsIgnoreCase("G") || unidadePeso.equalsIgnoreCase("KG") || unidadePeso.equalsIgnoreCase("TON")) && massa > 0.0) {
						ObjectNode pesoNode = mapper.createObjectNode();
						
						pesoNode.put("massa", massa);
						pesoNode.put("unidade", unidadePeso);
						
						Object obj = mapper.convertValue(pesoNode, Object.class);
						
						atributo.setValor(obj);
						produto.getAtributos().add(atributo);
					} else {					
						erros.append(errorString + "Formato inválido.\n");
					}
				} catch (Exception e) {
					erros.append(errorString + "Erro no processamento dos dados de peso.\n");
				}
				break;
			case "quantidade":
					String quantidadeStr = atributo.getValor().toString();
				
					if (quantidadeStr == null || quantidadeStr.isBlank()) {
						erros.append(errorString + "Vazio.\n");
					}
					
					JsonNode quantidadeNode = mapper.valueToTree(atributo.getValor());

					String uni = quantidadeNode.path("unidade").asString().toUpperCase();
					String quant = quantidadeNode.path("quantidade").asString();

					ObjectNode objectNode = mapper.createObjectNode();

					if(!UNIDADES_INTEIRAS.contains(uni) && !UNIDADES_FRACIONADAS.contains(uni)){
						erros.append(errorString + "Unidade inválida, inserir tal como: " + MSG_UNIDADES_VALIDAS + ".\n");
						break;
					} else if(UNIDADES_INTEIRAS.contains(uni) && !quant.contains(".") && !quant.contains(",")){
						objectNode.put("quantidade", Long.valueOf(quant));
					} else if (UNIDADES_FRACIONADAS.contains(uni)){
						objectNode.put("quantidade", new BigDecimal(quant));
					} else {
						erros.append(errorString + "Quantidade numérica não suportada para o tipo de unidade inserida.\n");
					}
					
					objectNode.put("unidade", uni);
					Object obj = mapper.convertValue(objectNode, Object.class);	
					atributo.setValor(obj);	
					produto.getAtributos().add(atributo);	
				break;
			default:
				String textoStr = atributo.getValor().toString();
				if(textoStr == null || textoStr.isBlank()) {
					erros.append(errorString + "Vazio.\n");
				}else {
					produto.getAtributos().add(atributo);
				}
				break;
			}
		});
		
		if(!erros.isEmpty()) {
			throw new AtributosInvalidosException(erros.toString());
		} else {
			return produto;
		}
		
	}
}
