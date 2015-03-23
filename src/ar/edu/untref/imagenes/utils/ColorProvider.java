package ar.edu.untref.imagenes.utils;

import java.awt.Color;

public class ColorProvider {

	public static String getHexagesimal(int rgb) {
		return String.format("#%06X", (0xFFFFFF & rgb));
	}

	public static int getIntRgbGrayScale(int red, int green, int blue) {
		int redConverter = (int) (red * 0.299);
		int greenConverter = (int) (green * 0.587);
		int blueConverter = (int) (blue * 0.114);

		Color newColor = new Color(redConverter + greenConverter
				+ blueConverter, redConverter + greenConverter + blueConverter,
				redConverter + greenConverter + blueConverter);
		
		return newColor.getRGB();
	}

}
