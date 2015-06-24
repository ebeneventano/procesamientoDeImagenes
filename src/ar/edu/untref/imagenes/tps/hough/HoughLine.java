package ar.edu.untref.imagenes.tps.hough;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import ar.edu.untref.imagenes.tps.domain.MatrizAcumuladora;

public class HoughLine {

	private Integer tetha;
	private Integer ro;

	public HoughLine(Integer ro, Integer tetha) {
		this.tetha = tetha;
		this.setRo(ro);
	}

	public Integer getTetha() {
		return tetha;
	}

	public void draw(BufferedImage image, int color) {

//		int height = image.getHeight();
//		int width = image.getWidth();
//
//		int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;
//
//		float centerX = width / 2;
//		float centerY = height / 2;
//
//		double tsin = Math.sin(tetha);
//		double tcos = Math.cos(tetha);
//
//		if (tetha < Math.PI * 0.25 || tetha > Math.PI * 0.75) {
//			for (int y = 0; y < height; y++) {
//				int x = (int) ((((ro - houghHeight) - ((y - centerY) * tsin)) / tcos) + centerX);
//				if (x < width && x >= 0) {
//					image.setRGB(x, y, color);
//				}
//			}
//		} else {
//			for (int x = 0; x < width; x++) {
//				int y = (int) ((((ro - houghHeight) - ((x - centerX) * tcos)) / tsin) + centerY);
//				if (y < height && y >= 0) {
//					image.setRGB(x, y, color);
//				}
//			}
//		}
		
		
		
	}

	public Integer getRo() {
		return ro;
	}

	public void setRo(Integer ro) {
		this.ro = ro;
	}

	public void draw(MatrizAcumuladora acumuladora, BufferedImage imagen) {

		Parametro[][] parametros = acumuladora.getEspacioParametros();

		int posicionRho = 0;
		int posicionTheta = 0;
		for(int i = 0; i < parametros.length ; i++) {
			for(int j = 0; j < parametros[0].length ; j++) {
				
				if(parametros[i][j].getRo() == this.ro && parametros[i][j].getTetha() == this.tetha) {
					
					posicionRho = i;
					posicionTheta = j;
				}
			}
		}
		
		List<Point> puntosDeEstaRecta = acumuladora.getMatrizAcumuladora()[posicionRho][posicionTheta];
		
		Point punto1 = puntosDeEstaRecta.get(0);
		Point punto2 = puntosDeEstaRecta.get(1);
		
		Graphics graphics = imagen.getGraphics();
		
		int x0, y0, x1, y1;
		
		if(punto1.x == punto2.x) {
			
			
						x0 = 0;
						x1 = imagen.getWidth();
						y0 = punto1.y;
						y1 = punto1.y;
		} else {
			

			x0 = punto1.x;
			x1 = punto1.x;
			y0 = 0;
			y1 = imagen.getHeight();			
			
		}
		
		graphics.setColor(Color.GREEN);
		graphics.drawLine(x0,y0, x1, y1);
		
		//graphics.drawLine(50, 0, 50, 200);
//		graphics.drawLine(150, 0, 150, 200);
//		graphics.drawLine(0, 50, 200, 50);
//		graphics.drawLine(0, 150, 200, 150);
//		
	}

}
