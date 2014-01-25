package pl.asie.pixels.common.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Utils {
	public static DataInputStream getDataInputStream(byte[] data) {
		return new DataInputStream(new ByteArrayInputStream(data));
	}
}
