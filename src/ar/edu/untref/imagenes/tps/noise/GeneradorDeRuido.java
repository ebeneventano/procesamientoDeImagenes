package ar.edu.untref.imagenes.tps.noise;

public class GeneradorDeRuido {

	public double generarNumeroAleatorioGaussiano(double lambda) {
		
		double x = 0;
		while (x == 0) {
			x = Math.random();
		}
		
		return ( (-1)/(lambda) ) * Math.log(x);
	}
	
	public double generarNumeroAleatorioRayleigh(double epsilon) {
		
		return 0;
	}
	
}