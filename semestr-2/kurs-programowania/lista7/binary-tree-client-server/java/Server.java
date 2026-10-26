import java.io.*;
import java.net.*;

/**
 * Serwer nasluchujacy na polaczenia od klientow na porcie 6666.
 */
public class Server{
	public static void main(String[] args){
		int port = 6666;

		try(ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Serwer dziala i nasluchuje na porcie: " + port);

			while(true){
				Socket clientSocket = serverSocket.accept();
				System.out.println("Podlaczono nowego klienta z IP: " + clientSocket.getInetAddress());

				ClientHandler handler = new ClientHandler(clientSocket);
				new Thread(handler).start();
			}
		}
		catch(IOException e){
			System.out.println("Blad serwera: " + e.getMessage());
		}
	}
}
