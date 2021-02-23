package com.jsanz.metanalizer.core.dicionario;

public enum ListaFormato {
	
	PAUPER(1), STANDAR(2), MODERN(3), PIONEER(4), LEGACY(5), VINTAGE(6), COMMANDER(7), COMMANDER1VS1(8);
	
	private int id; 

	
	ListaFormato(int id) {
		this.id=id;
	}


	public static Integer Match(String input) {
		switch (input) {
		case "PAUPER":
			return 1;			
		case "Pauper":
			return 1;			
		case "pauper":
			return 1;			
		default:
			return null;
		}
	}
	
	public static Integer Contains(String input) {
		if(input.contains("PAUPER") || input.contains("Pauper") || input.contains("pauper")) {
			return 1;
		}
		return 0;
	}
}
