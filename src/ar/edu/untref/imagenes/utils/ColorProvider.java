package ar.edu.untref.imagenes.utils;


public class ColorProvider {

	public static String getHexagesimal(int rgb) {
		return String.format("#%06X", (0xFFFFFF & rgb));
	}

	public static int getIntRgbGrayScale(int red, int green, int blue) {
		int redConverter = (int) (red * 0.299);
		int greenConverter = (int) (green * 0.587);
		int blueConverter = (int) (blue * 0.114);
		
		int gray = (int)(redConverter + greenConverter + blueConverter);
		
		return 0xff000000 + (gray<<16) + (gray<<8) + gray;
	}
	
	public static int getIntRgbColorScale(int red, int green, int blue) {	
		int color = (int)(red + green + blue);
		
		return color;
	}

}