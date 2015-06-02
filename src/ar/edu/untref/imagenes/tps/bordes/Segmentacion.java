package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.edu.untref.imagenes.tps.domain.PromedioRGB;

public class Segmentacion {

	public static void segmentarImagen(BufferedImage imagen, Point punto1, Point punto2) {
		   int primerPuntoX = punto1.x;
	       int primerPuntoY = punto1.y;
	       int segundoPuntoX = punto2.x;
	       int segundoPuntoY = punto2.y;
	       
	       int desdeX = 0;
	       int hastaX = 0;
	       int desdeY = 0;
	       int hastaY = 0;
	       
	       if(primerPuntoX <= segundoPuntoX){
	    	   desdeX = primerPuntoX;
	    	   hastaX = segundoPuntoX;
	       } else {
	    	   desdeX = segundoPuntoX;
	    	   hastaX = primerPuntoX;
	       }
	       
	       if(primerPuntoY<= segundoPuntoY){
	    	   desdeY = primerPuntoY;
	    	   hastaY = segundoPuntoY;
	       } else {
	    	   desdeY = segundoPuntoY;
	    	   hastaY = primerPuntoY;
	       }
	       
		int[][] matrizSigmas = new int [imagen.getWidth()][imagen.getHeight()];
		List<Point> lIn = new ArrayList<>();
		List<Point> lOut = new ArrayList<>();

		for(int i = 0; i < imagen.getWidth() ; i++){
			for(int j = 0; j < imagen.getHeight() ; j++){
				
				if(estaEnBorde(i, j, desdeX, hastaX, desdeY, hastaY)){
					
					matrizSigmas[i][j] = 1;
					lIn.add(new Point(i,j));
					
				}else if(estaAlRededorDelBorde(i, j, desdeX, hastaX, desdeY, hastaY)){
					
					matrizSigmas[i][j] = -1;
					lOut.add(new Point(i,j));
					
				}else if(estaAdentro(i, j, desdeX, hastaX, desdeY, hastaY)){
					
					matrizSigmas[i][j] = -3;
					
				}else{
					
					matrizSigmas[i][j] = 3;
					
				}
				
			}
		}
		
		PromedioRGB promedio = obtenerPromedioRGB(imagen, punto1, punto2);
		
		Iterator<Point> iteradorPuntos = lOut.iterator();
		while(iteradorPuntos.hasNext()){
			
			Point unPoint = iteradorPuntos.next();
			
			Color color = new Color(imagen.getRGB(unPoint.x, unPoint.y));
			int diferenciaColorRojo = color.getRed() - promedio.getPromedioRojo();
			int diferenciaColorVerde = color.getGreen() - promedio.getPromedioVerde();
			int diferenciaColorAzul = color.getBlue() - promedio.getPromedioAzul();
			
			int norma = (int) Math.sqrt((diferenciaColorAzul*diferenciaColorAzul + diferenciaColorRojo*diferenciaColorRojo + diferenciaColorVerde*diferenciaColorVerde));
			if(norma/(255*255*255) < 1){
				int valorMatrizIzquierda = matrizSigmas[unPoint.x - 1][unPoint.y];
				int valorMatrizDerecha = matrizSigmas[unPoint.x + 1][unPoint.y];
				int valorMatrizArriba = matrizSigmas[unPoint.x][unPoint.y - 1];
				int valorMatrizAbajo = matrizSigmas[unPoint.x][unPoint.y + 1];
				
				if(valorMatrizIzquierda == 3){
					matrizSigmas[unPoint.x - 1][unPoint.y] = 1;
					lIn.add(new Point(unPoint.x - 1, unPoint.y));
					matrizSigmas[unPoint.x][unPoint.y] = -3;
					iteradorPuntos.remove();
				}
				if(valorMatrizDerecha == 3){
					matrizSigmas[unPoint.x + 1][unPoint.y] = 1;
					lIn.add(new Point(unPoint.x + 1, unPoint.y));
					matrizSigmas[unPoint.x][unPoint.y] = -3;
					iteradorPuntos.remove();
				}
				if(valorMatrizArriba == 3){
					matrizSigmas[unPoint.x][unPoint.y - 1] = 1;
					lIn.add(new Point(unPoint.x, unPoint.y - 1));
					matrizSigmas[unPoint.x][unPoint.y] = -3;
					iteradorPuntos.remove();
				}
				if(valorMatrizAbajo == 3){
					matrizSigmas[unPoint.x][unPoint.y + 1] = 1;
					lIn.add(new Point(unPoint.x, unPoint.y + 1));
					matrizSigmas[unPoint.x][unPoint.y] = -3;
					iteradorPuntos.remove();
				}
			}
		}
		
		Iterator<Point> iteradorPuntosLin = lIn.iterator();
		while(iteradorPuntosLin.hasNext()){
			
			Point unPoint = iteradorPuntosLin.next();
			
			Color color = new Color(imagen.getRGB(unPoint.x, unPoint.y));
			int diferenciaColorRojo = color.getRed() - promedio.getPromedioRojo();
			int diferenciaColorVerde = color.getGreen() - promedio.getPromedioVerde();
			int diferenciaColorAzul = color.getBlue() - promedio.getPromedioAzul();
			
			int norma = (int) Math.sqrt((diferenciaColorAzul*diferenciaColorAzul + diferenciaColorRojo*diferenciaColorRojo + diferenciaColorVerde*diferenciaColorVerde));
			if(norma/(255*255*255) < 1){
				int valorMatrizIzquierda = matrizSigmas[unPoint.x - 1][unPoint.y];
				int valorMatrizDerecha = matrizSigmas[unPoint.x + 1][unPoint.y];
				int valorMatrizArriba = matrizSigmas[unPoint.x][unPoint.y - 1];
				int valorMatrizAbajo = matrizSigmas[unPoint.x][unPoint.y + 1];
				
				if(valorMatrizIzquierda == -3){
					matrizSigmas[unPoint.x - 1][unPoint.y] = -1;
					lOut.add(new Point(unPoint.x - 1, unPoint.y));
					matrizSigmas[unPoint.x][unPoint.y] = 3;
					iteradorPuntos.remove();
				}
				if(valorMatrizDerecha == -3){
					matrizSigmas[unPoint.x + 1][unPoint.y] = -1;
					lOut.add(new Point(unPoint.x + 1, unPoint.y));
					matrizSigmas[unPoint.x][unPoint.y] = 3;
					iteradorPuntos.remove();
				}
				if(valorMatrizArriba == -3){
					matrizSigmas[unPoint.x][unPoint.y - 1] = -1;
					lOut.add(new Point(unPoint.x, unPoint.y - 1));
					matrizSigmas[unPoint.x][unPoint.y] = 3;
					iteradorPuntos.remove();
				}
				if(valorMatrizAbajo == -3){
					matrizSigmas[unPoint.x][unPoint.y + 1] = -1;
					lOut.add(new Point(unPoint.x, unPoint.y + 1));
					matrizSigmas[unPoint.x][unPoint.y] = 3;
					iteradorPuntos.remove();
				}
			}
		}
	}
	
