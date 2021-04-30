
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.ArrayList;

public class app extends Application {

    Stage stage;
    Scene mainScene;
    Scene stopScene;
    Scene shortestPathsScene;
    Scene arrivalTimeScene;

    String stopsPath;
    String transfersPath;
    String timesPath;

    File stopsFile;
    File transfersFile;
    File timesFile;

    ArrayList<String> errors;

    static tripFinder tFinder;

    public static void main(String[] args) throws Exception {
        // init classes
        tFinder = new tripFinder();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        mainScene = getMainScene();
        stopScene = getStopFinderScene();
        shortestPathsScene = getShortestPathsScene();
        arrivalTimeScene = getTripFinderScene();

        stage = new Stage();
        stage.setScene(mainScene);
        stage.show();

    }

    private Scene getMainScene() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(100, 12.5, 13.5, 14.5));

        Label title = new Label("Welcome To Our Information Service");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        // Creating button1
        Button button1 = new Button("Search by bus stop");
        button1.setMinWidth(150);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(stopScene);
            }
        });

        // Creating button2
        Button button2 = new Button("Search by route");
        button2.setMinWidth(150);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(shortestPathsScene);
            }
        });

        // Creating button3
        Button button3 = new Button("Search by arrival time");
        button3.setMinWidth(150);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(arrivalTimeScene);
            }
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setVgap(25);
        flowPane.setPadding(new Insets(30));
        flowPane.getChildren().addAll(button1, button2, button3);

        Button exitBtn = new Button("Exit");
        BorderPane.setAlignment(exitBtn, Pos.BASELINE_CENTER);

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Platform.exit();
            }
        });

        root.setTop(title);
        root.setCenter(flowPane);
        root.setBottom(exitBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }

    private Scene getShortestPathsScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(100, 12.5, 13.5, 14.5));

        Label title = new Label("Search By Route");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(mainScene);
            }
        });

        root.setTop(title);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }

    private Scene getStopFinderScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(100, 12.5, 13.5, 14.5));

        Label title = new Label("Search By Bus Stop");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(mainScene);
            }
        });

        root.setTop(title);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }

    private Scene getTripFinderScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(0, 0, 0, 0));

        Label title = new Label("Search By Arrival Time");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(mainScene);
            }
        });

        // Main pane
        FlowPane mainPane = new FlowPane();
        mainPane.setMinWidth(400);
        mainPane.setOrientation(Orientation.VERTICAL);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.TOP_CENTER);
        mainPane.setColumnHalignment(HPos.CENTER);
        mainPane.setRowValignment(VPos.CENTER);
        mainPane.setVgap(20);

        // Input row
        FlowPane inputRow = new FlowPane();
        inputRow.setAlignment(Pos.TOP_CENTER);
        inputRow.setColumnHalignment(HPos.CENTER);
        inputRow.setRowValignment(VPos.CENTER);
        inputRow.setHgap(25);

        // Input label
        Label timeInputLabel = new Label("Input Arrival Time: ");
        timeInputLabel.setTextFill(Color.WHITE);
        timeInputLabel.setStyle("-fx-font: 15px Verdana;");

        // Input
        TextField timeInput = new TextField();
        timeInput.setText("00:00:00");
        timeInput.setMaxWidth(80);

        inputRow.getChildren().addAll(timeInputLabel, timeInput);

        // Search button
        Button searchBtn = new Button("Search");

        // Output
        GridPane outputTitle = new GridPane();
        GridPane output = new GridPane();
        output.setStyle("-fx-background-color: #2e2e2e");
        output.setMaxHeight(400);

        mainPane.getChildren().addAll(inputRow, searchBtn);
        ScrollPane sp = new ScrollPane();
        sp.setContent(output);
        sp.setPrefSize(750, 600);
        output.setPrefWidth(1000);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

        // Display output on click
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                output.getChildren().clear();
                if (mainPane.getChildren().contains(sp)) {
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                }

                String input = timeInput.getText();
                System.out.println(input);
                String[] result = tFinder.getStopByArival(input);

                if (result != null) {

                    String title = "Trip ID, Arrival Time, Departure Time, Stop ID, Stop Sequence, Stop Headsign, Pickup Type, Drop Off Type, Shape Dist";

                    for (int i = 0; i < 9; i++) {
                        String titleEntry = title.split(",")[i];
                        Label titleLabel = new Label(titleEntry);
                        titleLabel.setStyle("-fx-font: 10px Verdana");
                        titleLabel.setTextFill(Color.WHITE);
                        titleLabel.setPrefWidth(100);
                        titleLabel.setAlignment(Pos.CENTER);
                        outputTitle.add(titleLabel, i, 0);
                    }

                    for (int i = 0; i < result.length; i++) {

                        String entry = "";
                        String[] a = result[i].split(",");
                        int len = a.length;

                        for (int j = 0; j < len; j++) {
                            entry = result[i].split(",")[j];
                            Label entryLabel = new Label(entry);
                            entryLabel.setStyle("-fx-font: 10px Verdana;");
                            entryLabel.setTextFill(Color.WHITE);
                            entryLabel.setPrefWidth(100);
                            entryLabel.setAlignment(Pos.CENTER);
                            output.add(entryLabel, j, i);
                        }
                    }
                    mainPane.getChildren().addAll(outputTitle, sp);
                } else {
                    System.out.println("something went wrong");
                }

            }

        });

        root.setTop(title);
        root.setCenter(mainPane);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }
}
