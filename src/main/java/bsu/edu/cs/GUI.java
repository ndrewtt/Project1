package bsu.edu.cs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class GUI extends Application {

    private VBox revisionBox;

    @Override
    public void start(Stage primaryStage) {
        revisionBox = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(revisionBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        loadRevisions();

        Scene scene = new Scene(scrollPane, 800, 600);
        primaryStage.setTitle("Wikipedia Revision Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadRevisions() {
        try (InputStream inputStream = new FileInputStream("test.json")) {
            WikipediaRevisionReader reader = new WikipediaRevisionReader();

            List<WikipediaRevision> revisions = reader.getRevisions(inputStream.toString());

            revisions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));

            int limit = Math.min(15, revisions.size());
            for (int i = 0; i < limit; i++) {
                WikipediaRevision rev = revisions.get(i);
                String display = rev.getUser() + "  " + rev.getTimestamp();
                revisionBox.getChildren().add(new Label(display));
            }

        } catch (Exception e) {
            revisionBox.getChildren().add(new Label("Error loading revisions: " + e.getMessage()));
        }
    }
}
