import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;

/**
 * Klasa zarzadzajaca logika rysowania i modyfikowania figur geometrycznych.
 */
public class ShapeManager{
    /**
     * Typy figur obslugiwane przez edytor.
     */
    public enum ShapeType{ 
        /** Okrag */
        CIRCLE, 
        /** Prostokat */
        RECTANGLE, 
        /** Wielokat */
        POLYGON, 
        /** Brak figury */
        NONE 
    }
    private ShapeType selectedShape=ShapeType.NONE;

    private Polygon currentPolygon=null;
    private Circle startDot=null;

    private Shape activeShape=null;
    private ContextMenu activeMenu=null;

    private double dragOffsetX=0;
    private double dragOffsetY=0;

    private Pane pane;

    private double startX=0;
    private double startY=0;
    private Shape drawingShape=null;

    /**
     * Tworzy obiekt ShapeManager i inicjalizuje interakcje z panelem.
     * @param p panel sluzacy do wyswietlania figur
     */
    public ShapeManager(Pane p){
        init(p);
    }

    private void init(Pane p){
        this.pane=p;
        setupPaneEvents();
    }

    /**
     * Ustawia wybrany typ figury do narysowania.
     * @param type typ wybranej figury z enum ShapeType
     */
    public void setSelectedShape(ShapeType type){
        this.selectedShape=type;
    }

    private void setupPaneEvents(){
        pane.setOnMousePressed(e ->{
            if(activeMenu!=null&&activeMenu.isShowing()){
                activeMenu.hide();
                return;
            }
            if(e.getY()<30)
                return;
            else if(e.getButton()==javafx.scene.input.MouseButton.PRIMARY){
                if(selectedShape==ShapeType.CIRCLE){
                    startX=e.getX();
                    startY=e.getY();
                    Circle circle=new Circle(startX,startY,0);
                    circle.setFill(Color.BLUE);
                    drawingShape=circle;
                    pane.getChildren().add(circle);
                }else if(selectedShape==ShapeType.RECTANGLE){
                    startX=e.getX();
                    startY=e.getY();
                    Rectangle rectangle=new Rectangle(startX,startY,0,0);
                    rectangle.setFill(Color.GREEN);
                    drawingShape=rectangle;
                    pane.getChildren().add(rectangle);
                }else if(selectedShape==ShapeType.POLYGON){
                    if(currentPolygon==null){
                        currentPolygon=new Polygon();
                        currentPolygon.setFill(Color.RED);
                        currentPolygon.setStroke(Color.RED);
                        currentPolygon.getPoints().addAll(e.getX(),e.getY());
                        pane.getChildren().add(currentPolygon);

                        startDot=new Circle(e.getX(),e.getY(),4);
                        startDot.setFill(Color.BLACK);
                        pane.getChildren().add(startDot);
                    }
                    else{
                        double firstX=currentPolygon.getPoints().get(0);
                        double firstY=currentPolygon.getPoints().get(1);
                        double dist=Math.hypot(e.getX()-firstX,e.getY()-firstY);
                        
                        if(dist<15){
                            currentPolygon.setFill(Color.RED);
                            setupShapeEvents(currentPolygon);
                            currentPolygon=null;
                            pane.getChildren().remove(startDot);
                            startDot=null;
                        }
                        else{
                            currentPolygon.getPoints().addAll(e.getX(),e.getY());
                        }
                    }
                }
            }
        });

        pane.setOnMouseDragged(e ->{
            if(drawingShape!=null){
                if(drawingShape instanceof Circle){
                    ((Circle)drawingShape).setRadius(Math.hypot(e.getX()-startX,e.getY()-startY));
                }else if(drawingShape instanceof Rectangle){
                    Rectangle r=(Rectangle)drawingShape;
                    r.setX(Math.min(startX,e.getX()));
                    r.setY(Math.min(startY,e.getY()));
                    r.setWidth(Math.abs(e.getX()-startX));
                    r.setHeight(Math.abs(e.getY()-startY));
                }
            }
        });

        pane.setOnMouseReleased(e ->{
            if(drawingShape!=null){
                setupShapeEvents(drawingShape);
                drawingShape=null;
            }
        });
    }

    /**
     * Konfiguruje zdarzenia myszy dla podanej figury (przesuwanie, skalowanie, obracanie, zmiana koloru).
     * @param shape figura, dla ktorej maja zostac aktywowane zdarzenia
     */
    public void setupShapeEvents(Shape shape){
        shape.setOnMousePressed(e ->{
            if(e.getButton()==javafx.scene.input.MouseButton.PRIMARY){
                if(activeShape!=null){
                    activeShape.setStrokeWidth(0);
                }
                activeShape=shape;
                activeShape.setStroke(Color.BLACK);
                activeShape.setStrokeWidth(3);

                dragOffsetX=e.getSceneX()-shape.getTranslateX();
                dragOffsetY=e.getSceneY()-shape.getTranslateY();
                e.consume();
            }
        });

        shape.setOnMouseDragged(e ->{
            if(e.getButton()==javafx.scene.input.MouseButton.PRIMARY){
                if(activeShape==shape){
                    shape.setTranslateX(e.getSceneX()-dragOffsetX);
                    shape.setTranslateY(e.getSceneY()-dragOffsetY);
                    e.consume();
                }
            }
        });

        shape.setOnScroll(e ->{
            if(activeShape==shape){
                double zoom=1.1;
                if(e.getDeltaY()<0){
                    zoom=0.9;
                }
                shape.setScaleX(shape.getScaleX()*zoom);
                shape.setScaleY(shape.getScaleY()*zoom);
                e.consume();
            }
        });

        shape.setOnMouseClicked(e ->{
            if(activeShape==shape){
                if(e.getButton()==javafx.scene.input.MouseButton.SECONDARY){
                    if(activeMenu!=null) activeMenu.hide();
                    activeMenu=new ContextMenu();
                    
                    ColorPicker colorPicker=new ColorPicker((Color)shape.getFill());
                    CustomMenuItem colorItem=new CustomMenuItem(colorPicker,false);
                    colorPicker.setOnAction(ev->{
                        shape.setFill(colorPicker.getValue());
                    });
                    
                    activeMenu.getItems().add(colorItem);
                    activeMenu.show(shape,e.getScreenX(),e.getScreenY());
                    e.consume();
                }
                else if(e.getButton()==javafx.scene.input.MouseButton.MIDDLE){
                    shape.setRotate(shape.getRotate()+15);
                    e.consume();
                }
            }
        });
    }
}