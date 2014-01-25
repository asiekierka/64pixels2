package pl.asie.pixels.common.map;

import pl.asie.pixels.common.block.Block;

public abstract class WorldMap {
	public abstract Block getBlock(int x, int y);
}
