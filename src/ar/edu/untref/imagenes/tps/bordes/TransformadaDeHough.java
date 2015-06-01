package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ar.edu.untref.imagenes.tps.domain.MatrizAcumuladora;

public class TransformadaDeHough {

	public static BufferedImage aplicarTransformadaDeHough(BufferedImage imagenOriginal, 
			MatrizAcumuladora acumuladora){
		
		for (int i=0; i< imagenOriginal.getWidth(); i++){
			for (int j=0; j< imagenOriginal.getHeight(); j++){
				Color color = new Color(imagenOriginal.getRGB(i, j));
				verificarBlanco(acumuladora, i, j, color);
			}
		}
		
		BufferedImage nuevaImagen = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), 
				imagenOriginal.getType());
		
		for (Point unMaximo: acumuladora.getMaximos()) {
			
			Color color = new Color(255,255,255);
			int rgb = color.getRGB();
			
			nuevaImagen.setRGB(unMaximo.x, unMaximo.y, rgb);
		}
		
		return nuevaImagen;
	}

	private static void verificarBlanco(MatrizAcumuladora acumuladora, int i, int j, Color color) {
		if (color.getRed() == 255) {
			for (int k= 0; k< acumuladora.getDiscretizacionesTetha(); k++) {
				for (int m=0; m <acumuladora.getDiscretizacionesPhi(); m++) {
					int tetha = k + acumuladora.getPhiMin();
					int phi = m + acumuladora.getTethaMin();
					evaluarPunto(acumuladora, i, j, k, m, tetha, phi);
				}
			}
		}
	}

	private static void evaluarPunto(MatrizAcumuladora acumuladora, int i, int j, int k, 
			int m, int tetha, int phi) {
		if(acumuladora.getMatrizAcumuladora()[k][m] == null){
			acumuladora.getMatrizAcumuladora()[k][m] = new ArrayList<>();
		}
		
		if ((phi - i*Math.cos(tetha)- j*Math.sin(tetha)) < 0.2) {
			acumuladora.getMatrizAcumuladora()[k][m].add(new Point(i, j));
		}
	}

}
