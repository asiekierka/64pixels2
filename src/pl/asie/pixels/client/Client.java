package pl.asie.pixels.client;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;

import pl.asie.pixels.client.render.CharacterManager;
import pl.asie.pixels.client.render.PaletteManager;
import pl.asie.pixels.client.render.SwingRendererBlockGrid;
import pl.asie.pixels.common.map.WorldMapDebug;

public class Client extends JComponent {
	private static final long serialVersionUID = 1781552095881814198L;

	private JFrame window;
	private SwingRendererBlockGrid grid;
	private CharacterManager chars;
	private PaletteManager palette;
	
	public Client() {
		window = new JFrame("64pixels 0.0.0.1 all over again");
		palette = new PaletteManager(16);
		chars = new CharacterManager(palette, 256);
		grid = new SwingRendererBlockGrid(32, 24, true, chars);
	}
	
	public void init()
	{
		try {
			chars.importBinary("/res/rawcga.bin", 0);
			grid.setWorldMap(new WorldMapDebug());
		} catch(Exception e) { e.printStackTrace(); }
		
		window.add(grid);
		window.pack(); // makes everything a nice size
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void run() {
		while(true) {
			try {
				window.repaint();
				Thread.sleep(33);
			} catch(Exception e) {}
		}
	}

	public void deinit() {
	}
}
