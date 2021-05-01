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
import javafx.scene.text.TextAlignment;
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
        stopScene = getSearchShortestPathScene();
        shortestPathsScene = getSearchBusScene();
        arrivalTimeScene = getSearchByArrivalScene();

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
        Button button1 = new Button("Search Shortest Path");
        button1.setMinWidth(150);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(stopScene);
            }
        });

        // Creating button2
        Button button2 = new Button("Search By Bus Stop");
        button2.setMinWidth(150);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(shortestPathsScene);
            }
        });

        // Creating button3
        Button button3 = new Button("Search By Arrival Time");
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

    private Scene getSearchBusScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(0, 0, 0, 0));

        Label title = new Label("Search By Bus Stop");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);

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
        Label stopInputLabel = new Label("Input Stop Name: ");
        stopInputLabel.setTextFill(Color.WHITE);
        stopInputLabel.setStyle("-fx-font: 15px Verdana;");

        // Input
        TextField stopInput = new TextField();
        stopInput.setMaxWidth(80);

        inputRow.getChildren().addAll(stopInputLabel, stopInput);

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
        sp.setStyle("-fx-background: #2e2e2e");
        sp.setPrefSize(890, 600);
        output.setPrefWidth(1000);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Preping error label
        Label error = new Label(
                "There was no data available\nOR\nThe input format you have entered is invalid\n(Please use all caps)");
        error.setStyle("-fx-font: 10px Verdana");
        error.setTextFill(Color.WHITE);
        error.setTextAlignment(TextAlignment.CENTER);

        // Display output on click
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                output.getChildren().clear();
                if (mainPane.getChildren().contains(sp)) {
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                } else if (mainPane.getChildren().contains(error)) {
                    mainPane.getChildren().remove(error);
                }
                String[][] result = StopNameFinder.findBusStops(stopInput.getText());
                if (result != null) {
                    // display result
                    for (int i = 0; i < result.length; i++) {
                        for (int j = 0; j < result[i].length; j++) {
                            System.out.println(result[i][j]);
                            Label entry = new Label(result[i][j]);
                            entry.setTextAlignment(TextAlignment.CENTER);
                            output.add(entry, j, i);
                        }
                    }
                    output.setHgap(25);
                    mainPane.getChildren().addAll(outputTitle, sp);
                } else {

                    mainPane.getChildren().add(error);

                }
            }
        });

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                if (mainPane.getChildren().contains(error))
                    mainPane.getChildren().remove(error);
                else {
                    output.getChildren().clear();
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                }
                stage.setScene(mainScene);
            }
        });

        root.setTop(title);
        root.setCenter(mainPane);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }

    private Scene getSearchShortestPathScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(0, 0, 0, 0));

        Label title = new Label("Search Shortest Path");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);

        // Main pane
        FlowPane mainPane = new FlowPane();
        mainPane.setMinWidth(400);
        mainPane.setOrientation(Orientation.VERTICAL);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.TOP_CENTER);
        mainPane.setColumnHalignment(HPos.CENTER);
        mainPane.setRowValignment(VPos.CENTER);
        mainPane.setVgap(20);

        // Input row 1
        FlowPane inputRowOne = new FlowPane();
        inputRowOne.setAlignment(Pos.TOP_CENTER);
        inputRowOne.setColumnHalignment(HPos.CENTER);
        inputRowOne.setRowValignment(VPos.CENTER);
        inputRowOne.setHgap(25);

        // Input label 1
        Label stopOneInputLabel = new Label("Input Starting Stop:      ");
        stopOneInputLabel.setTextFill(Color.WHITE);
        stopOneInputLabel.setStyle("-fx-font: 15px Verdana;");

        // Input 1
        TextField stopOneInput = new TextField();
        stopOneInput.setMaxWidth(80);

        inputRowOne.getChildren().addAll(stopOneInputLabel, stopOneInput);

        // Input row 2
        FlowPane inputRowTwo = new FlowPane();
        inputRowTwo.setAlignment(Pos.TOP_CENTER);
        inputRowTwo.setColumnHalignment(HPos.CENTER);
        inputRowTwo.setRowValignment(VPos.CENTER);
        inputRowTwo.setHgap(25);

        // Input label 2
        Label stopTwoInputLabel = new Label("Input Destination Stop: ");
        stopTwoInputLabel.setTextFill(Color.WHITE);
        stopTwoInputLabel.setStyle("-fx-font: 15px Verdana;");

        // Input 2
        TextField stopTwoInput = new TextField();
        stopTwoInput.setMaxWidth(80);

        inputRowTwo.getChildren().addAll(stopTwoInputLabel, stopTwoInput);

        // Search button
        Button searchBtn = new Button("Search");

        // Output
        GridPane outputTitle = new GridPane();
        GridPane output = new GridPane();
        output.setStyle("-fx-background-color: #2e2e2e");
        // output.setAlignment(Pos.CENTER);
        output.setMaxHeight(400);

        mainPane.getChildren().addAll(inputRowOne, inputRowTwo, searchBtn);
        ScrollPane sp = new ScrollPane();
        sp.setContent(output);

        sp.setStyle("-fx-background: #2e2e2e");
        sp.setPrefSize(750, 500);
        output.setPrefWidth(1000);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

        // Preping error label
        Label error = new Label("There was no data available\nOR\nYour inputs were invalid");
        error.setStyle("-fx-font: 10px Verdana");
        error.setTextFill(Color.WHITE);
        error.setTextAlignment(TextAlignment.CENTER);

        // Display output on click
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                output.getChildren().clear();
                if (mainPane.getChildren().contains(sp)) {
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                } else if (mainPane.getChildren().contains(error)) {
                    mainPane.getChildren().remove(error);
                }

                // try get inputs
                try {
                    int inputOne = Integer.parseInt(stopOneInput.getText());
                    int inputTwo = Integer.parseInt(stopTwoInput.getText());
                    ArrayList<String> result = shortestPathsFinder.getShortestPath(inputOne, inputTwo);
                    if (result != null) {
                        // display results
                        Label costLabel = new Label(
                                "The 'cost' of the path between the two stops is: " + result.get(0));
                        costLabel.setAlignment(Pos.CENTER);
                        output.add(costLabel, 10, 0);
                        Label pathLabel = new Label("The path is: ");
                        output.add(pathLabel, 10, 1);
                        for (int i = 1; i < result.size(); i++) {
                            Label pathEntry = new Label(result.get(i));
                            pathEntry.setAlignment(Pos.CENTER);
                            output.add(pathEntry, 10, i + 2);
                        }
                        mainPane.getChildren().addAll(outputTitle, sp);
                    } else {
                        // display error
                        mainPane.getChildren().add(error);
                    }
                } catch (Exception e) {
                    // display error
                    mainPane.getChildren().add(error);
                }

            }
        });

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                if (mainPane.getChildren().contains(error))
                    mainPane.getChildren().remove(error);
                else {
                    output.getChildren().clear();
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                }
                stage.setScene(mainScene);
            }
        });

        root.setTop(title);
        root.setCenter(mainPane);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }

    private Scene getSearchByArrivalScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e2e2e");
        root.setPadding(new Insets(0, 0, 0, 0));

        Label title = new Label("Search By Arrival Time");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font: 25px Verdana;");
        BorderPane.setAlignment(title, Pos.CENTER);

        Button homeBtn = new Button("Home");
        BorderPane.setAlignment(homeBtn, Pos.BASELINE_CENTER);
        FlowPane mainPane = new FlowPane();

        // Main pane

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
        sp.setStyle("-fx-background: #2e2e2e");
        sp.setPrefSize(750, 600);
        output.setPrefWidth(1000);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

        // Preping error label
        Label error = new Label("There was no data available\nOR\nThe time format you have entered is invalid");
        error.setStyle("-fx-font: 10px Verdana");
        error.setTextFill(Color.WHITE);
        error.setTextAlignment(TextAlignment.CENTER);

        // Display output on click
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                output.getChildren().clear();
                if (mainPane.getChildren().contains(sp)) {
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                } else if (mainPane.getChildren().contains(error)) {
                    mainPane.getChildren().remove(error);
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
                    mainPane.getChildren().add(error);
                }
            }
        });

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                if (mainPane.getChildren().contains(error))
                    mainPane.getChildren().remove(error);
                else {
                    output.getChildren().clear();
                    mainPane.getChildren().remove(sp);
                    mainPane.getChildren().remove(outputTitle);
                }
                stage.setScene(mainScene);
            }
        });
        root.setTop(title);
        root.setCenter(mainPane);
        root.setBottom(homeBtn);
        Scene scene = new Scene(root, 900, 850);
        return scene;
    }
}
