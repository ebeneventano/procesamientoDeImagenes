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
	
	public static int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = 0;
		
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		
		return newPixel;
	}

	public static int getRGB(float blue, float green, float red, float alpha) {
		
		int newRgb = 0;
		
		newRgb = newRgb | (int) blue;
		newRgb = newRgb | ((int) green << 8);
		newRgb = newRgb | ((int) red << 16);
		newRgb = newRgb | ((int) alpha << 24);
		
		return newRgb;
	}

}