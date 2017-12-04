package taskpck;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class DialogController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML JFXTextField Text;
    @FXML JFXTextField mText;
    @FXML JFXTextField nText;

    public void startButtonAction(MouseEvent mouseEvent) throws IOException {

        String arrayText = Text.getText();
        String mTex = mText.getText();
        String nTex = nText.getText();
        Preferences prefs = Preferences.userRoot();
        prefs.put("data", arrayText);
        prefs.put("mText", mTex);
        prefs.put("nText", nTex);

        Parent root = FXMLLoader.load(getClass().getResource("taskFXML.fxml"));
        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void CloseMethod(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }


}
