package pl.asie.pixels.client.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class DrawUtils {
	public static final AffineTransformOp SCALE_DOUBLE_OP;
	
	static
	{
        AffineTransform scale2 = new AffineTransform();
        scale2.scale(2, 2);
        SCALE_DOUBLE_OP = new AffineTransformOp(scale2, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}
	
	public static BufferedImage scaleImage2x(BufferedImage original) {
		BufferedImage image = new BufferedImage(original.getWidth() * 2, original.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
        ((Graphics2D)image.getGraphics()).drawImage(original, DrawUtils.SCALE_DOUBLE_OP, 0, 0);
        return image;
	}
}
