package ar.edu.untref.imagenes.tps.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MatrizAcumuladora {

	private int phiMin;
	private int phiMax;
	private int tethaMin;
	private int tethaMax;
	private int discretizacionesPhi;
	private int discretizacionesTetha;
	private List<Point> [][] matrizAcumuladora;
	
	@SuppressWarnings("unchecked")
	public MatrizAcumuladora(int phiMin, int phiMax, int tethaMin,
			int tethaMax, int discretizacionesPhi, int discretizacionesTetha) {
		this.phiMin = phiMin;
		this.phiMax = phiMax;
		this.tethaMin = tethaMin;
		this.tethaMax = tethaMax;
		this.discretizacionesPhi = discretizacionesPhi;
		this.discretizacionesTetha = discretizacionesTetha;
		this.setMatrizAcumuladora(new ArrayList[discretizacionesTetha][discretizacionesPhi]);
	}
	
	public int getPhiMin() {
		return phiMin;
	}
	public void setPhiMin(int phiMin) {
		this.phiMin = phiMin;
	}
	public int getPhiMax() {
		return phiMax;
	}
	public void setPhiMax(int phiMax) {
		this.phiMax = phiMax;
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
	public int getDiscretizacionesPhi() {
		return discretizacionesPhi;
	}
	public void setDiscretizacionesPhi(int discretizacionesPhi) {
		this.discretizacionesPhi = discretizacionesPhi;
	}
	public int getDiscretizacionesTetha() {
		return discretizacionesTetha;
	}
	public void setDiscretizacionesTetha(int discretizacionesTetha) {
		this.discretizacionesTetha = discretizacionesTetha;
	}

	public List<Point> [][] getMatrizAcumuladora() {
		return matrizAcumuladora;
	}

	public void setMatrizAcumuladora(List<Point> [][] matrizAcumuladora) {
		this.matrizAcumuladora = matrizAcumuladora;
	}

	public List<Point> getMaximos() {
		int cantidadElementosMaximo = 0;
		int posicionX=0;
		int posicionY=0;
		
		for (int i=0; i< getDiscretizacionesTetha(); i++) {
			for (int j=0; j< getDiscretizacionesPhi(); j++){
				if (matrizAcumuladora[i][j].size() > cantidadElementosMaximo) {
					cantidadElementosMaximo = matrizAcumuladora[i][j].size();
					posicionX = i;
					posicionY= j;
				}
			}
		}
		
		return matrizAcumuladora[posicionX][posicionY];
	}
	
}