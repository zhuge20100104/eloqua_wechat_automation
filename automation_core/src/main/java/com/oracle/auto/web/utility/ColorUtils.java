/**
 * 
 */
package com.oracle.auto.web.utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by @author madirv on Jul 7, 2014
 *
 */
public class ColorUtils extends Color {

	static ColorUtils colorUtils = null;

	private ColorUtils(int r, int g, int b) {
		super(r, g, b);
	}
	private ColorUtils(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	public static ColorUtils getInstance(String rgb) {
		List<Integer> colors = getRGB(rgb);
		colorUtils = new ColorUtils(colors.get(0), colors.get(1), colors.get(2));
		return colorUtils;
	}

	public static ColorUtils getRgbaInstance(String rgba) {
		List<Integer> colors = getRGB(rgba);
		colorUtils = new ColorUtils(colors.get(0), colors.get(1), colors.get(2), colors.get(3));
		return colorUtils;
	}

	/**
	 * Returns the HEX value representing the colour in the default sRGB
	 * ColorModel.
	 * 
	 * @return the HEX value of the colour in the default sRGB ColorModel
	 */
	public String getHex() {
		return toHex(getRed(), getGreen(), getBlue());
	}

	public String getRgbaHex() {
		return toHex(getRed(), getGreen(), getBlue(),getAlpha());
	}

	/**
	 * Returns a web browser-friendly HEX value representing the colour in the
	 * default sRGB ColorModel.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 * @return a browser-friendly HEX value
	 */
	public String toHex(int r, int g, int b) {
		return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
				+ toBrowserHexValue(b);
	}

	public String toHex(int r, int g, int b, int a) {
		return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
				+ toBrowserHexValue(b) + toBrowserHexValue(a);
	}

	private String toBrowserHexValue(int number) {
		StringBuilder builder = new StringBuilder(
				Integer.toHexString(number & 0xff));
		while (builder.length() < 2) {
			builder.append("0");
		}
		return builder.toString().toUpperCase();
	}

	private static List<Integer> getRGB(String rgb) {
		rgb = rgb.replaceAll("rgba", "").replaceAll("\\(", "")
				.replaceAll("\\)", "");
		StringTokenizer st = new StringTokenizer(rgb, ",");
		List<Integer> colors = new ArrayList<Integer>();
		while (st.hasMoreElements()) {
			colors.add(Integer.parseInt(st.nextElement().toString().trim()));
		}
		return colors;
	}

}
