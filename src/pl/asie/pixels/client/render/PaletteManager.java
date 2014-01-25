package pl.asie.pixels.client.render;

import java.awt.Color;

public class PaletteManager {
	private final int[] colors;
	
	public PaletteManager(int length) {
		if(length == 16) { // Set default 16-color palette
			colors = new int[] { 0x000000, 0x0000AA, 0x00AA00, 0x00AAAA,
                    0xAA0000, 0xAA00AA, 0xAAAA00, 0xAAAAAA,
                    0x555555, 0x5555FF, 0x55FF55, 0x55FFFF,
                    0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF };
		} else {
			colors = new int[length];
		}
	}

	public Color getColor(int i) {
		return new Color(colors[i]);
	}

	public int getColorAsInteger(int i) {
		return colors[i];
	}

	public int length() {
		return colors.length;
	}
	
	public void setColor(int pos, int color) {
		if(pos >= 0 && pos < length()) {
			colors[pos] = color & 0x00FFFFFF;
		}
	}
	
	public void setColor(int pos, int r, int g, int b) {
		setColor(pos, ((r & 255) << 16) | ((g & 255) << 8) | (b & 255));
	}
}
