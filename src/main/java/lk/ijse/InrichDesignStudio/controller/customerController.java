package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import lk.ijse.InrichDesignStudio.Model.customerModel;
import lk.ijse.InrichDesignStudio.dto.customerDto;
import util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;

public class customerController {


    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;


  customerModel cusModel = new customerModel();






    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");

    }



    public void btnOnSaveCustomer(javafx.event.ActionEvent actionEvent) {
        boolean isExist = false;

        try {
            isExist = cusModel.exitCustomer(txtId.getText());
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tel = txtNumber.getText();


        if(id.isEmpty()||name.isEmpty()||email.isEmpty()||address.isEmpty()||tel.isEmpty()){//String.valueOf(varibale).isEmpty
            if (id.isEmpty()) setFocusColorRed(txtId);
            if (name.isEmpty()) setFocusColorRed(txtName);
            if (email.isEmpty()) setFocusColorRed(txtEmail);
            if (address.isEmpty()) setFocusColorRed(txtAddress);
            if (tel.isEmpty()) setFocusColorRed(txtNumber);
            new SystemAlert(Alert.AlertType.ERROR,"Error","Field Cannot be Empty", ButtonType.OK).show();

            // resetFieldStyle(fNameField);
            return;
        }
        if (!isExist) {
            var dto = new customerDto(id, name, address, email, tel);
            try {
                boolean isSaved = cusModel.saveCustomer(dto);
                if (isSaved){
                    resetFieldStyle(txtId);
                    resetFieldStyle(txtNumber);
                    resetFieldStyle(txtAddress);
                    resetFieldStyle(txtEmail);
                    resetFieldStyle(txtName);
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Succes","Customer Saved Succesfully,cheked your email", ButtonType.OK).show();
                    MailUtil mail = new MailUtil();
                    mail.setMsg("Hi, " + name + " \n\n\t  thank you for choosing us. We look forward to serving you and providing you with an excellent experience.  \n\nThank you..!");
                    mail.setTo(email);
                    mail.setSubject("WELCOME INRICH DESIGN STUDIO ");

                    Thread thread = new Thread(mail);
                    thread.start();

                    //resetFieldStyle(txtId,txtName,txtAddress,txtEmail,txtNumber);
                    clearField();

                }else {
                    new SystemAlert(Alert.AlertType.WARNING,"Warning","Customer not saved!",ButtonType.OK).show();
                }


            } catch (SQLException e) {
                //throw new RuntimeException(e);
                new SystemAlert(Alert.AlertType.ERROR,"Error","Something went wrong!",ButtonType.OK).show();
               // new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }
        }else{
            setFocusColorRed(txtId);
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Customer already exist!",ButtonType.OK).show();

        }
    }

    private void resetFieldStyle(TextField field) {
        field.setStyle("");
    }

    private void clearField() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtNumber.clear();
    }

    public void btnOnUpdateCustomer(javafx.event.ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tel = txtNumber.getText();

        var dto = new customerDto(id,name,address,email,tel);

        if(id.isEmpty()||name.isEmpty()||email.isEmpty()||address.isEmpty()||tel.isEmpty()){//String.valueOf(varibale).isEmpty
            if (id.isEmpty()) setFocusColorRed(txtId);
            if (name.isEmpty()) setFocusColorRed(txtName);
            if (email.isEmpty()) setFocusColorRed(txtEmail);
            if (address.isEmpty()) setFocusColorRed(txtAddress);
            if (tel.isEmpty()) setFocusColorRed(txtNumber);
            new SystemAlert(Alert.AlertType.ERROR,"Error","Field Cannot be Empty", ButtonType.OK).show();

            // resetFieldStyle(fNameField);
            return;
        }
        try {
            boolean isUpdated = cusModel.updateCustomer(dto);
            if (isUpdated){
                resetFieldStyle(txtId);
                resetFieldStyle(txtNumber);
                resetFieldStyle(txtAddress);
                resetFieldStyle(txtEmail);
                resetFieldStyle(txtName);
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Customer updated!",ButtonType.OK).showAndWait();
                clearField();
            }else{
                new SystemAlert(Alert.AlertType.WARNING,"Warning","Customer not updated!",ButtonType.OK).showAndWait();
            }

        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage());
        }


    }

    public void btnOnDeleteCustomer(javafx.event.ActionEvent actionEvent) {
        if (!txtId.getText().isEmpty()){
            resetFieldStyle(txtId);
            String id = txtId.getText();

            boolean isExit = false;
            try {
                isExit = cusModel.exitCustomer(id);

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }
            if(isExit){
                //TxtColours.setDefaultColours(txtCustomerId);

                try {
                    boolean isDeleted = cusModel.deleteCustomer(id);
                    if (isDeleted){
                        new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer deleted!", ButtonType.OK).showAndWait();
                        clearField();
                    }else{
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Customer not deleted!", ButtonType.OK).showAndWait();
                    }


                } catch (SQLException e) {
                    //throw new RuntimeException(e);
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                }

            }else{
                new SystemAlert(Alert.AlertType.ERROR, "Error", "Customer not found!", ButtonType.OK).show();
            }

        }else{
            setFocusColorRed(txtId);
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please enter customer id!", ButtonType.OK).showAndWait();
        }

    }

    public void btnOnCustomerDetail(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));

    }

    public void btnOnAnnocement(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/annoucementForm.fxml")));

    }
}