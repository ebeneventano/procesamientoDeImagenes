package ar.edu.untref.imagenes.tps.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import ar.edu.untref.imagenes.utils.ColorProvider;

public class GeneradorDeRuido {

	public double generarNumeroAleatorioGaussiano(double desviacionEstandar, double valorMedio) {

		double varianza = desviacionEstandar * desviacionEstandar;
		double x = valorMedio + new Random().nextGaussian() * varianza;

		double coeficiente = 1 / (desviacionEstandar * Math.sqrt(2 * Math.PI));

		double numeradorDeLaPotencia = (((-1) * x) - valorMedio);
		numeradorDeLaPotencia = numeradorDeLaPotencia * numeradorDeLaPotencia;
		double denominadorDeLaPotencia = 2 * desviacionEstandar
				* desviacionEstandar;

		return coeficiente
				* Math.exp(numeradorDeLaPotencia / denominadorDeLaPotencia);
	}

	public double generarNumeroAleatorioRayleigh(double phi) {

		double x = 0;
		while (x == 0) {
			x = Math.random();
		}

		double coeficiente = x / (phi * phi);

		double numeradorDeLaPotencia = (-1) * (x * x);
		double denominadorDelaPotencia = 2 * phi * phi;

		return coeficiente
				* Math.exp(numeradorDeLaPotencia / denominadorDelaPotencia);
	}

	public double generarNumeroAleatorioExponencial(double lambda) {

		double x = 0;
		while (x == 0) {
			x = Math.random();
		}

		return lambda * Math.exp((-1) * lambda * x);
	}

	public BufferedImage ruidoGaussianoAditivo(BufferedImage original, double sigma, double mu) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();

				double x1 = Math.random();
				double x2 = Math.random();
				double ruido = (Math.sqrt((-2) * Math.log10(x1)) * Math.cos(2
						* Math.PI * x2))
						* sigma + mu;

				int ruidoAditivo = (int) (nivelDeRojo + ruido);

				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoAditivo,
													ruidoAditivo, ruidoAditivo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
			}
		}

		return nuevaImagen;
	}

	public BufferedImage ruidoRayleighMultiplicativo(BufferedImage original, double phi) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double x = Math.random();
				double ruido = phi * Math.sqrt( (-2) * Math.log10(1-x) );

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();
				
				int ruidoMultiplicativo = (int) (nivelDeRojo * ruido);

				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoMultiplicativo,
														ruidoMultiplicativo, ruidoMultiplicativo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
				
			}
		}


		return nuevaImagen;
	}

}