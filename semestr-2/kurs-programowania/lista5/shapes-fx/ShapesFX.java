import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Glowna klasa edytora graficznego.
 * Odpowiada za utworzenie okna, paska menu oraz obsluge interfejsu uzytkownika.
 * @author Maksymilian Nowotnik
 */
public class ShapesFX extends Application{

    /**
     * Metoda uruchamiajaca aplikacje JavaFX.
     * @param args argumenty wiersza polecen
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Inicjalizuje glowne okno aplikacji i buduje jego elementy.
     * @param stage glowne okno programu
     */
    @Override
    public void start(Stage stage){
        BorderPane root=new BorderPane();
        
        Pane pane=new Pane();
        pane.setStyle("-fx-background-color: white;");
        
        ShapeManager shapeManager=new ShapeManager(pane);

        MenuBar menuBar=new MenuBar();

        Menu menuFigure=new Menu("Figure");
        MenuItem itemCircle=new MenuItem("Circle");
        MenuItem itemRectangle=new MenuItem("Rectangle");
        MenuItem itemPolygon=new MenuItem("Polygon");

        itemCircle.setOnAction(e -> shapeManager.setSelectedShape(ShapeManager.ShapeType.CIRCLE));
        itemRectangle.setOnAction(e -> shapeManager.setSelectedShape(ShapeManager.ShapeType.RECTANGLE));
        itemPolygon.setOnAction(e -> shapeManager.setSelectedShape(ShapeManager.ShapeType.POLYGON));

        menuFigure.getItems().addAll(itemCircle, itemRectangle, itemPolygon);

        Menu menuFile=new Menu("File");
        MenuItem itemSave=new MenuItem("Save");
        MenuItem itemLoad=new MenuItem("Load");

        itemSave.setOnAction(e ->{
            javafx.stage.FileChooser fc=new javafx.stage.FileChooser();
            java.io.File file=fc.showSaveDialog(stage);
            if(file!=null) FileManager.saveToFile(file,pane);
        });

        itemLoad.setOnAction(e ->{
            javafx.stage.FileChooser fc=new javafx.stage.FileChooser();
            java.io.File file=fc.showOpenDialog(stage);
            if(file!=null) FileManager.loadFromFile(file,pane,shapeManager);
        });

        menuFile.getItems().addAll(itemSave, itemLoad);
        menuBar.getMenus().add(0, menuFile);
        
        Menu menuHelp=new Menu("Help");
        MenuItem itemInstruction=new MenuItem("Instruction");
        itemInstruction.setOnAction(e ->{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instruction");
            alert.setHeaderText("Instrukcja obslugi ShapesFX");
            alert.setContentText("1. Wybierz figure z menu 'Figure'.\n2. Nacisnij lewy przycisk myszy i przeciagnij aby narysowac okrag lub prostokat*\n3. Kliknij lewym przyciskiem myszy na figure aby ustawic ja jako aktywna, co umozliwi wykonywanie z nia wszystkich innych dzialan\n4. Uzyj scroll'a aby powiekszyc figure\n5. Nacisnij scroll na figure aby ja obrocic\n6. Chwyc lewym przyciskiem myszy za figure aby przeniesc ja w inne miejsce\n7. Kliknij prawym przyciskiem myszy na figure, po otwarciu menu wybierz kolor wypelnienia figury\n8. Nacisnij 'File' nastepnie 'Save lub 'Load' aby zapisac lub zaladowac wczesniej zapisany plik\n\n *Rysowanie wielokata:\n1. Wybierz 'Polygon'\n2. Narysuj miejsca wierzcholkow swojego\n3. Nacisnij wierzcholek zaznaczony czarna kropka aby zakonczyc rysowanie figury");
            alert.showAndWait();
        });
        menuHelp.getItems().add(itemInstruction);

        menuBar.getMenus().addAll(menuFigure, menuHelp);
        root.setTop(menuBar);

        Button infoButton=new Button("Info");
        infoButton.setOnAction(e ->{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacje");
            alert.setHeaderText(null);
            alert.setContentText("Nazwa: ShapesFX\nAutor: Maksymilian Nowotnik\nPrzeznaczenie: Edytor graficzny");
            alert.showAndWait();
        });
        root.setBottom(infoButton);        
        
        root.setCenter(pane);

        Scene scene=new Scene(root, 800,600);
        stage.setScene(scene);
        stage.show();
    }
}