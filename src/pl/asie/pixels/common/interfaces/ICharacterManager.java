package pl.asie.pixels.common.interfaces;

import java.awt.image.BufferedImage;

public interface ICharacterManager {
	public BufferedImage get(int chr, int color, int flags);
	public BufferedImage getTransparent(int chr, int color, int flags);
}
