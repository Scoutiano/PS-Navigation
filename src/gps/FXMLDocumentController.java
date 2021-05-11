/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

import static gps.GPS.root;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Mohanad
 */
public class FXMLDocumentController implements Initializable {
    
    static LinkedList<Edge>[] cities;
    static ObservableList<City> citiesOL = FXCollections.observableArrayList();
    static LinkedList<Circle> points = new LinkedList<Circle>();
    static LinkedList<Circle> clickedPoints = new LinkedList<Circle>();
    static City previousTarget;
    
    /////////////Components for stage///////////////
    static Stage primaryStage = new Stage();
    static AnchorPane linePathAP = new AnchorPane();
    static AnchorPane ap = new AnchorPane();
    static Scene scene = new Scene(ap);
    
    Label sourceLbl = new Label("Source:");
    Label targetLbl = new Label("Target:");
    Label pathLbl = new Label("Path:");
    TextArea pathTA = new TextArea();
    ImageView mapIV = new ImageView();
    Button findPathBtn = new Button("Drive Safe");
    
    
    ComboBox<City> sourceCB = new ComboBox(citiesOL);
    ComboBox<City> targetCB = new ComboBox(citiesOL);
    /////////////////////////////////////////////////
    
    public void initializeStage(){
        
        ap.setPrefHeight(960.0);
        ap.setPrefWidth(919.0);
        ap.setStyle("-fx-background-color: #282828;");
        
        sourceLbl.setLayoutX(653.0);
        sourceLbl.setLayoutY(53.0);
        sourceLbl.setText("Source:");
        sourceLbl.setTextFill(javafx.scene.paint.Color.WHITE);
        
        targetLbl.setLayoutX(655.0);
        targetLbl.setLayoutY(169.0);
        targetLbl.setText("Target:");
        targetLbl.setTextFill(javafx.scene.paint.Color.WHITE);
        
        mapIV.setFitHeight(961.0);
        mapIV.setFitWidth(583.0);
        mapIV.setPickOnBounds(true);
        mapIV.setPreserveRatio(true);
        mapIV.setImage(new Image(getClass().getResource("resources/images/PalestineMap.png").toExternalForm()));
        
        findPathBtn.setLayoutX(655.0);
        findPathBtn.setLayoutY(297.0);
        findPathBtn.setMnemonicParsing(false);
        findPathBtn.setOnAction(e -> {
            findPath();
        });
        findPathBtn.setPrefHeight(31.0);
        findPathBtn.setPrefWidth(242.0);
        findPathBtn.setTextFill(javafx.scene.paint.Color.valueOf("#009703"));
        
        sourceCB.setLayoutX(737.0);
        sourceCB.setLayoutY(48.0);
        sourceCB.setPrefHeight(31.0);
        sourceCB.setPrefWidth(158.0);
        sourceCB.setStyle("-fx-background-color: #484848;");
        sourceCB.setItems(citiesOL);
        sourceCB.valueProperty().addListener(new ChangeListener<City>() {
            @Override
            public void changed(ObservableValue<? extends City> observable, City oldValue, City newValue) {
                
                if(oldValue == null){
                    points.get(newValue.cityID).setFill(Color.web("#00b300"));
                    return;
                }
                
                points.get(oldValue.cityID).setFill(Color.BLACK);
                points.get(newValue.cityID).setFill(Color.web("#00b300"));
            }
        });
        
        targetCB.setLayoutX(737.0);
        targetCB.setLayoutY(164.0);
        targetCB.setPrefHeight(31.0);
        targetCB.setPrefWidth(158.0);
        targetCB.setStyle("-fx-background-color: #484848; -fx-border-color: #484848;");
        targetCB.setItems(citiesOL);
        targetCB.valueProperty().addListener(new ChangeListener<City>() {
            @Override
            public void changed(ObservableValue<? extends City> observable, City oldValue, City newValue) {
                
                if(oldValue == null){
                    points.get(newValue.cityID).setFill(Color.web("#00b300"));
                    return;
                }
                
                points.get(oldValue.cityID).setFill(Color.BLACK);
                points.get(newValue.cityID).setFill(Color.web("#00b300"));
            }
        });
        
        pathTA.setLayoutX(653.0);
        pathTA.setLayoutY(480.0);
        pathTA.setPrefHeight(458.0);
        pathTA.setPrefWidth(240.0);
        pathTA.setEditable(false);
        pathTA.setStyle("-fx-background-color: #484848; -fx-control-inner-background: #484848; -fx-text-fill: #FFFFFF;");
        
        pathLbl.setLayoutX(653.0);
        pathLbl.setLayoutY(451.0);
        pathLbl.setText("Path:");
        pathLbl.setTextFill(javafx.scene.paint.Color.WHITE);

        findPathBtn.setLayoutX(655.0);
        findPathBtn.setLayoutY(266.0);
        findPathBtn.setMnemonicParsing(false);
        findPathBtn.setPrefHeight(80.0);
        findPathBtn.setPrefWidth(240.0);
        findPathBtn.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #484848;");
        findPathBtn.setText("Find Path");
        findPathBtn.setFont(new Font(24.0));
        
        ap.getChildren().add(sourceLbl);
        ap.getChildren().add(targetLbl);
        ap.getChildren().add(mapIV);
        ap.getChildren().add(findPathBtn);
        ap.getChildren().add(sourceCB);
        ap.getChildren().add(targetCB);
        ap.getChildren().add(linePathAP);
        ap.getChildren().add(pathLbl);
        ap.getChildren().add(pathTA);
        
        scene.getStylesheets().add("gps/resources/css/Style.css");
        
        primaryStage.setTitle("Navigation");
        primaryStage.getIcons().add(new Image(GPS.class.getResourceAsStream("resources/images/Icon.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            readCities();
            readRoads();
            
            sourceCB.setItems(citiesOL);
            targetCB.setItems(citiesOL);
            
            Circle testCircle = new Circle(5,5,10000);
            root.getChildren().add(testCircle);
            
            initializeStage();
            
            markCities();
            markCityNames();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Read cities from file and load into Hashtable
    public void readCities() throws FileNotFoundException, IOException {

        //Define file
        File file = new File("src/gps/resources/data/Cities.txt");

        //Read from file and load cities into Hashtable
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        
        int numberOfLines = 0;
        while ((st = br.readLine()) != null) {
            numberOfLines++;
        }
        br.close();
        br = new BufferedReader(new FileReader(file));
        
        cities = new LinkedList[numberOfLines];
        
        int count = 0;
        while ((st = br.readLine()) != null) {
            
            //LinkedList to add head node as the main city
            LinkedList<Edge> currentCity = new LinkedList<>();

            //Tokenize and get variables to define City
            String[] tokens = st.split(",");
            String cityName = tokens[0];
            double longitude = Double.parseDouble(tokens[1]);
            double latitude = Double.parseDouble(tokens[2]);
            double distance = 0;
            
            City city = new City(cityName,count, longitude, latitude,false);
            Edge edge = new Edge(city,distance);
            
            //Add City as head node for currentCity
            currentCity.add(edge);

            
            cities[count] = currentCity;

            //Add to ObservableList for UI purposes
            citiesOL.add(city);
            
            count++;
        }

    }

    public void readRoads() throws FileNotFoundException, IOException {
        //Define file
        File file = new File("src/gps/resources/data/Roads.txt");

        //Read from file and load cities into Hashtable
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            
            
            
            //Tokenize and get variables to define City
            String[] tokens = st.split(",");
            
            String cityAName = tokens[0];
            String cityBName = tokens[1];
            double distance = Double.parseDouble(tokens[2]);
            
            int cityAID = -1;
            int cityBID = -1;
            for(int i = 0 ; i < cities.length; i++){
                if(cities[i].get(0).city.cityName.compareTo(cityAName) == 0){
                    cityAID = i;
                }
                
                if(cities[i].get(0).city.cityName.compareTo(cityBName) == 0){
                    cityBID = i;
                }
            }
            
            Edge cityAEdge = new Edge(cities[cityAID].get(0).city,distance);
            Edge cityBEdge = new Edge(cities[cityBID].get(0).city,distance);
            
            cities[cityAID].add(cityBEdge);
            cities[cityBID].add(cityAEdge);
        }
    }
    
    public Container dijkstraSP(int s,int e){
        
        //previous to be able to reconstruct path
        int[] previous = new int[cities.length];
        
        //distance to keep track of shortest distance from source node to each node
        double[] distance = new double[cities.length];
        
        //Initialize the three arrays
        for(int i = 0; i < cities.length; i++){
            
            previous[i] = -1;
            distance[i] = Double.MAX_VALUE;
        }
        
        //MinHeap as priority queue to take most promising node (lowest cost)
        MinHeap queue = new MinHeap(cities.length);
        
        //Setting starting node
        distance[s] = 0;
        queue.insert(new Edge(cities[s].get(0).city,0));
        
        while(queue.size() != 0){
            
            //Get lowest cost node
            Edge currentEdge = queue.remove();
            City currentCity = currentEdge.city;
            
            double currentDistance = currentEdge.distance;
            
            //If node has been visited before at a lower cost, skip this path
            if(distance[currentCity.cityID] < currentDistance){
                continue;
            }
            
            //Get list of neighbouring nodes corresponding to the current node
            LinkedList<Edge> neighbours = cities[currentCity.cityID];
            
            if(neighbours.size() == 0){
                return new Container(previous,distance);
            }
            
            for(int i = 0; i < neighbours.size(); i++){
                
                //Get neighbouring node
                Edge currentNeighbourEdge = neighbours.get(i);
                City currentNeighbourCity = currentNeighbourEdge.city;
                double currentNeighbourDistance = currentNeighbourEdge.distance;
                double newDistance = currentNeighbourDistance + distance[currentCity.cityID];
                
                if(newDistance < distance[currentNeighbourCity.cityID]){
                    previous[currentNeighbourCity.cityID] = currentCity.cityID;
                    distance[currentNeighbourCity.cityID] = newDistance;
                    queue.insert(new Edge(currentNeighbourCity,newDistance));
                }
            }
        }
        
        return new Container(previous,distance);
    }
    
    public int[] buildPath(int s,int e,int[] previous){
        
        boolean isFound = false;
        
        //Generate path from parent array
        LinkedList<Integer> pathReverse = new LinkedList<>();
        for (int current = e; current != -1; current = previous[current]) {
            if (current == s) {
                pathReverse.add(current);
                isFound = true;
                break;
            }
            pathReverse.add(current);
        }
        int[] path = new int[pathReverse.size()];
        
        if(!isFound){
            return null;
        }
        
        for(int i = 0; i < path.length; i++){
            path[i] = pathReverse.get(path.length - i - 1);
        }
        return path;
    }
    
    public void markCities() {

        //Circle[] points = new Circle[cities.length];
        points = new LinkedList<Circle>();
        
        
        for (int i = 0; i < cities.length; i++) {
            
            
            
            points.add(new Circle(0, 0, 4.5));
            points.get(i).setStroke(Color.BLACK);
            points.get(i).setFill(Color.BLACK);
            points.get(i).setOnMouseClicked(e -> {
                
                Circle circle = (Circle)e.getSource();
                if(clickedPoints.size() == 0){
                    clickedPoints.add(circle);
                    sourceCB.getSelectionModel().select(cities[Integer.parseInt(circle.getStyle())].get(0).city);
                } else if (clickedPoints.size() == 1){
                    if(circle == clickedPoints.get(0)){
                        return;
                    }
                    clickedPoints.add(circle);
                    targetCB.getSelectionModel().select(cities[Integer.parseInt(circle.getStyle())].get(0).city);
                } else {
                    
                    if(circle == clickedPoints.get(0) || circle == clickedPoints.get(1)){
                        return;
                    }
                    
                    targetCB.getSelectionModel().select(cities[Integer.parseInt(circle.getStyle())].get(0).city);
                    sourceCB.getSelectionModel().select(cities[Integer.parseInt(clickedPoints.get(1).getStyle())].get(0).city);
                    clickedPoints.add(circle);
                    clickedPoints.removeFirst();
                }
            });
            
            points.get(i).setStyle(Integer.toString(i));
            
            AnchorPane.setBottomAnchor(points.get(i), (cities[i].get(0).city.y - 8) * -1);
            AnchorPane.setLeftAnchor(points.get(i), cities[i].get(0).city.x - 3);
            
            points.get(i).layoutYProperty().set(961);
            points.get(i).toFront();
            
            ap.getChildren().add(points.get(i));
        }
    }
    
    public void markCityNames() {
        LinkedList<Text> pointNames = new LinkedList<Text>();

        for (int i = 0; i < cities.length; i++) {
            pointNames.add(new Text(cities[i].get(0).city.cityName));
            pointNames.get(i).setFill(Color.GREEN);
            pointNames.get(i).setStroke(Color.BLACK);
            pointNames.get(i).setFont(new Font(10));
            pointNames.get(i).setStrokeWidth(0.2);
            pointNames.get(i).setMouseTransparent(true);
            
            AnchorPane.setBottomAnchor(pointNames.get(i), (cities[i].get(0).city.y - 15) * -1);
            AnchorPane.setLeftAnchor(pointNames.get(i), cities[i].get(0).city.x - 15);
            
            pointNames.get(i).layoutYProperty().set(961);
            pointNames.get(i).toFront();
            ap.getChildren().add(pointNames.get(i));
        }
    }

    //create a line path based on the generated path
    public void linePath(int[] path) {
        
        if(path == null){
            return;
        }
        
        linePathAP.getChildren().clear();
        Line[] linePath = new Line[path.length - 1];
        for (int i = 1; i < path.length; i++) {
            
            //Get coordainates of each city at this road
            int cityAID = path[i - 1];
            int cityBID = path[i];

            City cityA = cities[cityAID].get(0).city;
            City cityB = cities[cityBID].get(0).city;

            //Create line between cities in the path
            
            linePath[i - 1] = new Line(cityA.x, cityA.y, cityB.x, cityB.y);
            linePath[i - 1].toFront();
            linePath[i - 1].layoutYProperty().set(961);
            linePathAP.getChildren().add(linePath[i - 1]);
        }

    }
    
    
    public void findPath(){
        City cityA = sourceCB.getSelectionModel().getSelectedItem();
        City cityB = targetCB.getSelectionModel().getSelectedItem();
        
        if(cityA == null || cityB == null){
            return;
        }
        
        if(cityA.cityID == cityB.cityID){
            String cityName = cities[cityA.cityID].get(0).city.cityName;
            pathTA.setText(cityName + " 0 KM");
            //linePathAP.getChildren().clear();
            return;
        }
        
        Container container = dijkstraSP(cityA.cityID,cityB.cityID);
        int[] path = buildPath(cityA.cityID,cityB.cityID,container.previous);
        
        pathToString(path,container.distance);
        linePath(path);
    }
    
        //Turns the resulted path from LinkedList to String
    public void pathToString(int[] path, double[] distance) {
        String result = "";
        
        if(path == null){
            pathTA.setText("No path found");
            return;
        }
        
        for (int i = 0; i < path.length; i++) {
            if (i == path.length-1) {
                result = result + cities[path[i]].get(0).city.cityName + " " + String.format("%.2f",distance[path[i]]);
            } else {
                result = result + cities[path[i]].get(0).city.cityName + " " + String.format("%.2f",distance[path[i]]) + " KM\n" ;
            }
        }
        
        pathTA.setText(result);
    }
    
}
