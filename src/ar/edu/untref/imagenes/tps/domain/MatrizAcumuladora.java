package ar.edu.untref.imagenes.tps.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ar.edu.untref.imagenes.tps.hough.HoughLine;
import ar.edu.untref.imagenes.tps.hough.Parametro;

public class MatrizAcumuladora {

	private int rhoMin;
	private int rhoMax;
	private int tethaMin;
	private int tethaMax;
	private Integer discretizacionesRho;
	private Integer discretizacionesTetha;
	private List<Point> [][] matrizAcumuladora;
	private Parametro [][] espacioParametros;
	
	@SuppressWarnings({ "unchecked", "javadoc" })
	public MatrizAcumuladora(int roMin, int roMax, int tethaMin,
			int tethaMax, Integer saltoEntreDiscretizacionesRho, Integer discretizacionesTetha) {
		
		this.rhoMin = roMin;
		this.rhoMax = roMax;
		this.tethaMin = tethaMin;
		this.tethaMax = tethaMax;
		this.setDDiscretizacionesRho(saltoEntreDiscretizacionesRho);
		this.setDiscretizacionesTetha(discretizacionesTetha);
		this.cargarEspacioParametros();
		this.setMatrizAcumuladora(new ArrayList[this.espacioParametros.length]	[this.espacioParametros[0].length]);
	}
	
	private void cargarEspacioParametros() {

		this.setEspacioParametros(new Parametro[this.discretizacionesRho][this.discretizacionesTetha]);

		int distanciaEntreRhos = this.rhoMax - this.rhoMin;
		if (this.discretizacionesRho > 2) {
			distanciaEntreRhos = this.rhoMax / this.discretizacionesRho;
		}
		
		int distanciaEntreTethas = this.tethaMax - this.tethaMin;
		if (this.discretizacionesTetha > 2) {
			distanciaEntreTethas = this.tethaMax / this.discretizacionesTetha;
		}
		
		
		for (int rho = 0; rho < this.discretizacionesRho; rho++) {
			for (int tetha = 0; tetha < this.discretizacionesTetha; tetha++) {
				
				this.getEspacioParametros()[rho][tetha] = new Parametro(rho * distanciaEntreRhos, tetha * distanciaEntreTethas);
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
		return discretizacionesRho;
	}

	public void setDDiscretizacionesRho(Integer discretizacionesRo) {
		this.discretizacionesRho = discretizacionesRo;
	}
	
}