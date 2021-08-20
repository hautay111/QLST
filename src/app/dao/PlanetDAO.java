package app.dao;

import app.model.Planet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class PlanetDAO {
 
    public static ObservableList<Planet> getPlanetList() {
        Planet mercury = new Planet("MER", "Mercury");
        Planet venus = new Planet("VEN", "Venus");
        Planet earth = new Planet("EAR", "Earth");
 
        ObservableList<Planet> list //
                = FXCollections.observableArrayList(mercury, venus, earth);
 
        return list;
    }
    
}