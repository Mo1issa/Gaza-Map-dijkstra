package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class City {
	private String name;
	private double lat;
	private double lng;
	private ArrayList<Adjacent> adjacents = new ArrayList<>();;
	private RadioButton radio;
	private Label CityLabel;

	public City(String name, double latitude, double longitude) {
		this.name = name;
		this.lat = latitude;
		this.lng = longitude;
		LabeldeclerationToMap(GazaMap.map, 31.0, 32.0, 34.0, 35.0);

	}

	void LabeldeclerationToMap(Image gazaMap, double minLat, double maxLat, double minLng, double maxLng) {
		this.CityLabel = new Label(getName());
		this.CityLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
		this.CityLabel.setTextFill(Color.BLACK);

		Tooltip tip = new Tooltip(this.getName());
		tip.setFont(new Font(16));
		tip.setStyle("-fx-background-color: grey;");
		this.CityLabel.setTooltip(tip);

		this.radio = new RadioButton();

		Image image = new Image("flag.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(17);
		imageView.setFitWidth(16);

		this.CityLabel.setPadding(new Insets(-5));
		this.CityLabel.setGraphic(imageView);

		Point2D pixelPosition = GazaMap.calculatePixelPosition(gazaMap, minLat, maxLat, minLng, maxLng, this.getLat(),
				this.getLng());

		if (!getName().startsWith("c")) {
			// Create a VBox to contain the label and radio button
			VBox cityContainer = new VBox();
			cityContainer.setLayoutX(pixelPosition.getX());
			cityContainer.setLayoutY(pixelPosition.getY());

			// Set layout coordinates for the radio button
			this.radio.setLayoutX(20); // Adjust the X offset as needed
			this.radio.setLayoutY(0);

			// Add the label and radio button to the VBox
			cityContainer.getChildren().addAll(this.CityLabel, this.radio);

			// Add the VBox to the root
			GazaMap.root.getChildren().add(cityContainer);
		}
	}

	void ShapDeclaration(Image map, double minLat, double maxLat, double minLng, double maxLng,
			Circle... linkedCircles) {
		this.CityLabel = new Label(getName());
		this.CityLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 1));
		this.CityLabel.setTextFill(Color.BLACK);

		Tooltip tip = new Tooltip(this.getName());
		tip.setFont(new Font(16));
		tip.setStyle("-fx-background-color: grey;");
		this.CityLabel.setTooltip(tip);

		// Create a small point using a Circle
		Circle point = new Circle();
		point.setRadius(2); // Adjust the radius as needed
		point.setFill(Color.RED); // Set the color of the small point

		this.CityLabel.setPadding(new Insets(-5));

		// Set the graphic to the label
		this.CityLabel.setGraphic(point);

		// Set initial layout position
		Point2D pixelPosition = GazaMap.calculatePixelPosition1(map, minLat, maxLat, minLng, maxLng, this.getLat(),
				this.getLng());
		this.CityLabel.setLayoutX(pixelPosition.getX());
		this.CityLabel.setLayoutY(pixelPosition.getY());

		// Add the label to the root
		GazaMap.root.getChildren().add(this.CityLabel);
		GazaMap.root.getChildren().add(this.radio);

		// Linking Polyline
		Polyline linkingPolyline = createPolyline(linkedCircles);
		GazaMap.root.getChildren().add(linkingPolyline);
	}

	private Polyline createPolyline(Circle... circles) {
		Polyline polyline = new Polyline();
		for (Circle circle : circles) {
			// Ensure the circle has been added to the root before accessing its position
			GazaMap.root.getChildren().add(circle);

			// Add the center coordinates of the circle to the polyline
			polyline.getPoints().addAll(circle.getCenterX(), circle.getCenterY());
		}
		polyline.setStroke(Color.RED);
		polyline.setStrokeWidth(3);
		return polyline;
	}

	public List<Adjacent> getAdjacents() {
		return adjacents;
	}

	public void setAdjacents(List<Adjacent> adjacents) {
		this.adjacents = (ArrayList<Adjacent>) adjacents;
	}

	// method to add an adjacent city
	public void addAdjacentCity(City city, City adjacentCity, double distance) {
		this.adjacents.add(new Adjacent(city, city, distance));
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", lat=" + lat + ", lng=" + lng + ", adjacents=" + adjacents;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public City() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RadioButton getR() {
		return radio;
	}

	public void setR(RadioButton r) {
		this.radio = r;
	}

	public Label getLabel() {
		return CityLabel;
	}

	public void setLabel(Label label) {
		this.CityLabel = label;
	}

}
