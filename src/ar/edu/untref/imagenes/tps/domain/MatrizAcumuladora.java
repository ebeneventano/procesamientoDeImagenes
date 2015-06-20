package ar.edu.untref.imagenes.tps.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ar.edu.untref.imagenes.tps.hough.HoughLine;
import ar.edu.untref.imagenes.tps.hough.Parametro;

public class MatrizAcumuladora {

	private int roMin;
	private int roMax;
	private int tethaMin;
	private int tethaMax;
	private Integer discretizacionesRo;
	private Integer discretizacionesTetha;
	private List<Point> [][] matrizAcumuladora;
	private Parametro [][] espacioParametros;
	
	@SuppressWarnings("unchecked")
	public MatrizAcumuladora(int phiMin, int phiMax, int tethaMin,
			int tethaMax, Integer discretizacionesRo, Integer discretizacionesTetha) {
		this.roMin = phiMin;
		this.roMax = phiMax;
		this.tethaMin = tethaMin;
		this.tethaMax = tethaMax;
		this.setDiscretizacionesRo(discretizacionesRo);
		this.setDiscretizacionesTetha(discretizacionesTetha);
		this.cargarEspacioParametros();
		this.setMatrizAcumuladora(new ArrayList[this.espacioParametros.length]
				[this.espacioParametros[0].length]);
	}
	
	private void cargarEspacioParametros() {
		int cantidadDeTethas =  (int) ((float)((this.tethaMax-this.tethaMin)/this.discretizacionesTetha));
		int cantidadDeRos =  (int) ((float)((this.roMax-this.roMin)/this.discretizacionesRo));
		
		this.setEspacioParametros(new Parametro[cantidadDeRos][cantidadDeTethas]);
		
		for (int i= 0; i< cantidadDeRos; i++) {
			for (int j= 0; j< cantidadDeTethas; j++) {
				this.getEspacioParametros()[i][j] = new Parametro(roMin + (getDiscretizacionesRo()*i), tethaMin + 
						(discretizacionesTetha*j));
			}
		}
	}
	
	public int getTethaMin() {
		return tethaMin;
	}
	
	public void setTethaMin(int tethaMin) {
		this.tethaMin = tethaMin;
	}
	
	public int getTethaMax() {
		return tethaMax;
	}
	
	public void setTethaMax(int tethaMax) {
		this.tethaMax = tethaMax;
	}

	public List<Point> [][] getMatrizAcumuladora() {
		return matrizAcumuladora;
	}

	public void setMatrizAcumuladora(List<Point> [][] matrizAcumuladora) {
		this.matrizAcumuladora = matrizAcumuladora;
	}

	public List<HoughLine> getMaximos() {
		int cantidadElementosMaximo = 0;
		List<HoughLine> lines = new ArrayList<>();
		
		for (int i=0; i< matrizAcumuladora.length; i++) {
			for (int j=0; j< matrizAcumuladora[0].length; j++){

				if (matrizAcumuladora[i][j].size() > cantidadElementosMaximo) {
					cantidadElementosMaximo = matrizAcumuladora[i][j].size();
					lines.clear();
				}
				
				if (matrizAcumuladora[i][j].size() == cantidadElementosMaximo) {
					lines.add(new HoughLine(espacioParametros[i][j].getRo(), 
							espacioParametros[i][j].getTetha()));
				}
				
			}
		}
		
		return lines;
	}

	public Parametro [][] getEspacioParametros() {
		return espacioParametros;
	}

	public void setEspacioParametros(Parametro [][] espacioParametros) {
		this.espacioParametros = espacioParametros;
	}

	public Integer getDiscretizacionesTetha() {
		return discretizacionesTetha;
	}

	public void setDiscretizacionesTetha(Integer discretizacionesTetha) {
		this.discretizacionesTetha = discretizacionesTetha;
	}

	public Integer getDiscretizacionesRo() {
		return discretizacionesRo;
	}

	public void setDiscretizacionesRo(Integer discretizacionesRo) {
		this.discretizacionesRo = discretizacionesRo;
	}
	
}