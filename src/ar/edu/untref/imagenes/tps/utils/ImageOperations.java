package ar.edu.untref.imagenes.tps.utils;

import java.awt.image.BufferedImage;

public class ImageOperations {
	
	private BufferedImage image1;
	private BufferedImage image2;
	
	public ImageOperations(BufferedImage image1, BufferedImage image2){
		this.image1 = image1;
		this.image2 = image2;
	}
	
	public BufferedImage sumImages(){
		return operateWithImage("+");
	}
	
	public BufferedImage restImages(){
		return operateWithImage("-");
	}
	
	public BufferedImage multiplicateImages(){
		return operateWithImage("*");
	}
	
	public BufferedImage scalarMultiplication(int scalar){
		return null;
	}

	private BufferedImage operateWithImage (String operator){
		if (image1.getWidth() == image2.getWidth() && image1.getHeight() == image2.getHeight()) {

			BufferedImage imageSum = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
			
			for (int i = 0; i < image1.getWidth(); i++) {
				for (int j = 0; j < image1.getHeight(); j++) {

					int rgbImage1 = image1.getRGB(i, j);
					int rgbImage2 = image2.getRGB(i, j);
					
					if ("+".equals(operator)) {
						imageSum.setRGB(i, j, rgbImage1 + rgbImage2);
					} else if ("-".equals(operator)) {
						imageSum.setRGB(i, j, rgbImage1 - rgbImage2);
					} else if ("*".equals(operator)){
						imageSum.setRGB(i, j, rgbImage1 * rgbImage2);
					}
				}
			}

			return imageSum;
			
		} else {
			
			return null;
		}
	}
}
