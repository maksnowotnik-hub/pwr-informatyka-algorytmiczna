import java.io.*;
import java.net.*;
import java.util.function.Function;

/**
 * Obsluguje pojedyncze polaczenie z klientem w osobnym watku.
 */
public class ClientHandler implements Runnable{
	private Socket socket;

	public ClientHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run(){
		try(
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true)
		){
			String type = in.readLine();
			if(type==null)
				return;
			if(type.equalsIgnoreCase("INTEGER")){
				BinaryTree<Integer> tree = new BinaryTree<Integer>();
				processGeneric(tree, in ,out, Integer::parseInt);
			}
			else if(type.equalsIgnoreCase("DOUBLE")){
				BinaryTree<Double> tree = new BinaryTree<Double>();
				processGeneric(tree, in ,out, Double::parseDouble);
			}
			else if(type.equalsIgnoreCase("STRING")){
				BinaryTree<String> tree = new BinaryTree<String>();
				processGeneric(tree, in ,out, s->s);
			}
			else if(type.equalsIgnoreCase("RECTANGLE")){
				BinaryTree<Rectangle> tree = new BinaryTree<Rectangle>();
				processGeneric(tree, in, out, Rectangle::parse);
			}
		}
		catch(IOException e){
			System.out.println("Blad klienta: " + e.getMessage());
		}
		finally{
			try{
				socket.close();
			}
			catch(IOException e){
				System.out.println("Blad zamykanie gniazda: " + e.getMessage());
			}
		}
	}

	private <T extends Comparable <T>> void processGeneric(BinaryTree<T> tree, BufferedReader in, PrintWriter out, Function<String, T> parse) throws IOException{
		String inputLine;
		while((inputLine=in.readLine())!=null){
			
			if(inputLine.equals("EXIT")){
				System.out.println("Klient o IP: "+socket.getInetAddress().getHostAddress()+" odlaczyl sie.");
				break;
			}

			String[] parts = inputLine.split(" ", 2);
			String command = parts[0].toUpperCase();

			try{
				switch(command){
					case "INSERT":
						if(parts.length>1){
							T val = parse.apply(parts[1]);
							tree.insert(val);
							tree.draw(out);
						}
						else{
							out.println("Blad: podaj wartosc");
						}
						out.println("DONE");
						break;
					case "SEARCH":
						if(parts.length>1){
							T val = parse.apply(parts[1]);
							out.println("Znaleziono: " + tree.search(val));
						}
						else{
							out.println("Blad: podaj wartosc");
						}
						out.println("DONE");
						break;
					case "DELETE":
						if(parts.length>1){
							T val=parse.apply(parts[1]);
							if(tree.search(val)){
								tree.delete(val);
								tree.draw(out);
							}else{
								out.println("Blad: element nie istnieje");
							}
						}
						else{
							out.println("Blad: podaj wartosc");
						}
						out.println("DONE");
						break;
					case "DRAW":
						tree.draw(out);
						out.println("DONE");
						break;
					default:
						out.println("nieznana komenda");
						out.println("DONE");
						break;
				}
			}
			catch(Exception e){
				out.println("Blad przetwarzania");
				out.println("DONE");
			}
		}
	}
}