package bsu.edu.cs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends Application {

    private VBox revisionBox;
    private TextField articleField;
    private Button queryButton;

    @Override
    public void start(Stage primaryStage) {
        Label header = new Label("Wikipedia Fetcher :)");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        articleField = new TextField();
        articleField.setPromptText("Enter Wikipedia article name");

        queryButton = new Button("FETCH");
        queryButton.setOnAction(e -> fetchRevisions());

        revisionBox = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(revisionBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox root = new VBox(10, header, articleField, queryButton, scrollPane);
        root.setPadding(new Insets(10));

        revisionBox.getChildren().add(new Label("Enter the name of your article and press FETCH to begin"));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Wikipedia Fetcher :)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchRevisions() {
        String articleTitle = articleField.getText().trim();
        if (articleTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No article name provided");
            alert.setContentText("Please enter a Wikipedia article name");
            alert.showAndWait();
            return;
        }

        articleField.setDisable(true);
        queryButton.setDisable(true);
        revisionBox.getChildren().clear();
        revisionBox.getChildren().add(new Label("Fetching revisions for \"" + articleTitle + "\"..."));

        new Thread(() -> {
            try {
                WikipediaRevisionReader reader = new WikipediaRevisionReader();
                List<WikipediaRevision> revisions = reader.getRevisions(articleTitle);

                Platform.runLater(() -> {
                    revisionBox.getChildren().clear();

                    if (revisions.isEmpty()) {
                        revisionBox.getChildren().add(new Label("Sorry, we couldn't find anything on \"" + articleTitle + "\"."));
                    } else {
                        revisions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
                        int limit = Math.min(15, revisions.size());

                        for (int i = 0; i < limit; i++) {
                            WikipediaRevision rev = revisions.get(i);
                            String display = rev.getUser() + "  " + rev.getTimestamp();
                            revisionBox.getChildren().add(new Label(display));
                        }
                    }

                    articleField.setDisable(false);
                    queryButton.setDisable(false);
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Network error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();

                    articleField.setDisable(false);
                    queryButton.setDisable(false);
                });
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
