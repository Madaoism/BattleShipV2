package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GamingServer extends Thread {

	int port = 12323;

	boolean disconnect;


	public void run(){
		try {
		ServerSocket serv = new ServerSocket(port);
		Socket sock = null;

		while (true) {
			sock = serv.accept();

			new ClientHandler(sock).start();
			if (disconnect) {
				break;
			}

		}

		serv.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class ClientHandler extends Thread {

		private Socket client = null;

		public ClientHandler(Socket sock) {
			client = sock;

		}

		public void run() {
			try {
				PrintStream out = new PrintStream(client.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				String reply = in.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
