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
		
		// Recta Vertical
		if(punto1.x == punto2.x) {
			
			x0 = 0;
			x1 = imagen.getWidth();
			y0 = punto1.y;
			y1 = punto1.y;
			
		// Recta Horizontal
		} else if (punto1.y == punto2.y) {

			x0 = punto1.x;
			x1 = punto1.x;
			y0 = 0;
			y1 = imagen.getHeight();			

		// Diagonales
		} else {
			
			// Ecuacion de la recta que pasa por dos puntos (x0, y0), (x1, y1)
			// (x - x0) * (y1 - y0) = (y - y0) * (x1 - x0)
			// => y = [ (x - x0) * (y1 - y0) / (x1 - x0) ] - y0
			
			// Cuando x = 0 ¿cuál es el y correspondiente de la recta en la imágen?
			x0 = 0;
			y0 = (x0 - punto1.x) * (punto2.y - punto1.y) / (punto2.x - punto1.x);
			y0 = y0 - punto1.y;
			
			// Necesito otro punto de la imágen, ¿cuando x0 es el ancho total, cuál va a ser el valor de y?
			x1 = imagen.getWidth() - 1;
			y1 = (x1 - punto1.x) * (punto2.y - punto1.y) / (punto2.x - punto1.x);
			y1 = y1 - punto1.y;
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
