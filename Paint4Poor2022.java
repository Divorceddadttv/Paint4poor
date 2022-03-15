import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.input.*;


public class Paint4Poor2022 extends Application {
  // Anfang Attribute
  private Pane root;                // global definiert für späteren Zugriff
  private Pixel[][] leinwand;       // Ein Array aus erweiterten Buttons
  private Color[][] bild;           // Ein reines Farb-Array
  private String grundStyle = "-fx-border-width: 0;-fx-background-radius: 0;-fx-border-color:LIGHTGRAY;-fx-border-insets: 0;-fx-border-radius: 0;"; // für alle Pixel 
  private Color aktuelleFarbe = Color.BLACK; 
  private boolean quadrat = false;
  private boolean firstClick = true;
  private int lastX = 0;
  private int lastY = 0;
  private ToggleGroup toggleGroup1 = new ToggleGroup();
  private ToggleGroup toggleGroup2 = new ToggleGroup();
  private RadioButton radioButtonSchwarz = new RadioButton();
  private RadioButton radioButtonWeiss = new RadioButton();
  private RadioButton radioButtonRot = new RadioButton();
  private RadioButton radioButtonGruen = new RadioButton();
  private RadioButton radioButtonQuadrat = new RadioButton();
  private RadioButton radioButtonPixel = new RadioButton();
  // Ende Attribute

  
  
