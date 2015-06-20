package ar.edu.untref.imagenes.tps.hough;

import java.awt.image.BufferedImage;

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

		int height = image.getHeight();
		int width = image.getWidth();

		int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;

		float centerX = width / 2;
		float centerY = height / 2;

		double tsin = Math.sin(tetha);
		double tcos = Math.cos(tetha);

		if (tetha < Math.PI * 0.25 || tetha > Math.PI * 0.75) {
			for (int y = 0; y < height; y++) {
				int x = (int) ((((ro - houghHeight) - ((y - centerY) * tsin)) / tcos) + centerX);
				if (x < width && x >= 0) {
					image.setRGB(x, y, color);
				}
			}
		} else {
			for (int x = 0; x < width; x++) {
				int y = (int) ((((ro - houghHeight) - ((x - centerX) * tcos)) / tsin) + centerY);
				if (y < height && y >= 0) {
					image.setRGB(x, y, color);
				}
			}
		}
	}

	public Integer getRo() {
		return ro;
	}

	public void setRo(Integer ro) {
		this.ro = ro;
	}

}
