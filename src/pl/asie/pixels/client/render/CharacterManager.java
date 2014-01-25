package pl.asie.pixels.client.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import pl.asie.pixels.client.util.DrawUtils;
import pl.asie.pixels.common.interfaces.ICharacterManager;

public class CharacterManager implements ICharacterManager {
	public final int CHAR_LENGTH;
	public final int COLOR_LENGTH;
	public final int FLAG_DOUBLED;
	public final PaletteManager palette;
	
	private final byte[] chars;
	private final HashMap<Integer, BufferedImage> charImages;
	
	public CharacterManager(PaletteManager palette, int chars) {
		CHAR_LENGTH = chars;
		COLOR_LENGTH = palette.length();
		
		// Flags
		FLAG_DOUBLED = CHAR_LENGTH * COLOR_LENGTH;
		
		// Misc
		this.chars = new byte[CHAR_LENGTH * 8];
		this.charImages = new HashMap<Integer, BufferedImage>();
		this.palette = palette;
	}

	public void importBinary(File charset, int offset) throws IOException, InvalidCharsetException {
		int length = (int)charset.length();
		if(length % 8 != 0 || length == 0 || length > (chars.length - (offset*8))) {
			throw new InvalidCharsetException();
		}
		FileInputStream input = new FileInputStream(charset);
		input.read(chars, offset * 8, length);
		input.close();
		for(int i = 0; i < (length >> 3); i++) { // for every char
			invalidate(offset + i);
		}
	}
	
	public void importBinary(String resourceName, int offset) throws IOException, InvalidCharsetException {
		InputStream input = getClass().getResourceAsStream(resourceName);
		int length = (int)input.available();
		if(length % 8 != 0 || length == 0 || length > (chars.length - (offset*8))) {
			throw new InvalidCharsetException();
		}
		input.read(chars, offset * 8, length);
		input.close();
		for(int i = 0; i < (length >> 3); i++) { // for every char
			invalidate(offset + i);
		}
	}
	
	private void invalidate(int chr) {
		for(int color = 0; color < COLOR_LENGTH; color++) {
			int location = chr + (color * CHAR_LENGTH);
			charImages.remove(location);
			charImages.remove(location | FLAG_DOUBLED);
		}
	}
	
	private BufferedImage draw(int chr, int color, int flags) {
		if((flags & FLAG_DOUBLED) > 0) { // Draw doubled
			return DrawUtils.scaleImage2x(getTransparent(chr, color, (flags & ~FLAG_DOUBLED))); // Get 1x version and scale it
		} else {
			BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
			int fgColor = 0xFF000000 | palette.getColorAsInteger(color & 15);
	        int[] charPixels = new int[64];
	        int yPos = chr * 8;
	        for(int cPos = 0; cPos < 64; cPos += 8) {
	        	byte cgaValue = chars[yPos];
	        	for(int cgaPos = 7; cgaPos >= 0; cgaPos--) {
	        		if((cgaValue & 1) == 1) {
	        			charPixels[cPos + cgaPos] = fgColor;
	        		}
	        		cgaValue >>= 1;
	        	}
	        	yPos++;
	        }
	        image.getRaster().setDataElements(0,0,8,8,charPixels);
			return image;
		}
	}
	
	public BufferedImage getTransparent(int chr, int color, int flags) {
		int location = chr + ((color & 15) * CHAR_LENGTH) + flags;
		BufferedImage fgImage = null;
		if(charImages.containsKey(location)) fgImage = charImages.get(location);
		else fgImage = draw(chr, color & 15, flags);
		return fgImage;
	}

	public BufferedImage get(int chr, int color, int flags) {
		BufferedImage fgImage = getTransparent(chr, color, flags);
		BufferedImage image = new BufferedImage(fgImage.getWidth(), fgImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageG = image.createGraphics();
		imageG.drawImage(fgImage, 0, 0, palette.getColor(color >> 4), null);
		return image;
	}
}
