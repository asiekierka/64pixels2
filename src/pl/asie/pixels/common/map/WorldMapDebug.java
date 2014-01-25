package pl.asie.pixels.common.map;

import java.util.Random;

import pl.asie.pixels.common.block.Block;

public class WorldMapDebug extends WorldMap {
	private static final Random rand = new Random();
	
	@Override
	public Block getBlock(int x, int y) {
		return new Block(x, y, 0, rand.nextInt(256), rand.nextInt(256));
	}
}
