package pl.asie.pixels.client.render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import pl.asie.pixels.client.util.DrawUtils;
import pl.asie.pixels.common.block.Block;
import pl.asie.pixels.common.map.WorldMap;

public class SwingRendererBlockGrid extends JComponent {
	private static final long serialVersionUID = -3799754622092405178L;
	private final int GRID_W, GRID_H;
	private final boolean isDoubled;
	private final CharacterManager chars;
	private WorldMap map;
	private int x, y;
	private boolean shouldUpdate = true;
	
	public SwingRendererBlockGrid(int width, int height, boolean isDoubled, CharacterManager chars) {
		this.GRID_W = width;
		this.GRID_H = height;
		this.isDoubled = isDoubled;
		this.chars = chars;
		
		updateSize();
	}
	
	private void updateSize() {
        Dimension d = new Dimension(getWidth(), getHeight());
        this.setSize(d);
        this.setPreferredSize(d);
	}
	
	public BufferedImage getImageForGridPos(int x, int y, int flags) {
		Block block = map.getBlock(x, y);
		BufferedImage blockImage = block.render(chars);
		if(blockImage == null) { // No custom renderer
			blockImage = chars.get(block.getChar(), block.getColor(), flags);
		} else if(isDoubled && blockImage.getWidth() == 8) { // Scale two times!
			blockImage = DrawUtils.scaleImage2x(blockImage);
		}
		return blockImage;
	}
	
    public void paintComponent(Graphics output)
    {
    	if(!shouldUpdate) return; // Do not need to update.
    	int charSize = getCharSize();
    	int flags = isDoubled ? chars.FLAG_DOUBLED : 0;
    	BufferedImage image = new BufferedImage(GRID_W * charSize, GRID_H * charSize, BufferedImage.TYPE_INT_RGB);
    	Graphics2D graphics = image.createGraphics();
    	for(int y = 0; y < GRID_H; y++) {
    		for(int x = 0; x < GRID_W; x++) {
    			graphics.drawImage(getImageForGridPos(x, y, flags), x*charSize, y*charSize, null);
    		}
    	}
    	output.drawImage(image, 0, 0, null);
    	//shouldUpdate = false; // Clear flag, finish painting.
    }
    
    public int getCharSize() {
    	return isDoubled ? 16 : 8;
    }
	
	public int getBlockWidth() { return GRID_W; }
	public int getBlockHeight() { return GRID_H; }
	public int getWidth() { return GRID_W * getCharSize(); }
	public int getHeight() { return GRID_H * getCharSize(); }
	public WorldMap getWorldMap() { return map; }
	
	public void setWorldMap(WorldMap map) {
		this.map = map;
		shouldUpdate = true;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		shouldUpdate = true;
	}
	
	public void setPositionDelta(int x, int y) {
		setPosition(this.x + x, this.y + y);
	}
}
