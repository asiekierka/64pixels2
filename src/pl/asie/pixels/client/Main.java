package pl.asie.pixels.client;

public class Main {
	private static Client client;
	public static void main(String[] args)
	{
		System.out.println("loading 64pixels...");
		ShutdownHook hook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook( hook );
		client = new Client();
		client.init();
		client.run();
	}

	private static class ShutdownHook extends Thread {
		public void run() {
			System.out.println("VM shutdown happening...");
			client.deinit();
		}
	}
}
