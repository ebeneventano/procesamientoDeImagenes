package susan;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Smallest Univaluate Segment Assimilating Nucleus
 * 
 * Detector de bordes y esquinas
 * 
 * @author frodriguez
 * 
 */
public class Susan {

	private static final int TAMANIO_MASCARA = 7;
	
	// Se cuentan sÃ³lo los pÃ­xeles de la imÃ¡gen circular
	private static final int CANTIDAD_PIXELES_MASCARA = 37;
	
	private int[][] mascara;
	private double umbralT = 27.0;
	
	private int pixelNegro = new Color(0, 0, 0).getRGB();
	private int pixelRojo = new Color(255, 0, 0).getRGB();
	
 	private static double criterioDeBorde = 0.5;
 	private static double criterioDeEsquina = 0.75;
	
	/**
	 * Si el resultado es aprox 0, no corresponde a borde ni esquina.
	 * Si el resultado es aprox 0.5, es un borde.
	 * Si el resultado es aprox 0.75 es una esquina.
	 * 
	 * Por lo tanto, se tomÃ³ como criterio que cualquier resultado mayor a 0.4, serÃ¡ considerado borde/esquina.
	 */
	
	/**
	 * Aplica una mÃ¡scara circular de 7x7 con el mÃ©todo de Susan.
	 */
	public Susan() {
		
		inicializarMascaraCircular();
	}

	private void inicializarMascaraCircular() {

		this.mascara = new int[TAMANIO_MASCARA][TAMANIO_MASCARA];
		
		int[] primerFila = {0, 0, 1, 1, 1, 0, 0};
		int[] segundaFila = {0, 1, 1, 1, 1, 1, 0};
		
		int[] filaDeSiete1 = new int[TAMANIO_MASCARA];
		int[] filaDeSiete2 = new int[TAMANIO_MASCARA];
		int[] filaDeSiete3 = new int[TAMANIO_MASCARA];
		
		for (int i = 0 ; i < TAMANIO_MASCARA ; i++) {
			filaDeSiete1[i] = 1;
			filaDeSiete2[i] = 1;
			filaDeSiete3[i] = 1;
		}
		
		int[] anteUltimaFila = {0, 1, 1, 1, 1, 1, 0};
		int[] ultimaFila = {0, 0, 1, 1, 1, 0, 0};
		
		this.mascara[0] = primerFila;
		this.mascara[1] = segundaFila;
		this.mascara[2] = filaDeSiete1;
		this.mascara[3] = filaDeSiete2;
		this.mascara[4] = filaDeSiete3;
		this.mascara[5] = anteUltimaFila;
		this.mascara[6] = ultimaFila;
	}
	
	/**
	 * @return imagen binaria que detecta los bordes y esquinas de la imagen original
	 */
	public BufferedImage aplicarSusanBorde(BufferedImage imagenOriginal, String flagDetector) {
		
		BufferedImage imagenResultado = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), imagenOriginal.getType());
		
 		int sumarEnAncho = (-1) * (TAMANIO_MASCARA / 2);
 		int sumarEnAlto = (-1) * (TAMANIO_MASCARA / 2);
 		
 		// Iterar la imagen, sacando los bordes.
 		for (int i = TAMANIO_MASCARA / 2; i < imagenResultado.getWidth() - (TAMANIO_MASCARA / 2); i++) {
 			for (int j = TAMANIO_MASCARA / 2; j < imagenResultado.getHeight() - (TAMANIO_MASCARA / 2); j++) {
 
 				// Tomo el valor del píxel central de la máscara (el (3,3) de la máscara)
 				int indiceICentralDeLaImagen = i + sumarEnAncho + (TAMANIO_MASCARA / 2);
 				int indiceJCentralDeLaImagen = j + sumarEnAlto + (TAMANIO_MASCARA / 2);
 				double valorCentral = new Color(imagenOriginal.getRGB(indiceICentralDeLaImagen, indiceJCentralDeLaImagen)).getRed();
 				
 				int cantidadDePixelesSimilaresAlCentral = 0;

 				// Iterar la máscara
 				for(int iAnchoMascara = 0; iAnchoMascara < TAMANIO_MASCARA; iAnchoMascara++) {
 					for(int iAltoMascara = 0; iAltoMascara < TAMANIO_MASCARA; iAltoMascara++) {
 						
 						int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
 						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;
 
 						double valor = new Color(imagenOriginal.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
 						
 						// Se multiplica el valor leído por la máscara, para sacar los que no pertenezcan a la parte circular.
 						valor = valor * mascara[iAnchoMascara][iAltoMascara];
 						
 						if (Math.abs(valor - valorCentral) < umbralT) {
 							
 							cantidadDePixelesSimilaresAlCentral++;
 						}
 					}
 				}
 				// Fin iteración máscara
 				
 				double Sr0 = 1.0 - ((double)cantidadDePixelesSimilaresAlCentral / (double)CANTIDAD_PIXELES_MASCARA);
 				
 				
 				switch (flagDetector) {

 				case "E":
 					if(Math.abs( Sr0 - criterioDeEsquina) < 0.1){
 	 					
 	 					imagenResultado.setRGB(i, j, pixelRojo);
 	 				} else {
 	 					
 	 					imagenResultado.setRGB(i, j, pixelNegro);
 	 				}
 					break;

 				case "B":
 					if(Math.abs( Sr0 - criterioDeBorde) < 0.1){
 	 					
 	 					imagenResultado.setRGB(i, j, pixelRojo);
 	 				} else {
 	 					
 	 					imagenResultado.setRGB(i, j, pixelNegro);
 	 				}
 					break;
 					
 				case "BE":
 					if(Math.abs( Sr0 - criterioDeEsquina) < 0.1 || Math.abs( Sr0 - criterioDeBorde) < 0.1){
 	 					
 	 					imagenResultado.setRGB(i, j, pixelRojo);
 	 				} else {
 	 					
 	 					imagenResultado.setRGB(i, j, pixelNegro);
 	 				}
 					break;
 					
				case "EB":
 					if(Math.abs( Sr0 - criterioDeEsquina) < 0.1 || Math.abs( Sr0 - criterioDeBorde) < 0.1){
 	 					
 	 					imagenResultado.setRGB(i, j, pixelRojo);
 	 				} else {
 	 					
 	 					imagenResultado.setRGB(i, j, pixelNegro);
 	 				}
 					break;
				}
 			}
 		}
 		return imagenResultado;
	}
	
}