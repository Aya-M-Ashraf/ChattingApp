package controller;

/**
 *
 * @author Amr Abd El Latief
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author JayaPrasad
 *
 */
public class YesNoMessage extends Application {

    /*
     * (non-Javadoc)
     * 
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    int clickedButton = 0;

    public int showYesNoDialog(Stage primaryStage) {

        try {
            start(primaryStage);

        } catch (Exception ex) {
            Logger.getLogger(YesNoMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clickedButton;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Label exitLabel = new Label("Are you sure you want to exit?");
        exitLabel.setAlignment(Pos.BASELINE_CENTER);

        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                dialogStage.close();

                clickedButton = 1;

            }
        });
        Button noBtn = new Button("No");

        noBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                dialogStage.close();
                clickedButton = -1;
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(40.0);
        hBox.getChildren().addAll(yesBtn, noBtn);

        VBox vBox = new VBox();
        vBox.setSpacing(40.0);
        vBox.getChildren().addAll(exitLabel, hBox);
        Scene newScene = new Scene(vBox);
        newScene.getStylesheets().add("src/view/cssForm.css");
        dialogStage.setScene(newScene);
        dialogStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
