package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class welcomepageController {
    @FXML
    private JFXButton logBtn;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton signBtn;



    public void btnOnLogin(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Log In Page");
        stage.centerOnScreen();

    }

    public void btnOnSignup(ActionEvent actionEvent) throws Exception {


        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/signupPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign Up Page");
        stage.centerOnScreen();
        //stage.setFullScreen(true);

    }
}
