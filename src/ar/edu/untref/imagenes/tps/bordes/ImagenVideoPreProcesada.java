package ar.edu.untref.imagenes.tps.bordes;

import java.awt.image.BufferedImage;

public class ImagenVideoPreProcesada {

	private BufferedImage image;
	
	public ImagenVideoPreProcesada(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}