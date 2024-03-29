package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.InrichDesignStudio.bo.custom.UserBO;
import lk.ijse.InrichDesignStudio.bo.factory.BOFactory;
import lk.ijse.InrichDesignStudio.bo.factory.BOTypes;
import lk.ijse.InrichDesignStudio.dto.UserDto;
import util.Regex;
import util.SystemAlert;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;

public class SignupController {

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


    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOTypes.USER);


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

        if(first_name.isEmpty() || last_name.isEmpty() || email.isEmpty()||password.isEmpty()||rePassword.isEmpty()){//String.valueOf(varibale).isEmpty
            if (Regex.getNamePattern().matcher(fNameField.getText()).matches()) setFocusColorRed(fNameField);
            if (email.isEmpty()) setFocusColorRed(passField);
            if (Regex.getNamePattern().matcher(lNameField.getText()).matches()) setFocusColorRed(lNameField);
            if (Regex.getEmailPattern().matcher(EmalField.getText()).matches()) setFocusColorRed(EmalField);
            if (password.isEmpty()) setFocusColorRed(passField);
            if (rePassword.isEmpty()) setFocusColorRed(rePassField);
            new Alert(Alert.AlertType.ERROR,"Cannot Empty Field ").showAndWait();
           // resetFieldStyle(fNameField);
            return;
        }
        boolean isExist =false;
        try {
            isExist = userBO.existUser(email);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(!isExist) {


            if (password.equals(rePassword)) {

                var dto = new UserDto(email,first_name, last_name,  password);

                try {
                    boolean isSaved = userBO.saveUser(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Account Succesfully created").showAndWait();

                        clearFields();
                        resetFieldStyle(fNameField);
                        resetFieldStyle(lNameField);
                        resetFieldStyle(EmalField);
                        resetFieldStyle(passField);
                        resetFieldStyle(rePassField);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                }

            } else {
                setFocusColorRed(rePassField);
                new Alert(Alert.AlertType.ERROR, "Doesn't Match Password").showAndWait();
            }
        }else{
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Customer is already exist", ButtonType.OK).show();
            setFocusColorRed(EmalField);
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
