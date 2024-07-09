package application;

public class Adjacent {

	private City source;
	private City destination;
	private double distance;

	public Adjacent() {

	}

	public Adjacent(City source, City destination, double distance) {
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}

	public City getSource() {
		return source;
	}

	public void setSource(City source) {
		this.source = source;
	}

	public City getDestination() {
		return destination;
	}

	public void setDestination(City destination) {
		this.destination = destination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Edge{" + "source=" + source.getName() + ", destination=" + destination.getName() + ", weight="
				+ distance + '}';
	}
}
