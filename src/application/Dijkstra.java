package application;

import java.util.*;

public class Dijkstra {

	public Dijkstra() {
	}

	public List<City> getShortestPath(City source, City destination, Graph graph) {
		myPriorityQueue<City> queue = new myPriorityQueue<>(new Comparator<City>() {
	        @Override
	        public int compare(City city1, City city2) {
	            return (int) (graph.getDistances().get(city1) - graph.getDistances().get(city2));
	        }
	    });

	    queue.add(source);
	    graph.getDistances().put(source, 0.0);

	    Map<City, City> previous = new HashMap<>();

	    while (!queue.isEmpty()) {
	        City current = queue.poll();

	        // Stop the algorithm if the destination is reached
	        if (current.equals(destination)) {
	            break;
	        }

	        for (Adjacent adjacent : graph.getAdjacents().get(current)) {
	            City adjacentCity = adjacent.getDestination();
	            double distance = adjacent.getDistance();
	            if (graph.getDistances().get(adjacentCity) > graph.getDistances().get(current) + distance) {
	                graph.getDistances().put(adjacentCity, graph.getDistances().get(current) + distance);
	                previous.put(adjacentCity, current);
	                queue.add(adjacentCity);
	            }
	        }
	    }

	    if (previous.get(destination) == null) {
	        return null;
	    }

	    List<City> path = new ArrayList<>();
	    City current = destination;
	    while (current != source) {
	        path.add(current);
	        current = previous.get(current);
	    }
	    path.add(source);
	    Collections.reverse(path);

	    return path;
	}

}
