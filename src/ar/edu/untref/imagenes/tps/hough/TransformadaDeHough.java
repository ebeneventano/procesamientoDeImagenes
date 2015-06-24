package ar.edu.untref.imagenes.tps.hough;

import java.awt.Color;
import java.awt.Graphics2D;
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
		Graphics2D graphics = nuevaImagen.createGraphics();
		graphics.setBackground(Color.WHITE);
		
		for (HoughLine line: acumuladora.getMaximos()) {
			line.draw(acumuladora, nuevaImagen);
		}
		
		return nuevaImagen;
	}

	private static void verificarBlanco(MatrizAcumuladora acumuladora, int i, int j, Color color) {
		if (color.getRed() == 255) {
			for (int k= 0; k< acumuladora.getEspacioParametros().length; k++) {
				for (int m=0; m <acumuladora.getEspacioParametros()[0].length; m++) {
					int tetha = acumuladora.getEspacioParametros()[k][m].getTetha();
					int ro = acumuladora.getEspacioParametros()[k][m].getRo();
					evaluarPunto(acumuladora, i, j, k, m, tetha, ro);
				}
			}
		}
	}

	private static void evaluarPunto(MatrizAcumuladora acumuladora, int i, int j, int k, 
			int m, int tetha, int ro) {
		if(acumuladora.getMatrizAcumuladora()[k][m] == null){
			acumuladora.getMatrizAcumuladora()[k][m] = new ArrayList<>();
		}
		
		if ((ro - i*Math.cos(tetha)- j*Math.sin(tetha)) < 1) {
			acumuladora.getMatrizAcumuladora()[k][m].add(new Point(i, j));
		}
	}

}