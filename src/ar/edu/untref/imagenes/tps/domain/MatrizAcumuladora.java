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
	private Integer saltoEntreDiscretizacionesDeRo;
	private Integer discretizacionesTetha;
	private List<Point> [][] matrizAcumuladora;
	private Parametro [][] espacioParametros;
	
	@SuppressWarnings("unchecked")
	public MatrizAcumuladora(int roMin, int roMax, int tethaMin,
			int tethaMax, Integer saltoEntreDiscretizacionesRo, Integer discretizacionesTetha) {
		this.roMin = roMin;
		this.roMax = roMax;
		this.tethaMin = tethaMin;
		this.tethaMax = tethaMax;
		this.setSaltoEntreDiscretizacionesRo(saltoEntreDiscretizacionesRo);
		this.setDiscretizacionesTetha(discretizacionesTetha);
		this.cargarEspacioParametros();
		this.setMatrizAcumuladora(new ArrayList[this.espacioParametros.length]
				[this.espacioParametros[0].length]);
	}
	
	private void cargarEspacioParametros() {
//		int cantidadDeTethas =  (int) ((float)((this.tethaMax-this.tethaMin)/this.discretizacionesTetha));
//		int cantidadDeRos =  (int) ((float)((this.roMax-this.roMin)/this.saltoEntreDiscretizacionesDeRo));
//		
//		this.setEspacioParametros(new Parametro[cantidadDeRos][cantidadDeTethas]);
//		
//		for (int i= 0; i< cantidadDeRos; i++) {
//			//for (int j= 0; j< cantidadDeTethas; j++) {
//				//this.getEspacioParametros()[i][j] = new Parametro(roMin + (getDiscretizacionesRo()*i), tethaMin + 
//					//	(discretizacionesTetha*j));
//			//}
//			
//		}
		
		this.setEspacioParametros(new Parametro[2][2]);
		this.getEspacioParametros()[0][0] = new Parametro(50, 0);
		this.getEspacioParametros()[0][1] = new Parametro(50, 90);
		this.getEspacioParametros()[1][0] = new Parametro(150, 0);
		this.getEspacioParametros()[1][1] = new Parametro(150, 90);
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
		return saltoEntreDiscretizacionesDeRo;
	}

	public void setSaltoEntreDiscretizacionesRo(Integer discretizacionesRo) {
		this.saltoEntreDiscretizacionesDeRo = discretizacionesRo;
	}
	
}