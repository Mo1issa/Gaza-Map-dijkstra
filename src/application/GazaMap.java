package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GazaMap extends Application {
	public static FileChooser fileChooser;
	public static File file; // The input file
	public static ArrayList<City> cityArray = new ArrayList<>();
	public static ArrayList<City> streetsArray = new ArrayList<>();
	private List<Line> trackLineList = new ArrayList<>();
	private List<Polygon> arrowheadList = new ArrayList<>();
	static int mouseClick = 0;
	static int total = 0;
	Background ground;
	Color fixedColor;
	public static Image map;
	public static double minLat = 31.0;
	static double maxLat = 32.0;
	static double minLng = 34.0;
	static double maxLng = 35.0;
	GridPane browserPane;
	static Pane mapImagePane = new Pane();
	static Pane root = new Pane();
	HashMap<String, City> cityMap = new HashMap<>();
	Graph cityGraph = new Graph();
	ComboBox<City> source = new ComboBox<>();
	ComboBox<City> target = new ComboBox<>();
	Line trackLine = new Line();
	public static ArrayList<Line> trackLines = new ArrayList<>();

	// static Pane root;

	@Override
	public void start(Stage stage) throws Exception {

		fixedColor = Color.web("#353535");
		map = new Image("Gaza.png");

		// Creating grid pane
		browserPane = new GridPane();
		browserPane.setAlignment(javafx.geometry.Pos.CENTER);
		browserPane.setPadding(new Insets(100, 210, 100, 210));
		browserPane.setHgap(10.5);
		browserPane.setVgap(10.5);

		// Set the background image
		String imagePath = "freepalestine.png";
		Image backgroundImage = new Image(imagePath);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
		Background backgroundWithImage = new Background(background);
		// Set the background to the GridPane
		browserPane.setBackground(backgroundWithImage);
		// Label Greeting User
		Label wel = new Label("Insert the file of Map cityArrays Palesea!");
		// Label Greeting User

		// Set font and styles
		wel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		wel.setStyle("-fx-background-color: Black; -fx-text-fill: red; -fx-padding: 10px; -fx-background-insets: 0;");

		// wel.setTextFill(Color.WHITE);
		browserPane.add(wel, 0, 0);

		// User Button to browse file
		Button browserBtn = new Button("Browse");
		browserBtn.setFont(Font.font(14));
		browserBtn.setStyle("-fx-background-radius: 18, 7;-fx-background-color:lightgreen;");
		browserBtn.setTextFill(fixedColor);
		browserBtn.setPrefSize(100, 30);
		browserPane.add(browserBtn, 3, 0);
		browserBtn.setOnAction(d -> {
			Stage stage3 = new Stage();
			fileChooser = new FileChooser();
			file = fileChooser.showOpenDialog(stage3);
			if (file == (null)) {
				showAlert(Alert.AlertType.ERROR, "Error", "Operation Failed", "You haven't chosen a file yet!", null);
				System.out.println("null pointer");
			} else {
				if (readFile(file) == -1) {
					showAlert(Alert.AlertType.ERROR, "Error", "Operation Failed", "Number of cityArrays doesn't match",
							null);
				} else {
					MapMapStage();
				}
			}
		});

		// User button to exit
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setFont(Font.font(14));
		cancelBtn.setTextFill(fixedColor);
		cancelBtn.setPrefSize(100, 30);
		cancelBtn.setStyle("-fx-background-radius: 18, 7;-fx-background-color:Red;");
		browserPane.add(cancelBtn, 4, 0);
		cancelBtn.setOnAction(e -> javafx.application.Platform.exit());

		// Create Scene
		Scene scene = new Scene(browserPane);
		// Set minimum size for the stage
		stage.setMinWidth(400);
		stage.setMinHeight(650);
		stage.setScene(scene);
		stage.setTitle("file Broswer");
		stage.getIcons().add(new Image("Hamas.png"));
		stage.show();
	}

	private void MapMapStage() {
		Stage mapStage = new Stage();
		BorderPane pane2 = new BorderPane();
		pane2.setPadding(new Insets(20, 10, 20, 10));
		pane2.setBackground(ground);

		GridPane mapPane = new GridPane();
		mapPane.setPadding(new Insets(20, 10, 20, 10));
		mapPane.setHgap(17);
		mapPane.setVgap(17);
		pane2.setRight(mapPane);
		// Label Greeting User
		Label MapML = new Label("Gaza Map");
		MapML.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		MapML.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		MapML.setTextFill(fixedColor);
		mapPane.add(MapML, 0, 0);

		BackgroundFill groundf1 = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
		Background ground1 = new Background(groundf1);

		Label cityl = new Label("Choose City by:");
		cityl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		cityl.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		cityl.setTextFill(fixedColor);
		mapPane.add(cityl, 0, 1);
		// Set the background GIF
		String gifPath = "file:C:\\Users\\HP\\Downloads\\state-of-palestine-694_256.gif";
		Image backgroundImage = new Image(gifPath);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background backgroundWithGif = new Background(background);
		// Set the background to the GridPane
		mapPane.setBackground(backgroundWithGif);

		ComboBox<String> choice = new ComboBox<>();
		choice.setPrefHeight(30);
		choice.setPrefWidth(150);
		choice.setBackground(ground1);
		choice.getItems().add("List");
		choice.getItems().add("Click");
		choice.setValue("Click");
		mapPane.add(choice, 1, 1);

		Label sourcel = new Label("Source:");
		sourcel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		sourcel.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		sourcel.setTextFill(fixedColor);
		mapPane.add(sourcel, 0, 2);

		source.setPrefHeight(30);
		source.setPrefWidth(150);
		source.setBackground(ground1);
		mapPane.add(source, 1, 2);

		// This label will be updated by the process of file importing
		Label targetl = new Label("Target:");
		targetl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		targetl.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		mapPane.add(targetl, 0, 3);

		target.setPrefHeight(30);
		target.setPrefWidth(150);
		target.setBackground(ground1);
		mapPane.add(target, 1, 3);

		// This label will be updated by the process of file importing
		Label pathLabel = new Label("Shortest Path:");
		pathLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		pathLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		TextArea path = new TextArea();
		path.setPrefHeight(200);
		path.setPrefWidth(150);
		path.setEditable(false);

        // Set text, style, and font
		path.setStyle("-fx-control-inner-background: lightblue;");
		path.setFont(Font.font("Arial", 12));

		mapPane.add(path, 1, 8);

		// This label will be updated by the process of file importing
		Label distl = new Label("Distance:");
		distl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		distl.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");

		mapPane.add(distl, 0, 9);

		TextField dist = new TextField();
		dist.setPrefHeight(30);
		dist.setPrefWidth(150);
		dist.setEditable(false);
		mapPane.add(dist, 1, 9);

		// User button to reset map
		Button reset = new Button("Clear");
		reset.setFont(Font.font(14));
		reset.setPrefSize(100, 30);
		GridPane.setHalignment(reset, HPos.CENTER);
		reset.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		mapPane.add(reset, 1, 4);

		reset.setOnAction(l -> {
		    for (City city : cityArray) {
		        city.getR().setSelected(false);
		        city.getLabel().setTextFill(Color.BLACK);
		        mouseClick = 0;
		        path.setText("");
		        dist.setText("");

		        root.getChildren().removeAll(trackLineList);
		        root.getChildren().removeAll(arrowheadList);

		        // Clear the lists
		        trackLineList.clear();
		        arrowheadList.clear();
		    }
		});
		
		// User button to run the shortest path
		Button runBtn = new Button("Run");
		runBtn.setFont(Font.font(14));
		runBtn.setPrefSize(100, 30);
		runBtn.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		mapPane.add(runBtn, 0, 4);
		

		for (int i = 0; i < cityArray.size();i++) {
			final int index=i;
			cityArray.get(i).getR().setOnAction(e->{
				mouseClick++;
				if(mouseClick==1) {
					source.setValue(cityArray.get(index));
				}
				else {
					target.setValue(cityArray.get(index));
					
				}
			});
		}
		runBtn.setOnAction(l -> {

			ArrayList<City> list = new ArrayList<>(2);
			

			if (choice.getSelectionModel().getSelectedItem().equals("List")) {
				City sourceCity = search(source.getSelectionModel().getSelectedItem().getName());
				City targetCity = search(target.getSelectionModel().getSelectedItem().getName());

				if (sourceCity != null && targetCity != null) {
					list.add(0, sourceCity);
					list.add(1, targetCity);

					if (list.size() >= 2) {
						list.get(0).getR().fire();
						list.get(1).getR().fire();
					}
				}
			}

			else if (choice.getSelectionModel().getSelectedItem().equals("Click")) {
				for (City city : cityArray) {
					if (city.getR().isSelected()) {
					
						list.add(city);
					
						mouseClick++;
					}
					
					
				}

				if (mouseClick == 2) {
					City startObj = list.get(0);
					City targetObj = list.get(1);
					source.setValue(startObj);
					target.setValue(targetObj);
				} else {
					reset.fire();
				}

						} else {
				showAlert(Alert.AlertType.ERROR, "Please Choose choice", "Warning!", "Click or List", null);
			}
			if (source.getSelectionModel().getSelectedItem() != null
					&& target.getSelectionModel().getSelectedItem() != null) {

				cityGraph.initializeDistances(source.getSelectionModel().getSelectedItem());
				Dijkstra mydijks = new Dijkstra();
				List<City> visited = mydijks.getShortestPath(source.getSelectionModel().getSelectedItem(),
						target.getSelectionModel().getSelectedItem(), cityGraph);
				if (visited != null) {
					path.appendText("this is the path from source to destination:" + '\n');
					for (int i = 0; i < visited.size() - 1; i++) {
						City current = visited.get(i);
						City next = visited.get(i + 1);

						path.appendText("from-> " + current.getName() + " to-> " + next.getName() + '\n');
						trackLineWithArrow(current, next);

					}
					dist.setText(cityGraph.getDistances().get(target.getValue()) + " KM");

				} else {
					showAlert(Alert.AlertType.ERROR, " ", "No path ", "warning ", null);
				}

			} else
				showAlert(Alert.AlertType.ERROR, "Please Choose Countries", "Warning !", "", null);
		});

		// User button to exit
		Button cancel = new Button("Exit");
		cancel.setFont(Font.font(14));
		cancel.setPrefSize(100, 30);
		GridPane.setHalignment(cancel, HPos.CENTER);
		cancel.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		mapPane.add(cancel, 1, 10);
		cancel.setOnAction(l -> javafx.application.Platform.exit());

		System.out.println("Image loaded successfully. Width: " + map.getWidth() + ", Height: " + map.getHeight());

		// Assuming 'map' is your Image
		ImageView v = new ImageView(map);
		v.setFitWidth(750);
		v.setFitHeight(790);
		v.setStyle("-fx-border-radius: 18, 7;");

		// Create a Pane to hold the ImageView
//		mapImagePane = new Pane(v);
		mapImagePane.getChildren().add(v);
		mapImagePane.getChildren().add(root);
		// Add the Pane to the GridPane
//		pane2.add(mapImagePane, 0, 0);
		pane2.setLeft(mapImagePane);

		for (City city : cityArray) {

			System.out.println("???");
			addCityToMap(root, map, city);
		}
		// Set the preferred width and height for the stage
		double preferredWidth = 1100; // Adjust this value as needed
		double preferredHeight = 790; // Adjust this value as needed

		mapStage.setTitle("Map Map");
		mapStage.getIcons().add(new Image("Hamas.png"));

		// Create a Scene with the preferred width and height
		Scene scene = new Scene(pane2, preferredWidth, preferredHeight);
		scene.getStylesheets().add("style.css");
		mapStage.setMaxWidth(preferredWidth);
		mapStage.setMaxHeight(preferredHeight);

		// Set the scene to the stage
		mapStage.setScene(scene);

		// Show the stage
		mapStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	private void trackLineWithArrow(City c1, City c2) {
	    System.out.println("paint image");
	    double arrowSize = 10;
	    double arrowWidth = 5;
	    Point2D pixelPositionC1 = GazaMap.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c1.getLat(),
	            c1.getLng());
	    Point2D pixelPositionC2 = GazaMap.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c2.getLat(),
	            c2.getLng());

	    trackLine = new Line();
	    trackLine.setStartX(pixelPositionC1.getX());
	    trackLine.setStartY(pixelPositionC1.getY());
	    trackLine.setEndX(pixelPositionC2.getX());
	    trackLine.setEndY(pixelPositionC2.getY());

	    trackLine.setFill(Color.GREEN);
	    trackLine.setSmooth(true);
	    trackLine.setStroke(Color.GREEN);
	    trackLine.setStrokeWidth(5);

	    // Creating arrowhead shape
	    double endX = trackLine.getEndX();
	    double endY = trackLine.getEndY();

	    double angle = Math.atan2((endY - trackLine.getStartY()), (endX - trackLine.getStartX()));
	    double sin = Math.sin(angle);
	    double cos = Math.cos(angle);

	    double arrowheadX1 = endX - arrowSize * cos + arrowWidth * sin;
	    double arrowheadY1 = endY - arrowSize * sin - arrowWidth * cos;
	    double arrowheadX2 = endX - arrowSize * cos - arrowWidth * sin;
	    double arrowheadY2 = endY - arrowSize * sin + arrowWidth * cos;

	    Polygon arrowhead = new Polygon(endX, endY, arrowheadX1, arrowheadY1, arrowheadX2, arrowheadY2);
	    arrowhead.setFill(trackLine.getStroke());

	    // Store references to the line and arrowhead
	    trackLineList.add(trackLine);
	    arrowheadList.add(arrowhead);

	    root.getChildren().addAll(trackLine, arrowhead);
	}

