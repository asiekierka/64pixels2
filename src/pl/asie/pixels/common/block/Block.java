package pl.asie.pixels.common.block;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;

import pl.asie.pixels.common.interfaces.ICharacterManager;
import pl.asie.pixels.common.util.Utils;

public class Block {
	private final int x, y;
	private int type, chr, color;
	
	public Block(int x, int y, byte[] serializedData) {
		this.x = x;
		this.y = y;
		this.deserialize(serializedData);
	}
	
	public Block(int x, int y, int type, int chr, int color) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.chr = chr;
		this.color = color;
	}

	public void deserialize(byte[] serializedData) {
		DataInputStream data = Utils.getDataInputStream(serializedData);
		try {
			type = data.readUnsignedShort();
			chr = data.readUnsignedShort();
			color = data.readUnsignedByte();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getType() { return type; }
	public int getChar() { return chr; }
	public int getColor() { return color; }
	
	/**
	 * Override this function if you want to use a custom renderer.
	 * @param characterManager The CharacterManager you can request characters from.
	 * @return A BufferedImage containing the requested character, of width 8 and height 8.
	 */
	public BufferedImage render(ICharacterManager characterManager) {
		return null;
	}
}