  // Belegt die beiden Arrays leinwand und bild mit Startwerten
  public void generiereLeinwand(int spalten, int zeilen, int pixelbreite) {
    int linkerRand = 10;
    int obererRand = 10;
    String pixelStyle;
    leinwand = new Pixel[spalten][zeilen];
    bild = new Color[spalten][zeilen];
    for (int y = 0; y < zeilen; y++) {
      for (int x = 0; x < spalten; x++) {
        bild[x][y] = Color.WHITE;
        leinwand[x][y] = new Pixel(x, y);
        leinwand[x][y].setLayoutX(linkerRand + x * pixelbreite);
        leinwand[x][y].setLayoutY(obererRand + y * pixelbreite);
        leinwand[x][y].setPrefHeight(pixelbreite);
        leinwand[x][y].setPrefWidth(pixelbreite);
        pixelStyle = grundStyle + "-fx-background-color: #" + leinwand[x][y].getFarbe().toString().substring(2)+";";
        leinwand[x][y].setStyle(pixelStyle);                      
        leinwand[x][y].setOnAction(
        (event) -> {leinwand_Action(event);} 
        );
        root.getChildren().add(leinwand[x][y]);
      }
    }
  }
  
  
  // Das wird alles einmal beim Starten ausgeführt
  public void start(Stage primaryStage) { 
    root = new Pane();
    Scene scene = new Scene(root, 640, 508);

    // Anfang Komponenten
    
    radioButtonSchwarz.setSelected(true);
    radioButtonSchwarz.setLayoutX(500);
    radioButtonSchwarz.setLayoutY(17);
    radioButtonSchwarz.setPrefHeight(17);
    radioButtonSchwarz.setPrefWidth(96);
    radioButtonSchwarz.setText("schwarz");
    radioButtonSchwarz.setToggleGroup(toggleGroup1);
    radioButtonSchwarz.setOnAction(
      (event) -> {farbwahl_Action(event);} 
    );
    root.getChildren().add(radioButtonSchwarz);
    
    radioButtonWeiss.setLayoutX(500);
    radioButtonWeiss.setLayoutY(44);
    radioButtonWeiss.setPrefHeight(17);
    radioButtonWeiss.setPrefWidth(96);
    radioButtonWeiss.setText("weiß");
    radioButtonWeiss.setToggleGroup(toggleGroup1);
    radioButtonWeiss.setOnAction(
      (event) -> {farbwahl_Action(event);} 
    );
    root.getChildren().add(radioButtonWeiss);
    
    radioButtonRot.setLayoutX(500);
    radioButtonRot.setLayoutY(71);
    radioButtonRot.setPrefHeight(17);
    radioButtonRot.setPrefWidth(96);
    radioButtonRot.setText("rot");
    radioButtonRot.setToggleGroup(toggleGroup1);
    radioButtonRot.setOnAction(
      (event) -> {farbwahl_Action(event);} 
    );
    root.getChildren().add(radioButtonRot);
    
    radioButtonGruen.setLayoutX(500);
    radioButtonGruen.setLayoutY(95);
    radioButtonGruen.setPrefHeight(17);
    radioButtonGruen.setPrefWidth(96);
    radioButtonGruen.setText("grün");
    radioButtonGruen.setToggleGroup(toggleGroup1);
    radioButtonGruen.setOnAction(
      (event) -> {farbwahl_Action(event);} 
    );
    root.getChildren().add(radioButtonGruen);
    
    radioButtonQuadrat.setLayoutX(500);
    radioButtonQuadrat.setLayoutY(150);
    radioButtonQuadrat.setPrefHeight(17);
    radioButtonQuadrat.setPrefWidth(96);
    radioButtonQuadrat.setText("Rechteck");
    radioButtonQuadrat.setToggleGroup(toggleGroup2);
    radioButtonQuadrat.setOnAction(
      (event) -> {Malart_Action(event);} 
    );
    root.getChildren().add(radioButtonQuadrat);
    
    radioButtonPixel.setLayoutX(500);
    radioButtonPixel.setLayoutY(200);
    radioButtonPixel.setPrefHeight(17);
    radioButtonPixel.setPrefWidth(96);
    radioButtonPixel.setText("Pixel");
    radioButtonPixel.setToggleGroup(toggleGroup2);
    radioButtonPixel.setOnAction(
      (event) -> {Malart_Action(event);} 
    );
    root.getChildren().add(radioButtonPixel);
    
    // erzeugen des "Array of Button" sowie initialiseren von bild[][]
    generiereLeinwand(16,16,30);
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("Paint4Poor2022");
    primaryStage.setScene(scene);
    primaryStage.show();
  } 
  // Anfang Methoden
  
  
  // wenn irgendein Button der Leinwand gedrückt wird
  public void leinwand_Action(Event evt) {
    if (quadrat == false) {
      int x = ((Pixel) evt.getSource()).getX();
      int y = ((Pixel) evt.getSource()).getY();
      leinwand[x][y].setFarbe(aktuelleFarbe);
      leinwand[x][y].setStyle(grundStyle + "-fx-background-color: #" + leinwand[x][y].getFarbe().toString().substring(2)+";"); 
      bild[x][y] = aktuelleFarbe;
    } else if (quadrat == true) {
      if (firstClick == true) {
        lastX = ((Pixel) evt.getSource()).getX();
        lastY = ((Pixel) evt.getSource()).getY();     
        firstClick = false;   
      } else if (firstClick == false) {
        int nowx = ((Pixel) evt.getSource()).getX();
        int nowy = ((Pixel) evt.getSource()).getY();
          for (int y = lastY; y < nowy ; y++) {
            for (int x = lastX; x < nowx; x++) {
            leinwand[x][y].setFarbe(aktuelleFarbe);
            leinwand[x][y].setStyle(grundStyle + "-fx-background-color: #" + leinwand[x][y].getFarbe().toString().substring(2)+";"); 
            bild[x][y] = aktuelleFarbe;
          }
        }
        firstClick = true;
      }
    }
  } 
  
  
  // wenn einer der Farbwahl-Radiobuttons gedrückt wird
  public void farbwahl_Action(Event evt) {
    if (radioButtonSchwarz.isSelected()) {
      aktuelleFarbe = Color.BLACK; 
    } else if (radioButtonWeiss.isSelected()) {       
      aktuelleFarbe = Color.WHITE; 
    } else if (radioButtonRot.isSelected()) {
      aktuelleFarbe = Color.RED;
    } else if (radioButtonGruen.isSelected()) {
      aktuelleFarbe = Color.GREEN;
    }
  }
  public void Malart_Action(Event evt){
    if (radioButtonQuadrat.isSelected()) {
      quadrat = true;
    } else if (radioButtonPixel.isSelected()) {
      quadrat = false; 
    }
  }
  
  // Hauptprogramm
  public static void main(String[] args) {
    launch(args);
  } 
  // Ende Methoden
  
} 

