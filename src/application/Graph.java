package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, City> cityMap; //this map  stores cities by name
    private Map<City, List<Adjacent>> adjacents;
    private Map<City, Double> distances;

    public Graph() {
        cityMap = new HashMap<>();
        adjacents = new HashMap<>();
        distances = new HashMap<>();
    }

    public void addCity(City city) {
        cityMap.put(city.getName(), city); // Add the city to the map by name
        adjacents.put(city, new LinkedList<>());
        distances.put(city, Double.POSITIVE_INFINITY);
    }

    public void addAdjacent(City source, City destination, double weight) {
        adjacents.get(source).add(new Adjacent(source, destination, weight));
    }

    public Map<City, List<Adjacent>> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(Map<City, List<Adjacent>> adjacents) {
        this.adjacents = adjacents;
    }

    public Map<City, Double> getDistances() {
        return distances;
    }

    public void setDistances(Map<City, Double> distances) {
        this.distances = distances;
    }

    // New method to get a city by name
    public City getCityByName(String cityName) {
        return cityMap.get(cityName);
    }
 // Initialize distances map
 	// Method to initialize distances from a source city
// 	This method initializes the distances map. It sets the distance of each city from the source city to positive infinity, 
// 	except for the source city, which is set to 0.
 	public void initializeDistances(City start) {
 		distances = new HashMap<>();
 		for (City city : adjacents.keySet()) {
 			distances.put(city, Double.POSITIVE_INFINITY);
 		}
 		distances.put(start, 0.0);
 	}
    // New method to add a city to the graph
    public void add(City city, List<Adjacent> adjacentList) {
        cityMap.put(city.getName(), city);
        adjacents.put(city, adjacentList);
        distances.put(city, Double.POSITIVE_INFINITY);
    }
}
