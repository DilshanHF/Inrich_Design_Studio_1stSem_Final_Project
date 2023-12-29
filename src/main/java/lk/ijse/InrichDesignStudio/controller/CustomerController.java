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
import lk.ijse.InrichDesignStudio.Model.CustomerModel;
import lk.ijse.InrichDesignStudio.bo.custom.CustomerBO;
import lk.ijse.InrichDesignStudio.bo.custom.impl.CustomerBOImpl;
import lk.ijse.InrichDesignStudio.dto.Tm.customerTm;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.entity.Customer;
import util.Regex;
import util.SystemAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    public TextField txtCustomerId;
    public Button btnSearch;
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
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAdd;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<customerTm> tblCustomer;
    @FXML
    private TextField txtSearchId;



   // CustomerModel cusModel = new CustomerModel();
    CustomerBO customerBO = new CustomerBOImpl();



    private void loadAllCustomer() {
        //var model = new CustomerModel();

        ObservableList<customerTm> obList = FXCollections.observableArrayList();

        try {
            ArrayList<CustomerDto> dtoList = customerBO.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                JFXButton button = new JFXButton("edit",new ImageView("assets/edit-97@30x.png"));
                button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                button.getStyleClass().add("infoBtn");
                setButtonOnAction(button);
                obList.add(
                        new customerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTel(),
                                dto.getEmail(),
                                button
                        )
                );
            }

            tblCustomer.setItems(obList);
            tblCustomer.refresh();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setButtonOnAction(JFXButton button) {
        button.setOnAction((e) -> {
            int index = -1;

            for (int i=0 ; i < tblCustomer.getItems().size(); i++){
                if (colAction.getCellData(i).equals(button)){
                    index = i;
                }
            }
            customerTm customer = tblCustomer.getItems().get(index);
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtNumber.setText(customer.getTel());
            txtEmail.setText(customer.getEmail());
        });
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }


    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");

    }



    public void btnOnSaveCustomer(javafx.event.ActionEvent actionEvent) {
        boolean isExists = false;
        try {
            isExists = customerBO.existCustomer(txtId.getText());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }

        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNumber.getText().isEmpty() || txtEmail.getText().isEmpty())){
            if (Regex.getCustomerId().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);
                if (Regex.getNamePattern().matcher(txtName.getText()).matches()) {
                    resetFieldStyle(txtName);
                    if (Regex.getContactPattern().matcher(txtNumber.getText()).matches()) {
                        resetFieldStyle(txtNumber);
                        if (Regex.getAddressPattern().matcher(txtAddress.getText()).matches()) {
                            resetFieldStyle(txtAddress);
                            if (Regex.getEmailPattern().matcher(txtEmail.getText()).matches()) {
                                resetFieldStyle(txtEmail);

                            if (!isExists) {
                                resetFieldStyle(txtId);

                                String id = txtId.getText();
                                String name = txtName.getText();
                                String address = txtAddress.getText();
                                String tel = txtNumber.getText();
                                String email = txtEmail.getText();
                                //Double salary = Double.valueOf(txtSalary.getText());
                                var dto = new CustomerDto(id,name,address,tel,email);

                                try {
                                    boolean isSaved = customerBO.saveCustomer(dto);
                                    if (isSaved) {
                                        new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer  saved!", ButtonType.OK).show();
                                        tblCustomer.refresh();
                                        loadAllCustomer();
                                       clearField();
                                    } else {
                                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Customer not saved!", ButtonType.OK).show();
                                    }
                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                                    //new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                                }
                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Customer is already exist", ButtonType.OK).show();
                                setFocusColorRed(txtId);
                            }
                        }else{
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Email Address", ButtonType.OK).show();
                                setFocusColorRed(txtEmail);
                            }

                        }else {
                            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Address", ButtonType.OK).show();
                            setFocusColorRed(txtAddress);

                        }
                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Number", ButtonType.OK).show();
                        setFocusColorRed(txtNumber);

                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Name", ButtonType.OK).show();
                    setFocusColorRed(txtName);
                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Customer Id", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        } else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtAddress);
            setFocusColorRed(txtNumber);
            setFocusColorRed(txtEmail);


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

    public void btnOnUpdateCustomer(ActionEvent actionEvent) {


        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNumber.getText().isEmpty() || txtEmail.getText().isEmpty())) {
            if (Regex.getCustomerId().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);
                if (Regex.getContactPattern().matcher(txtNumber.getText()).matches()) {
                    resetFieldStyle(txtNumber);
                    if (Regex.getEmailPattern().matcher(txtEmail.getText()).matches()) {
                      resetFieldStyle(txtEmail);




                          String id = txtId.getText();
                          String name = txtName.getText();
                          String address = txtAddress.getText();
                          String tel = txtNumber.getText();
                          String email = txtEmail.getText();

                          var dto = new CustomerDto(id, name, address, tel, email);

                          try {
                              boolean isUpdated = customerBO.updateCustomer(dto);
                              if (isUpdated) {
                                  new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer updated!", ButtonType.OK).show();
                                  clearField();
                                  loadAllCustomer();

                              } else {
                                  new SystemAlert(Alert.AlertType.WARNING, "Warning", "Customer not updated!", ButtonType.OK).show();
                              }
                          } catch (SQLException | ClassNotFoundException e) {
                              e.printStackTrace();
                              new SystemAlert(Alert.AlertType.ERROR, "Error", "Somehing went wrong!", ButtonType.OK).show();
                          }

                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Email", ButtonType.OK).show();
                        setFocusColorRed(txtEmail);

                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Number", ButtonType.OK).show();
                    setFocusColorRed(txtNumber);
                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Customer Id", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtAddress);
            setFocusColorRed(txtNumber);
            setFocusColorRed(txtEmail);

        }


    }

    public void btnOnDeleteCustomer(javafx.event.ActionEvent actionEvent) {
        if (!txtId.getText().isEmpty()) {
            if (Regex.getCustomerId().matcher(txtId.getText()).matches()) {
                boolean isExists = false;
                try {
                    isExists = customerBO.existCustomer(txtId.getText());
                } catch (SQLException | ClassNotFoundException e) {
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
                            boolean isDeleted = customerBO.deleteCustomer(id);
                            if (isDeleted) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Employee has deleted!", ButtonType.OK).show();
                                clearField();
                                loadAllCustomer();
                                //populateEmployeeTable();
                                //searchFilter();
                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not deleted!", ButtonType.OK).show();
                            }
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                        }
                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "No Customer Found!", ButtonType.OK).showAndWait();
                    clearField();

                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Customer Id ", ButtonType.OK).showAndWait();
                setFocusColorRed(txtId);

                //clearField();
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter Customer Id ", ButtonType.OK).showAndWait();
            setFocusColorRed(txtId);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCustomer();
    }



    public void btnOnClickOnSearch(ActionEvent actionEvent) throws SQLException {
        boolean isExists = false;
        try {
            isExists = customerBO.existCustomer(txtSearchId.getText());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
        if(!(txtSearchId.getText().isEmpty())){
            if (Regex.getCustomerId().matcher(txtSearchId.getText()).matches()){
                String id = txtSearchId.getText();
                if (isExists){
                    try {
                        CustomerDto customerDto = customerBO.searchCustomer(id);
                        if (customerDto != null){
                            txtId.setText(customerDto.getId());
                            txtName.setText(customerDto.getName());
                            txtAddress.setText(customerDto.getAddress());
                            txtNumber.setText(customerDto.getTel());
                            txtEmail.setText(customerDto.getEmail());
                        }else {
                            new SystemAlert(Alert.AlertType.WARNING, "Warning", "No Customer Found!", ButtonType.OK).show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                       // throw new RuntimeException(e);
                        new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                    }


                }
            }else {
                new SystemAlert(Alert.AlertType.ERROR, "Error", "Invalid customer Id", ButtonType.OK).show();
            }
        }else{
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
        }

    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException {
        btnOnClickOnSearch(actionEvent);
    }

    public void btnOnClear(ActionEvent actionEvent) {
        clearField();
    }
}