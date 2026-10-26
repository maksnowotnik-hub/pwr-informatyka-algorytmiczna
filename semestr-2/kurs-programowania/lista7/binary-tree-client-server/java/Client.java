import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * Aplikacja kliencka z interfejsem graficznym (JavaFX).
 */
public class Client extends Application{
	private PrintWriter out;
	private BufferedReader in;
	private Socket socket;

	private Stage window;
	private Scene typeScene, mainScene;
	private TextArea treeDisplay;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		window.setTitle("Drzewo Binarne - Klient");

		Label typeLabel = new Label ("Wybierz typ drzewa: ");
		Button buttonInt = new Button ("INTEGER");
		Button buttonDouble = new Button ("DOUBLE");
		Button buttonString = new Button ("STRING");
		Button buttonRect = new Button("RECTANGLE");

		buttonInt.setOnAction(e -> connectAndStart("INTEGER"));
		buttonDouble.setOnAction(e -> connectAndStart("DOUBLE"));
		buttonString.setOnAction(e -> connectAndStart("STRING"));
		buttonRect.setOnAction(e -> connectAndStart("RECTANGLE"));

		HBox typeButtons = new HBox(10, buttonInt, buttonDouble, buttonString, buttonRect);
		typeButtons.setAlignment(Pos.CENTER);

		VBox typeLayout = new VBox(20, typeLabel, typeButtons);
		typeLayout.setAlignment(Pos.CENTER);

		typeScene = new Scene(typeLayout, 400, 200);

		TextField inputField = new TextField();
		inputField.setPromptText("Podaj wartosc: ");

		Button buttonInsert = new Button ("INSERT");
		Button buttonSearch = new Button ("SEARCH");
		Button buttonDelete = new Button ("DELETE");
		Button buttonDraw = new Button ("DRAW");

		buttonInsert.setOnAction(e ->{
			sendCommand("INSERT "+inputField.getText());
			inputField.clear();
		});
		
		buttonSearch.setOnAction(e ->{
			sendCommand("SEARCH "+inputField.getText());
			inputField.clear();
		});
		
		buttonDelete.setOnAction(e ->{
			sendCommand("DELETE "+inputField.getText());
			inputField.clear();
		});
		
		buttonDraw.setOnAction(e ->{
			sendCommand("DRAW");
			inputField.clear();
		});

		HBox controlLayout = new HBox(10, inputField, buttonInsert, buttonSearch, buttonDelete, buttonDraw);
		controlLayout.setPadding(new Insets(10));
		controlLayout.setAlignment(Pos.CENTER);

		treeDisplay = new TextArea();
		treeDisplay.setEditable(false);
		treeDisplay.setFont(Font.font("Monospaced", 14));

		VBox mainLayout = new VBox(10, controlLayout, treeDisplay);
		mainLayout.setPadding(new Insets(10));
		VBox.setVgrow(treeDisplay, Priority.ALWAYS);

		mainScene = new Scene(mainLayout, 600, 450);

		window.setOnCloseRequest(e ->{
			if(out!=null){
				out.println("EXIT");
			}
			try{
				if(socket!=null){
					socket.close();
				}
			}catch(IOException ex){
				System.out.println("Wystąpił problem przy zamykaniu gniazda: " + ex.getMessage());
			}
			Platform.exit();
		});

		window.setScene(typeScene);
		window.show();
	}

	private void connectAndStart(String type){
		try{
			socket = new Socket("localhost", 6666);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(type);

			window.setScene(mainScene);
			sendCommand("DRAW");
		}
		catch(IOException e){
			Alert alert = new Alert(Alert.AlertType.ERROR, "Nie mozna polaczyc z serwerem!");
			alert.showAndWait();
		}
	}

	private void sendCommand(String command){
		if(out==null || in==null){
			return;
		}

		out.println(command);

		try{
			StringBuilder responseBuilder = new StringBuilder();
			String line;

			while((line=in.readLine())!=null){
				if(line.equals("DONE")){
					break;
				}
				responseBuilder.append(line).append("\n");
			}
			treeDisplay.setText(responseBuilder.toString());
		}
		catch(IOException e){
			treeDisplay.setText("Blad komunikacji z serwerem");
		}
	}
}