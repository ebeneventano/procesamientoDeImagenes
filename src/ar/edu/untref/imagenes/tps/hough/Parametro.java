package ar.edu.untref.imagenes.tps.hough;

public class Parametro {

	private Integer tetha;
	private Integer ro;

	public Parametro(Integer ro, Integer tetha) {
		this.tetha = tetha;
		this.ro = ro;
	}
	
	public Integer getTetha() {
		return tetha;
	}

	public Integer getRo() {
		return ro;
	}

}
