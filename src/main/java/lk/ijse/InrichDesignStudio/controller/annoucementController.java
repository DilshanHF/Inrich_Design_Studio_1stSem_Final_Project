package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import util.SystemAlert;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.Objects;

public class annoucementController {
    public TextField txtCustomerMail;
    public TextField txtSubject;
    public TextField txtMassage;

    @FXML
    private AnchorPane mainPane;
    public void btnOnCustomerDetail(ActionEvent actionEvent) throws IOException {

        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));
    }

    public void btnOnAnnocement(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/annoucementForm.fxml")));


    }

    public void btnOnSend(ActionEvent actionEvent) {
        String email = txtCustomerMail.getText();
        String subject = txtSubject.getText();
        String massage = txtMassage.getText();

        if(email.isEmpty()|| subject.isEmpty()|| massage.isEmpty()){
            new SystemAlert(Alert.AlertType.ERROR,"Error","Field Cannot be Empty", ButtonType.OK).showAndWait();
            return;
        }else {
            MailUtil mail = new MailUtil();
            mail.setMsg(massage);
            mail.setTo(email);
            mail.setSubject(subject);

            Thread thread = new Thread(mail);
            thread.start();

            //resetFieldStyle(txtId,txtName,txtAddress,txtEmail,txtNumber);
            clearField();

            new SystemAlert(Alert.AlertType.CONFIRMATION,"Succes","Sent mail Succesfully", ButtonType.OK).show();

        }


    }

    private void clearField() {
        txtCustomerMail.clear();
        txtSubject.clear();
        txtMassage.clear();
    }
}