	private static boolean estaAdentro(int i, int j, int desdeX, int hastaX, int desdeY, int hastaY) {
		boolean estaAdentro = false;
		if(i > desdeX && i < hastaX && j > desdeY && j < hastaY){
			estaAdentro = true;
		}
		
		return estaAdentro;
	}

	private static boolean estaAlRededorDelBorde(int i, int j, int desdeX, int hastaX, int desdeY, int hastaY) {
		
		boolean estaAlrededor = false;
		if(j + 1 == desdeY && i <= hastaX && i >= desdeX){
			estaAlrededor = true;
		}else if(i + 1 == desdeX && j >= desdeY && j<= hastaY){
			estaAlrededor = true;
		}else if(i - 1 == hastaX && j >= desdeY && j<= hastaY){
			estaAlrededor = true;
		}else if(j - 1 == hastaY && i >= desdeX && i <= hastaX){
			estaAlrededor = true;
		}
		
		return estaAlrededor;
	}

	private static boolean estaEnBorde(int i, int j, int desdeX, int hastaX, int desdeY, int hastaY) {
		boolean estaEnBorde = false;
		if(j == desdeY && i <= hastaX && i >= desdeX){
			estaEnBorde = true;
		}else if(i == desdeX && j >= desdeY && j<= hastaY){
			estaEnBorde = true;
		}else if(i == hastaX && j >= desdeY && j<= hastaY){
			estaEnBorde = true;
		}else if(j == hastaY && i >= desdeX && i <= hastaX){
			estaEnBorde = true;
		}
		
		return estaEnBorde;
	}

	private static PromedioRGB obtenerPromedioRGB(BufferedImage imagen, Point punto1, Point punto2){
	       int rojoAcumulado = 0 ;
	       int verdeAcumulado = 0;
	       int azulAcumulado = 0;
	       
	       int contadorCantPixel = 0;
	       
	       int promedioRojo = 0;
	       int promedioVerde = 0;
	       int promedioAzul = 0;
	       
	       int primerPuntoX = punto1.x;
	       int primerPuntoY = punto1.y;
	       int segundoPuntoX = punto2.x;
	       int segundoPuntoY = punto2.y;
	       
	       int desdeX = 0;
	       int hastaX = 0;
	       int desdeY = 0;
	       int hastaY = 0;
	       
	       if(primerPuntoX <= segundoPuntoX){
	    	   desdeX = primerPuntoX;
	    	   hastaX = segundoPuntoX;
	       } else {
	    	   desdeX = segundoPuntoX;
	    	   hastaX = primerPuntoX;
	       }
	       
	       if(primerPuntoY<= segundoPuntoY){
	    	   desdeY = primerPuntoY;
	    	   hastaY = segundoPuntoY;
	       } else {
	    	   desdeY = segundoPuntoY;
	    	   hastaY = primerPuntoY;
	       }
	       
	       for(int i = desdeX ; i < hastaX ; i++){
	    	   for(int j = desdeY ; j < hastaY ; j++){
			       Color color = new Color(imagen.getRGB(i, j));
			       int blue = color.getBlue();
			       int green = color.getGreen();
			       int red = color.getRed();
			       
			       rojoAcumulado += red;
			       verdeAcumulado += green;
			       azulAcumulado += blue;
			       
			       contadorCantPixel++;
	    	   }
	       }
	       
	       promedioRojo = rojoAcumulado / contadorCantPixel;
	       promedioVerde = verdeAcumulado / contadorCantPixel;
	       promedioAzul = azulAcumulado / contadorCantPixel;

	       PromedioRGB promedio = new PromedioRGB(promedioRojo, promedioVerde, promedioAzul);
	       
	       return promedio;

	}
}
