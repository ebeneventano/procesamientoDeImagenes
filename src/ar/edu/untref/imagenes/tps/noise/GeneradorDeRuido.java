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
	
	public double generarNumeroAleatorioRayleigh(double epsilon) {
		
		return 0;
	}
	
}