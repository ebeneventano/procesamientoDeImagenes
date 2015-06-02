package ar.edu.untref.imagenes.tps.domain;

public class PromedioRGB {

	int promedioRojo;
	int promedioVerde;
	int promedioAzul;
	
	public PromedioRGB(int promedioRojo, int promedioVerde, int promedioAzul){
		this.promedioAzul = promedioAzul;
		this.promedioVerde = promedioVerde;
		this.promedioRojo = promedioRojo;
	}
	
	public int getPromedioRojo() {
		return promedioRojo;
	}
	
	public int getPromedioVerde() {
		return promedioVerde;
	}
	
	public int getPromedioAzul() {
		return promedioAzul;
	}
	

}
