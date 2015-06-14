package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImagenVideoPreProcesada {

	private BufferedImage image;
	
	private List<Point> lOut;
	
	private List<Point> lIn;
	
	private int[] promedioDeColores;
	
	public ImagenVideoPreProcesada(BufferedImage image, List<Point> lOut, List<Point> lIn, int[] promedioDeColores) {
		this.image = image;
		this.lIn = lIn;
		this.lOut = lOut;
		this.setPromedioDeColores(promedioDeColores);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public List<Point> getlOut() {
		return lOut;
	}

	public void setlOut(List<Point> lOut) {
		this.lOut = lOut;
	}

	public List<Point> getlIn() {
		return lIn;
	}

	public void setlIn(List<Point> lIn) {
		this.lIn = lIn;
	}

	public int[] getPromedioDeColores() {
		return promedioDeColores;
	}

	public void setPromedioDeColores(int[] promedioDeColores) {
		this.promedioDeColores = promedioDeColores;
	}
	
}
