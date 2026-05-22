package com.hermes.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hermes.exception.exceptions.AtributosInvalidosException;
import com.hermes.exception.exceptions.ProductWithoutNameException;
import com.hermes.models.Atributo;
import com.hermes.models.Product;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.JsonNodeException;
import tools.jackson.databind.node.ObjectNode;

@Service
public class ProductValidator {
	
	public ProductValidator() {}
	
	public Product validate(String nome, List<Atributo> atributos) {
		StringBuffer erros = new StringBuffer();
		
		Product produto = new Product();
		
		if(nome == null || nome.isBlank()) {
			erros.append("Sem nome.\n");
			throw new ProductWithoutNameException(erros.toString());
		}
		
		produto.setName(nome);
		
		ObjectMapper mapper = new ObjectMapper();
		
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
						soNumero = !soNumero;
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
				
				String pesoJson = mapper.writeValueAsString(atributo.getValor());
				JsonNode node = mapper.readTree(pesoJson);
				
				Double massa = node.get("massa").asDouble();
				String unidadePeso = node.get("unidade").asString();
						
				
				if((unidadePeso.toString().equals("G") || unidadePeso.toString().equals("KG") || unidadePeso.toString().equals("TON")) && massa > 0.0) {
					ObjectNode pesoNode = mapper.createObjectNode();
					
					pesoNode.put("massa", massa);
					pesoNode.put("unidade", unidadePeso);
					
					Object obj = mapper.convertValue(pesoNode, Object.class);
					
					atributo.setValor(obj);
					produto.getAtributos().add(atributo);
				} else {					
					erros.append(errorString + "Formato inválido.\n");
				}
				break;
			case "quantidade":
					String quantidadeStr = atributo.getValor().toString();
				
					if (quantidadeStr == null || quantidadeStr.isBlank()) {
						erros.append(errorString + "Vazio.\n");
					}
					String quantidadeJson = mapper.writeValueAsString(atributo.getValor());	
					JsonNode quantidadeNode= mapper.readTree(quantidadeJson);
					/*String quantidadeString = quantidadeNode.asString();
					
					if(quantidadeString == null || quantidadeString.isBlank()) {
						erros.append(errorString + "Vazio.");
					}
					StringBuffer quantidade = new StringBuffer();
					StringBuffer unidade = new StringBuffer();
					boolean wasBreaked = false;
					
					for(int l = 0; l < quantidadeString.length(); l++) {
						char C = quantidadeString.charAt(l);
						if(Character.isDigit(C) && !(unidade.isEmpty())) {
							unidade.append(C);
						} else if(Character.isDigit(C)){
							quantidade.append(C);
						} else if(Character.isAlphabetic(C)){
							if(Character.isLowerCase(C)) {
								C = Character.toUpperCase(C);
							}
							unidade.append(C);
						} else if(C == ',') {
							quantidade.append('.');
						} else {
							wasBreaked = true;
							break;
						}
					}
					*/
					String[] unis = {"UNID", "CX", "CX2", "CX3","CX5", "CX10", "CX15", "CX20", "CX25",
					"CX50", "CX100", "PC", "PACOTE", "DUZIA", "CENTO", "MILHEI", "KIT", "CJ", "FD"
					, "RESMA", "ROLO", "GF", "LATA", "FRASCO", "AMPOLA", "BISNAG", "BALDE", "VIDRO"
					, "VASIL", "TAMBOR", "PALETE"};
					
					String[] fracs = {"KG", "G", "TON", "L", "ML", "M", "M2", "M3", "CM", "CM2"};
					
					ArrayList<String> unidadesArrayList = new ArrayList<String>(Arrays.asList(unis));
					ArrayList<String> fracinadosArrayList = new ArrayList<String>(Arrays.asList(fracs));
					
					
					String uni = quantidadeNode.get("unidade").asString(); /*unidade.toString();*/
					String quant = quantidadeNode.get("quantidade").asString() ; /* quantidade.toString();*/
					if(unidadesArrayList.contains(uni) /* && !wasBreaked */ && !(quant.contains("."))) {

						ObjectNode objectNode = mapper.createObjectNode();
						
						objectNode.put("quantidade", Integer.valueOf(quant));
						objectNode.put("unidade", uni);
						
						Object obj = mapper.convertValue(objectNode, Object.class);
						
						atributo.setValor(obj);
						
						produto.getAtributos().add(atributo);
					} else if(fracinadosArrayList.contains(uni) /* && !wasBreaked */) {
						
						ObjectNode objectNode = mapper.createObjectNode();
						
						objectNode.put("quantidade", new BigDecimal(quant));
						objectNode.put("unidade", uni);
						
						Object obj = mapper.convertValue(objectNode, Object.class);
						
						atributo.setValor(obj);
						
						produto.getAtributos().add(atributo);
					} else {
						StringBuffer msg = new StringBuffer();
						unidadesArrayList.forEach(U -> {
							msg.append(U + ", ");
						});
						
						erros.append(errorString + "Unidade inválida, inserir tal como: " + msg.toString() + ".\n");
					}	
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
