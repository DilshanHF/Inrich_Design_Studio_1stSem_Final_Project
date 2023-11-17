package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import lk.ijse.InrichDesignStudio.Model.employeeModel;
import lk.ijse.InrichDesignStudio.dto.customerDto;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import util.SystemAlert;

import java.sql.SQLException;

public class employeeController {


    public AnchorPane mainPane;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtTel;

    employeeModel empModel = new employeeModel();


    public void btnOnAttendance(ActionEvent actionEvent) {
    }

    public void btnOnAttendanceDetails(ActionEvent actionEvent) {

    }

    public void btnOnEmployeeDetails(ActionEvent actionEvent) {
    }

    public void btnOnSaveEmployee(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String nic = txtNic.getText();

        boolean isExist = false;

        try {
            isExist = empModel.exitEmployee(txtId.getText());
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }



        if(id.isEmpty()||name.isEmpty()||name.isEmpty()||address.isEmpty()||tel.isEmpty()||nic.isEmpty()){//String.valueOf(varibale).isEmpty
            if (id.isEmpty()) setFocusColorRed(txtId);
            if (name.isEmpty()) setFocusColorRed(txtName);
            if (address.isEmpty()) setFocusColorRed(txtAddress);
            if (tel.isEmpty()) setFocusColorRed(txtTel);
            if (nic.isEmpty()) setFocusColorRed(txtNic);
            new SystemAlert(Alert.AlertType.ERROR,"Error","Field Cannot be Empty", ButtonType.OK).showAndWait();

            // resetFieldStyle(fNameField);
            return;
        }
        if (!isExist) {

            var Edto = new employeeDto(id, name, address, tel, nic);


            //boolean isSaved = false;
            boolean isSaved;
            try {
                isSaved = empModel.saveEmployee(Edto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (isSaved) {
                resetFieldStyle(txtId);
                resetFieldStyle(txtName);
                resetFieldStyle(txtAddress);
                resetFieldStyle(txtTel);
                resetFieldStyle(txtNic);
                new SystemAlert(Alert.AlertType.CONFIRMATION, "Succes", "Employee Saved Succesfully,cheked your email", ButtonType.OK).showAndWait();

                //resetFieldStyle(txtId,txtName,txtAddress,txtEmail,txtNumber);
                clearField();

            } else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not saved!", ButtonType.OK).showAndWait();
            }

        }else{
            setFocusColorRed(txtId);
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Employee already exist!",ButtonType.OK).showAndWait();

        }
    }

    private void clearField() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTel.clear();
        txtNic.clear();
    }

    private void resetFieldStyle(TextField field) {
        field.setStyle("");
    }

    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");

    }

    public void btnOnUpdate(ActionEvent actionEvent) {

    }

    public void btnOnDelete(ActionEvent actionEvent) {
    }


}
