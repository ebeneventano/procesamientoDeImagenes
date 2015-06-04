package ar.edu.untref.imagenes.tps.domain;

import java.awt.Point;

public class Curva {
	
	private Point desde;
	private Point hasta;
	
	public Curva (Point desde, Point hasta){
		this.desde = desde;
		this.hasta = hasta;
	}
	
	public Point getDesde(){
		return desde;
	}
	
	public Point getHasta(){
		return hasta;
	}

}
