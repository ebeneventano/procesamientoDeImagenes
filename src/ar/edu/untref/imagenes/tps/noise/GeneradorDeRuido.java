package ar.edu.untref.imagenes.tps.noise;

import java.util.Random;

public class GeneradorDeRuido {

	public double generarNumeroAleatorioGaussiano(double desviacionEstandar, double valorMedio) {
		
		double varianza = desviacionEstandar * desviacionEstandar;
		double x = valorMedio + new Random().nextGaussian() * varianza;
		
		double coeficiente = 1 / ( desviacionEstandar * Math.sqrt( 2 * Math.PI ));
		
		double numeradorDeLaPotencia = (((-1) * x ) - valorMedio);
		numeradorDeLaPotencia = numeradorDeLaPotencia * numeradorDeLaPotencia;
		double denominadorDeLaPotencia = 2 * desviacionEstandar * desviacionEstandar;
		
		return coeficiente * Math.exp(numeradorDeLaPotencia / denominadorDeLaPotencia);
	}
	
	public double generarNumeroAleatorioRayleigh(double phi) {
		
		double x = 0;
		while (x == 0) {
			x = Math.random();
		} 

		double coeficiente = x / (phi*phi);
		
		double numeradorDeLaPotencia = (-1) * (x * x);
		double denominadorDelaPotencia = 2 * phi * phi;
		
		return coeficiente * Math.exp(numeradorDeLaPotencia / denominadorDelaPotencia);
	}
	
	public double generarNumeroAleatorioExponencial(double lambda) {
		
		double x = 0;
		while (x == 0) {
			x = Math.random();
		} 

		return lambda * Math.exp((-1) * lambda * x);
	}
	
}