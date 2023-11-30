package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import lk.ijse.InrichDesignStudio.Model.employeeModel;
import lk.ijse.InrichDesignStudio.dto.Tm.customerTm;
import lk.ijse.InrichDesignStudio.dto.Tm.employeeTm;
import lk.ijse.InrichDesignStudio.dto.customerDto;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import util.Regex;
import util.SystemAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeeController implements Initializable {


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

    @FXML
    private TableColumn<?, ?> colEmpAdd;

    @FXML
    private TableColumn<?, ?> colEmpCon;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpNic;

    @FXML
    private TableView<employeeTm> tblEmployee;



    employeeModel empModel = new employeeModel();


    public void btnOnAttendance(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/markAttendanceForm.fxml")));

    }

    public void btnOnAttendanceDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/attendanceDetailsForm.fxml")));
    }

    public void btnOnEmployeeDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml")));
    }

    public void btnOnSaveEmployee(ActionEvent actionEvent) {
        boolean isExists = false;
        try {
            isExists = empModel.exitEmployee(txtId.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }

        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtTel.getText().isEmpty() || txtNic.getText().isEmpty())){
            if (Regex.getEmployeeId().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);
                if (Regex.getNamePattern().matcher(txtName.getText()).matches()) {
                    resetFieldStyle(txtName);
                    if (Regex.getContactPattern().matcher(txtTel.getText()).matches()) {
                        resetFieldStyle(txtTel);
                        if (Regex.getAddressPattern().matcher(txtAddress.getText()).matches()) {
                            resetFieldStyle(txtAddress);
                            if (Regex.getOldIDPattern().matcher(txtNic.getText()).matches()) {
                                resetFieldStyle(txtNic);

                                if (!isExists) {
                                    resetFieldStyle(txtId);

                                    String id = txtId.getText();
                                    String name = txtName.getText();
                                    String address = txtAddress.getText();
                                    String tel = txtTel.getText();
                                    String nic = txtNic.getText();
                                    //Double salary = Double.valueOf(txtSalary.getText());
                                    var dto = new employeeDto(id,name,address,tel,nic);

                                    try {
                                        boolean isSaved = empModel.saveEmployee(dto);
                                        if (isSaved) {
                                            new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Employee  saved!", ButtonType.OK).show();
                                            //tblCustomer.refresh();
                                            clearField();
                                            loadAllCustomer();

                                        } else {
                                            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not saved!", ButtonType.OK).show();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                                        //new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                                    }
                                } else {
                                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee is already exist", ButtonType.OK).show();
                                    setFocusColorRed(txtId);
                                }
                            }else{
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Nic Id", ButtonType.OK).show();
                                setFocusColorRed(txtNic);
                            }

                        }else {
                            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Address", ButtonType.OK).show();
                            setFocusColorRed(txtAddress);

                        }
                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Number", ButtonType.OK).show();
                        setFocusColorRed(txtTel);

                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Name", ButtonType.OK).show();
                    setFocusColorRed(txtName);
                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Employee Id", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        } else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtAddress);
            setFocusColorRed(txtTel);
            setFocusColorRed(txtNic);


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

    public void btnOnUpdate(ActionEvent actionEvent) throws SQLException {

        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtTel.getText().isEmpty() || txtNic.getText().isEmpty())) {
            if (Regex.getEmployeeId().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);
                if (Regex.getContactPattern().matcher(txtTel.getText()).matches()) {
                    resetFieldStyle(txtTel);
                    if (Regex.getOldIDPattern().matcher(txtNic.getText()).matches()) {
                        resetFieldStyle(txtNic);




                        String id = txtId.getText();
                        String name = txtName.getText();
                        String address = txtAddress.getText();
                        String tel = txtTel.getText();
                        String nic = txtNic.getText();

                        var dto = new customerDto(id, name, address, tel,nic);


                        boolean isUpdated = empModel.updateEmployee(dto);
                        if (isUpdated) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Employee updated!", ButtonType.OK).show();
                            loadAllCustomer();
                            clearField();

                        } else {
                            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not updated!", ButtonType.OK).show();
                        }

                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Nic", ButtonType.OK).show();
                        setFocusColorRed(txtNic);

                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Number", ButtonType.OK).show();
                    setFocusColorRed(txtTel);
                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Employee Id", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtAddress);
            setFocusColorRed(txtTel);
            setFocusColorRed(txtNic);

        }


    }

    public void btnOnDelete(ActionEvent actionEvent) {
        if (!txtId.getText().isEmpty()) {
            if (Regex.getEmployeeId().matcher(txtId.getText()).matches()) {
                boolean isExists = false;
                try {
                    isExists = empModel.exitCustomer(txtId.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
                if (isExists) {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new SystemAlert(Alert.AlertType.INFORMATION, "Information", "Are you sure to delete ?", yes, no).showAndWait();

                    String id = txtId.getText();

                    if (result.orElse(no) == yes) {
                        resetFieldStyle(txtId);
                        //lblError.setText("");

                        try {
                            boolean isDeleted = empModel.deleteEmployee(id);
                            if (isDeleted) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Employee has deleted!", ButtonType.OK).show();
                                clearField();
                                loadAllCustomer();
                                //populateEmployeeTable();
                                //searchFilter();
                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not deleted!", ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                        }
                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "No Employee Found!", ButtonType.OK).showAndWait();
                    clearField();

                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Employee Id ", ButtonType.OK).showAndWait();
                setFocusColorRed(txtId);

                //clearField();
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter Employee Id ", ButtonType.OK).showAndWait();
            setFocusColorRed(txtId);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCustomer();
    }

    private void loadAllCustomer() {
        ObservableList<employeeTm> obList = FXCollections.observableArrayList();

        try {
            List<employeeDto> dtoList = empModel.getAllEmployee();

            for (employeeDto dto : dtoList) {
                obList.add(
                        new employeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTel(),
                                dto.getNic()
                        )
                );
            }

            tblEmployee.setItems(obList);
            tblEmployee.refresh();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmpCon.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colEmpNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

    }



}
