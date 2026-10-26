import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

/**
 * Klasa zarzadzajaca trwalym zapisem i wczytywaniem stanu aplikacji z pliku na dysku.
 */
public class FileManager{

    /**
     * Zapisuje parametry wszystkich figur znajdujacych sie na panelu do pliku tekstowego.
     * @param file wskazany przez uzytkownika plik docelowy
     * @param pane panel z aktualnie narysowanymi figurami
     */
    public static void saveToFile(File file, Pane pane){
        try(PrintWriter writer=new PrintWriter(file)){
            for(Node node:pane.getChildren()){
                if(node instanceof Shape){
                    Shape s=(Shape)node;
                    
                    if(s instanceof Circle&&((Circle)s).getRadius()==4.0) continue;

                    String type=s.getClass().getSimpleName();
                    String color=s.getFill().toString();
                    writer.print(type+";"+color+";"+s.getTranslateX()+";"+s.getTranslateY()+";"+s.getScaleX()+";"+s.getScaleY()+";"+s.getRotate());
                    
                    if(s instanceof Circle){
                        Circle c=(Circle)s;
                        writer.print(";"+c.getCenterX()+";"+c.getCenterY()+";"+c.getRadius());
                    }else if(s instanceof Rectangle){
                        Rectangle r=(Rectangle)s;
                        writer.print(";"+r.getX()+";"+r.getY()+";"+r.getWidth()+";"+r.getHeight());
                    }else if(s instanceof Polygon){
                        Polygon p=(Polygon)s;
                        for(Double pt:p.getPoints()){
                            writer.print(";"+pt);
                        }
                    }
                    writer.println();
                }
            }
        }catch(Exception ex){
            System.out.println("Blad zapisu");
        }
    }

    /**
     * Wczytuje figury z zapisanego pliku tekstowego i odtwarza je na panelu.
     * @param file wskazany przez uzytkownika plik zrodlowy
     * @param pane panel roboczy edytora (zostanie wyczyszczony)
     * @param shapeManager menedzer zarzadzajacy zdarzeniami figur
     */
    public static void loadFromFile(File file, Pane pane, ShapeManager shapeManager){
        try(Scanner scanner=new Scanner(file)){
            pane.getChildren().clear();
            
            while(scanner.hasNextLine()){
                String line=scanner.nextLine();
                if(line.isEmpty()) continue;
                String[] parts=line.split(";");
                Shape s=null;
                
                if(parts[0].equals("Circle")){
                    s=new Circle(Double.parseDouble(parts[7]),Double.parseDouble(parts[8]),Double.parseDouble(parts[9]));
                }else if(parts[0].equals("Rectangle")){
                    s=new Rectangle(Double.parseDouble(parts[7]),Double.parseDouble(parts[8]),Double.parseDouble(parts[9]),Double.parseDouble(parts[10]));
                }else if(parts[0].equals("Polygon")){
                    Polygon p=new Polygon();
                    for(int i=7;i<parts.length;i++){
                        p.getPoints().add(Double.parseDouble(parts[i]));
                    }
                    s=p;
                }
                
                if(s!=null){
                    s.setFill(Color.valueOf(parts[1]));
                    s.setTranslateX(Double.parseDouble(parts[2]));
                    s.setTranslateY(Double.parseDouble(parts[3]));
                    s.setScaleX(Double.parseDouble(parts[4]));
                    s.setScaleY(Double.parseDouble(parts[5]));
                    s.setRotate(Double.parseDouble(parts[6]));
                    
                    shapeManager.setupShapeEvents(s);
                    pane.getChildren().add(s);
                }
            }
        }catch(Exception ex){
             System.out.println("Blad odczytu");
        }
    }
}