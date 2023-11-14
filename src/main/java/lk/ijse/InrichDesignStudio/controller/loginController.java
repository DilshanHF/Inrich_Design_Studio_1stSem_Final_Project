package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.InrichDesignStudio.Model.userModel;
import lk.ijse.InrichDesignStudio.dto.userDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController {

    public AnchorPane pane;
    public TextField userNameField;
    public PasswordField txtHidePasword;
    public TextField txtShowPassword;
    public ImageView lblCloseEye;
    public ImageView lblOpenEye;
    public static String password=null;

    public static String username;
    private userModel uModel = new userModel();


    public void btnOnLogin(ActionEvent actionEvent) throws IOException {

        username = userNameField.getText();
        password = txtHidePasword.getText();

        //var dto = new userDto(username,password);

        try {
            if(username != null || password != null){
                boolean isLogin = uModel.checkCredentials(username,password);
                if(isLogin){
                    //new Alert(Alert.AlertType.CONFIRMATION,"Login Succesfully").showAndWait();


                    AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/Dashbord.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");
                    stage.centerOnScreen();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Your Entered Password Or UserName is Wrong !").show();

                    userNameField.clear();
                    txtHidePasword.clear();
                }

            }else{

                if(username.isEmpty())setFocusColorRed(userNameField);
                if(password.isEmpty())setFocusColorRed(txtHidePasword);
                new Alert(Alert.AlertType.ERROR, "Please fill All the fields !").showAndWait();
                resetFieldStyle(userNameField);
                resetFieldStyle(txtHidePasword);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    private void resetFieldStyle(TextField field) {field.setStyle("");}

    private void setFocusColorRed(TextField field) {

        field.setStyle("-fx-border-color: red;");
    }

    public void btnOnSignup(ActionEvent actionEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/signupPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Log In Page");
        stage.centerOnScreen();
    }
    public void initialize() {
       txtShowPassword.setVisible(false);
       lblOpenEye.setVisible(false);
       lblCloseEye.setVisible(true);
       txtHidePasword.setVisible(true);

    }

    public void HidepasswordOnClicked(KeyEvent keyEvent) {
        password =txtHidePasword.getText();
        txtShowPassword.setText(password);

    }

    public void Close_Eye_Clicked(MouseEvent mouseEvent) {
            txtShowPassword.setVisible(true);
            lblOpenEye.setVisible(true);
            lblCloseEye.setVisible(false);
            txtHidePasword.setVisible(false);

    }

    public void ShowPasswordOnClicked(KeyEvent keyEvent) {
        password = txtShowPassword.getText();
        txtHidePasword.setText(password);

    }

    public void Open_Eye_ClickedAction(MouseEvent mouseEvent) {
        txtShowPassword.setVisible(false);
        lblOpenEye.setVisible(false);
        lblCloseEye.setVisible(true);
        txtHidePasword.setVisible(true);
    }
}