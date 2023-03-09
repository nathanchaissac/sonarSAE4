package model.tournoi;

public enum ImportanceTournoi {
	LOCAL("Local"), NATIONAL("National"), INTERNATIONAL("International");
	
	private String name;
	
	private ImportanceTournoi(String name) {
		this.name = name;
	}
	
	public String getNom() {
		return this.name;
	}
	
}
