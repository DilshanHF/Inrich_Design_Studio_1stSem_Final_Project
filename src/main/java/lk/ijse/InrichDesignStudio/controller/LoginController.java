package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import lk.ijse.InrichDesignStudio.Model.UserModel;
import util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public AnchorPane pane;
    public TextField userNameField;
    public PasswordField txtHidePasword;
    public TextField txtShowPassword;
    public ImageView lblCloseEye;
    public ImageView lblOpenEye;
    public static String password = null;

    public static String username;
    private UserModel uModel = new UserModel();


    public void btnOnLogin(ActionEvent actionEvent) throws IOException {

        username = userNameField.getText();
        password = txtHidePasword.getText();

        //var dto = new userDto(username,password);

        try {
            if (username != null || password != null) {
                boolean isLogin = uModel.checkCredentials(username, password);
                if (isLogin) {
                    resetFieldStyle(userNameField);
                    resetFieldStyle(txtHidePasword);

                    //new SystemAlert(Alert.AlertType.CONFIRMATION, "Succes", "SuccesFully login", ButtonType.OK).showAndWait();
                    MailUtil mail = new MailUtil();
                    mail.setMsg("Welcome..! \n\n\tYou are successfully logged  \n\nThank you..!");
                    mail.setTo(username);
                    mail.setSubject("System Login");

                    Thread thread = new Thread(mail);
                    thread.start();

                    AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/Dashbord.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");
                    stage.centerOnScreen();


                } else {
                    new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                    // new Alert(Alert.AlertType.ERROR, "Your Entered Password Or UserName is Wrong !").show();

                    userNameField.clear();
                    txtHidePasword.clear();
                }

            } else {

                if (username.isEmpty()) setFocusColorRed(userNameField);
                if (password.isEmpty()) setFocusColorRed(txtHidePasword);
                //new Alert(Alert.AlertType.ERROR, "Please fill All the fields !").showAndWait();
                new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill All the fields !", ButtonType.OK).show();

            }


        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
        }
    }

        private void resetFieldStyle (TextField field){
                field.setStyle("");
            }

            private void setFocusColorRed (TextField field){

                field.setStyle("-fx-border-color: red;");
            }

            public void btnOnSignup (ActionEvent actionEvent) throws IOException {

                AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/signupPage.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Log In Page");
                stage.centerOnScreen();
            }
            public void initialize () {
                txtShowPassword.setVisible(false);
                lblOpenEye.setVisible(false);
                lblCloseEye.setVisible(true);
                txtHidePasword.setVisible(true);

            }

            public void HidepasswordOnClicked (KeyEvent keyEvent){
                password = txtHidePasword.getText();
                txtShowPassword.setText(password);

            }

            public void Close_Eye_Clicked (MouseEvent mouseEvent){
                txtShowPassword.setVisible(true);
                lblOpenEye.setVisible(true);
                lblCloseEye.setVisible(false);
                txtHidePasword.setVisible(false);

            }

            public void ShowPasswordOnClicked (KeyEvent keyEvent){
                password = txtShowPassword.getText();
                txtHidePasword.setText(password);

            }

            public void Open_Eye_ClickedAction (MouseEvent mouseEvent){
                txtShowPassword.setVisible(false);
                lblOpenEye.setVisible(false);
                lblCloseEye.setVisible(true);
                txtHidePasword.setVisible(true);
            }

            public void txtShowPassword (ActionEvent actionEvent) throws IOException {
                btnOnLogin(actionEvent);
            }


    public void txtHidePassword(ActionEvent actionEvent) throws IOException {
        btnOnLogin(actionEvent);
    }
}