//	private void TrackingLines(City c1, City c2) {
//		System.out.println("paint image");
//
//		Point2D pixelPositionC1 = GazaMap.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c1.getLat(),
//				c1.getLng());
//		Point2D pixelPositionC2 = GazaMap.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c2.getLat(),
//				c2.getLng());
//
//		trackLine = new Line();
//		trackLine.setStartX(pixelPositionC1.getX());
//		trackLine.setStartY(pixelPositionC1.getY());
//		trackLine.setEndX(pixelPositionC2.getX());
//		trackLine.setEndY(pixelPositionC2.getY());
//
//		trackLine.setFill(Color.GREEN);
//		trackLine.setSmooth(true);
//		trackLine.setStroke(Color.GREEN);
//		trackLine.setStrokeWidth(5);
//		trackLines.add(trackLine);
//		root.getChildren().add(trackLine);
//	}
//
// calculate image set
	static Point2D calculatePixelPosition(Image image, double minLat, double maxLat, double minLng, double maxLng,
			double cityLat, double cityLng) {
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();

		// Adjust these scale factors to control the image size and position spacing
		double imageScale = 0.62; // Experiment with this value to control the overall image size
		double spacingScale = 4.65; // Experiment with this value to control the position spacing

		// Calculate the center of the scaled map in pixel coordinates
		int centerX = (int) (imageWidth * 1.6123 * imageScale);
		int centerY = (int) (imageHeight * 0.49 * imageScale);

		// Calculate the center of the latitude and longitude of the cityArrays
		double centerLat = (maxLat + minLat) / 2.0;
		double centerLng = (maxLng + minLng) / 2.0;

		// Calculate the percentage of latitude and longitude for the city relative to
		// the center
		double latPercentage = (cityLat - centerLat) / (maxLat - minLat);
		double lngPercentage = (cityLng - centerLng) / (maxLng - minLng);

		// Calculate pixel position relative to the center with adjusted scaling
		double x = centerX + lngPercentage * imageWidth * imageScale * spacingScale;
		double y = centerY - latPercentage * imageHeight * imageScale * spacingScale;

		return new Point2D(x, y);
	}

	private void addCityToMap(Pane root, Image map, City city) {
		// Calculate pixel position for the city

		Point2D pixelPosition = calculatePixelPosition(map, 30.0, 32.0, 33.5, 35.5, city.getLat(), city.getLng());

		city.getR().setLayoutX(pixelPosition.getX());
		city.getR().setLayoutY(pixelPosition.getY());
	}

	public static City search(String city) {
		for (City cityl : cityArray) {
			if (cityl.getName().equals(city)) {
				return cityl;
			}
		}
		return null;
	}

	private int readFile(File file) {
		Graph graph = new Graph();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			// Read the first line to get the number of cityArrays and adjacents
			String[] sizes = reader.readLine().trim().split(",");
			int numcityArrays = Integer.parseInt(sizes[0]);
			int numAdjacents = Integer.parseInt(sizes[1]);

			// Read city information and add them to the graph
			for (int i = 0; i < numcityArrays; i++) {
				String[] cityInfo = reader.readLine().trim().split(",");
				if (cityInfo.length != 3) {
					showAlert(Alert.AlertType.ERROR, "Error", "Invalid Data", "Invalid data format  , Skipping...",
							null);
					continue;
				}

				String cityName = cityInfo[0];
				System.out.println(cityName);
				double cityLat;
				double cityLng;

				try {
					cityLat = Double.parseDouble(cityInfo[1]);
					cityLng = Double.parseDouble(cityInfo[2]);
				} catch (NumberFormatException e) {
					showAlert(Alert.AlertType.ERROR, "Error", "Invalid Data",
							"Invalid latitude or longitude in line . Skipping...", cityName);
					continue;
				}

				// Create City object and add it to the list
				City city = new City(cityName, cityLat, cityLng);
				cityArray.add(city);
				graph.add(city, new ArrayList<>());
				cityMap.put(cityInfo[0], city);
				cityGraph.addCity(city);

				if (!cityName.startsWith("c")) {
					source.getItems().add(city);
					target.getItems().add(city);
				}
			}
			// Read adjacent information and add them to the graph
			for (int i = 0; i < numAdjacents; i++) {
				String[] adjacentInfo = reader.readLine().trim().split(",");
				String sourceCityName = adjacentInfo[0];
				String destinationCityName = adjacentInfo[1];
				City sourceCity = graph.getCityByName(sourceCityName);
				City destinationCity = graph.getCityByName(destinationCityName);

				// Check if either sourceCity or destinationCity is null before proceeding
				if (sourceCity != null && destinationCity != null) {
					// Correct
					DecimalFormat format = new DecimalFormat("#.##");
					double distance1 = Double.parseDouble(format.format(distance(sourceCity, destinationCity)));
					cityGraph.addAdjacent(sourceCity, destinationCity, distance1);
					System.out.println(
							sourceCity.getName() + " " + destinationCity.getName() + " Distance: " + distance1 + " km");
				} else {
					System.out.println("City not found in cityMap: " + sourceCityName + " or " + destinationCityName);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "Error", "File Reading Error", "Error reading the file.", null);
			System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrror");
			return -1;
		}
		return 0;
	}

	private static double distance(City startCountry, City endCountry) {

		double startLat = startCountry.getLat();
		double startLong = startCountry.getLng();
		double endLat = endCountry.getLat();
		double endLong = endCountry.getLng();

		int EARTH_RADIUS = 6371;

		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

	private static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	private static void showAlert(Alert.AlertType alertType, String title, String header, String content,
			String string) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	static Point2D calculatePixelPosition1(Image image, double minLat, double maxLat, double minLng, double maxLng,
			double cityLat, double cityLng) {
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();

		// Adjust these scale factors to control the image size and position spacing
		double imageScale = 0.4; // Experiment with this value to control the overall image size
		double spacingScale = 2.5; // Experiment with this value to control the position spacing

		// Calculate the center of the scaled map in pixel coordinates
		int centerX = (int) (imageWidth * 0.5 * imageScale);
		int centerY = (int) (imageHeight * 0.5 * imageScale);

		// Calculate the center of the latitude and longitude of the cityArrays
		double centerLat = (maxLat + minLat) / 2.0;
		double centerLng = (maxLng + minLng) / 2.0;

		// Calculate the percentage of latitude and longitude for the city relative to
		// the center
		double latPercentage = (cityLat - centerLat) / (maxLat - minLat);
		double lngPercentage = (cityLng - centerLng) / (maxLng - minLng);

		// Calculate pixel position relative to the center with adjusted scaling
		double x = centerX + lngPercentage * imageWidth * imageScale * spacingScale;
		double y = centerY - latPercentage * imageHeight * imageScale * spacingScale;

		return new Point2D(x, y);
	}

}
