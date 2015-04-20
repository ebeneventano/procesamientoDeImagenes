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
//				double ruido = 1 - (Math.exp(-((x*x)/(2*phi*phi))));

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();
				
				int ruidoMultiplicativo = (int) (nivelDeRojo * ruido);

				int alpha = (int) (new Color(original.getRGB(i, j)).getAlpha() * ruido);
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoMultiplicativo,
														ruidoMultiplicativo, ruidoMultiplicativo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
				
			}
		}


		return nuevaImagen;
	}

	public BufferedImage ruidoExponencialMultiplicativo(BufferedImage original, double lambda) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double x = Math.random();
				while (x == 0) {
					x = Math.random();
				}

				double ruido = ((-1) / lambda) * Math.log10(x);

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
	
	public BufferedImage ruidoImpulsivo(BufferedImage original, int densidadDeContaminacion) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		int cantidadDePixelesDeLaImagen = original.getWidth() * original.getHeight();
		int cantidadDePixelesAContaminar = densidadDeContaminacion * cantidadDePixelesDeLaImagen / 100;

		// Copiar imagen antes de contaminarla
		for(int ancho = 0; ancho < original.getWidth() ; ancho++) {
			for(int alto = 0; alto < original.getHeight() ; alto++) {
				nuevaImagen.setRGB(ancho, alto, original.getRGB(ancho, alto));
			}
		}
		
		while (cantidadDePixelesAContaminar > 0) {

			System.out.println("cantidadDePixelesAContaminar: " + cantidadDePixelesAContaminar);
			
			int i = (int) (Math.random() * original.getWidth()) ;
			int j = (int) (Math.random() * original.getHeight());

			// Contaminación
			double x = Math.random();

			double p0 = Math.random();
			
			double p1 = Math.random();
			while (p1 <= p0) {
				p1 = Math.random();
			}
			
			int pixelBlanco = ColorProvider.colorToRGB(255, 255, 255, 255);
			int pixelNegro = ColorProvider.colorToRGB(255, 0, 0, 0);
			
			if (x <= p0) {
				nuevaImagen.setRGB(i, j, pixelNegro);

			/* Consultar: ¿Qué hacemos con los valores intermedios? */
//			} else if (x >= p1) {
			} else {

				nuevaImagen.setRGB(i, j, pixelBlanco);
			}
			// Fin contaminación
			
			cantidadDePixelesAContaminar--;
		}
		
		return nuevaImagen;
	}
	
	public BufferedImage suavizadoConFiltroDeLaMedia(BufferedImage original, int anchoMascara, int altoMascara) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		// Copiar imagen antes de filtrarla
		for(int ancho = 0; ancho < original.getWidth() ; ancho++) {
			for(int alto = 0; alto < original.getHeight() ; alto++) {
				nuevaImagen.setRGB(ancho, alto, original.getRGB(ancho, alto));
			}
		}

		// Crear la máscara de la mediana
		int[][] mascara = new int[anchoMascara][altoMascara];
		for (int i = 0; i < anchoMascara; i++) {
			for(int j = 0; j < altoMascara; j++) {
				mascara[i][j] = 1;
			}
		}
		
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		
		// Agregar borde zero-padding
		int pixelNegro = ColorProvider.colorToRGB(255, 0, 0, 0);
		for (int i = 0; i < anchoMascara / 2; i++) {
			for (int j = 0 ; j < nuevaImagen.getHeight() ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		for (int i = 0; i < nuevaImagen.getWidth(); i++) {
			for (int j = 0 ; j < altoMascara / 2 ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		// Iterar la imagen, sacando los bordes.
		for(int i = anchoMascara / 2; i < original.getWidth() - (anchoMascara / 2); i++) {
			for(int j = altoMascara / 2; j < original.getHeight() - (altoMascara / 2); j++) {
				
				int sumatoria = 0;
				// Iterar la máscara
				for(int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for(int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {
						
						// Opero si no es el punto central de la máscara
						if(!(iAnchoMascara == (anchoMascara / 2) && iAltoMascara == (altoMascara / 2))) {
							
							int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
							int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;
				
							double nivelDeRojo = new Color(original.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
							sumatoria += nivelDeRojo * mascara[iAnchoMascara][iAltoMascara];
							
						}
						
					}
				}
				
				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				sumatoria = sumatoria / (anchoMascara * altoMascara);
				int nuevoPixel = ColorProvider.colorToRGB(alpha, sumatoria,
															sumatoria, sumatoria);

				nuevaImagen.setRGB(i, j, nuevoPixel);
			}
		}
		
		return nuevaImagen;
	}

	public BufferedImage suavizadoConFiltroDeLaMediana(BufferedImage original, int anchoMascara, int altoMascara) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		return nuevaImagen;
	}
}