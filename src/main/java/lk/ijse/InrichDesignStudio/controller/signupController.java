package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.InrichDesignStudio.Model.userModel;
import lk.ijse.InrichDesignStudio.dto.userDto;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;

public class signupController {

    public AnchorPane pane;
    @FXML
    private TextField EmalField;

    @FXML
    private TextField fNameField;

    @FXML
    private TextField lNameField;

    @FXML
    private ImageView mainImage;

    @FXML
    private PasswordField passField;

    @FXML
    private PasswordField rePassField;



    private userModel uModel = new userModel();

    public void btnOnlogin(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Log In Page");
        stage.centerOnScreen();
    }

    public void btnOnsgnup(ActionEvent actionEvent) {
        String first_name = fNameField.getText();
        String last_name =  lNameField.getText();
        String email =   EmalField.getText();
        String password = passField.getText();
        String rePassword = rePassField.getText();

        if(first_name.isEmpty() || last_name.isEmpty() || email.isEmpty()||password.isEmpty()||rePassword.isEmpty()){
            if (first_name.isEmpty()) setFocusColorRed(fNameField);
            if (last_name.isEmpty()) setFocusColorRed(lNameField);
            if (email.isEmpty()) setFocusColorRed(EmalField);
            if (password.isEmpty()) setFocusColorRed(passField);
            if (rePassword.isEmpty()) setFocusColorRed(rePassField);
            new Alert(Alert.AlertType.ERROR,"Cannot Empty Field ").showAndWait();
           // resetFieldStyle(fNameField);
            return;
        }

        if(password.equals(rePassword)){

            var dto = new userDto(first_name,last_name,email,password);

            try {
                boolean isSaved = uModel.saveUser(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Account Succesfully created").showAndWait();
                    clearFields();
                    resetFieldStyle(fNameField);
                    resetFieldStyle(lNameField);
                    resetFieldStyle(EmalField);
                    resetFieldStyle(passField);
                    resetFieldStyle(rePassField);
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }

        }else {
            setFocusColorRed(rePassField);
            new Alert(Alert.AlertType.ERROR,"Doesn't Match Password").showAndWait();
        }

    }

    private void resetFieldStyle(TextField field) {
        field.setStyle("");
    }

    private void clearFields() {
        fNameField.clear();
        lNameField.clear();
        EmalField.clear();
        passField.clear();
        rePassField.clear();
    }


    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");

    }



   /* public void btnOnsgnup(ActionEvent actionEvent) {

    }

    public void btnOnlogin(ActionEvent actionEvent) {

    }*/

}
