package lk.ijse.InrichDesignStudio.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.bo.custom.ExpensesBO;
import lk.ijse.InrichDesignStudio.bo.factory.BOFactory;
import lk.ijse.InrichDesignStudio.bo.factory.BOTypes;
import lk.ijse.InrichDesignStudio.dto.Tm.expenseTm;
import lk.ijse.InrichDesignStudio.dto.ExpenseDto;
import util.SystemAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExpensesController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView <expenseTm> tblExpenses;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtType;

    //ExpensesModel exModel = new ExpensesModel();
    ExpensesBO expensesBO = (ExpensesBO) BOFactory.getBoFactory().getBO(BOTypes.EXPENSES);



    public void btnOnExpensesDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));
    }

    public void btnOnIncomeDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));
    }

    public void btnOnAddExpenses(ActionEvent actionEvent) {
        boolean isExists = false;
        try {
            isExists = expensesBO.existExpenses(txtId.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(txtId.getText().isEmpty()||txtDate.getValue()==null || txtDescription.getText().isEmpty()||txtType.getText().isEmpty()||txtAmount.getText().isEmpty())){
            resetFieldStyle(txtId);
            resetFieldStyle(txtDescription);
            resetFieldStyle(txtType);
            resetFieldStyle(txtAmount);

            if (!isExists){
                resetFieldStyle(txtId);

                String id = txtId.getText();
                LocalDate date = txtDate.getValue();
                String des =txtDescription.getText();
                String type = txtType.getText();
                double amount = Double.parseDouble(txtAmount.getText());

                var dto = new ExpenseDto(id,date,des,type,amount);
                try {
                    boolean isSaved = expensesBO.saveExpenses(dto);
                    if (isSaved){
                        new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Expense  saved!", ButtonType.OK).show();
                        clearField();
                        loadAllExpenses();
                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Expense not saved!", ButtonType.OK).show();
                        clearField();
                    }
                } catch (SQLException e) {
                    //throw new RuntimeException(e);
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                    System.out.println(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee is already exist", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtDate.getEditor());
            setFocusColorRed(txtDescription);
            setFocusColorRed(txtType);
            setFocusColorRed(txtAmount);
        }



    }

    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");

    }

    private void clearField() {
        txtId.clear();
        txtDate.setValue(null);
        txtType.clear();
        txtDescription.clear();
        txtAmount.clear();
    }

    private void resetFieldStyle(TextField field) {
        field.setStyle("");
    }

    public void btnOnUpdateExpenses(ActionEvent actionEvent) {

        if (!(txtId.getText().isEmpty()||txtDate.getValue()==null || txtDescription.getText().isEmpty()||txtType.getText().isEmpty()||txtAmount.getText().isEmpty())){
            resetFieldStyle(txtId);
            resetFieldStyle(txtDescription);
            resetFieldStyle(txtType);
            resetFieldStyle(txtAmount);



                String id = txtId.getText();
                LocalDate date = txtDate.getValue();
                String des =txtDescription.getText();
                String type = txtType.getText();
                double amount = Double.parseDouble(txtAmount.getText());

                var dto = new ExpenseDto(id,date,des,type,amount);
                try {
                    boolean isUpdated = expensesBO.updateExpenses(dto);
                    if (isUpdated){
                        new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Expense  saved!", ButtonType.OK).show();
                        clearField();
                        loadAllExpenses();
                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Expense not saved!", ButtonType.OK).show();
                        clearField();
                    }
                } catch (SQLException e) {
                    //throw new RuntimeException(e);
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                    System.out.println(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtDate.getEditor());
            setFocusColorRed(txtDescription);
            setFocusColorRed(txtType);
            setFocusColorRed(txtAmount);
        }
    }

    public void btnOnDeleteExpenses(ActionEvent actionEvent) {
        if (!txtId.getText().isEmpty()) {

                boolean isExists = false;
                try {
                    isExists = expensesBO.existExpenses(txtId.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
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
                            boolean isDeleted = expensesBO.deleteExpenses(id);
                            if (isDeleted) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Expenses has deleted!", ButtonType.OK).show();
                                clearField();
                                loadAllExpenses();
                                //populateEmployeeTable();
                                //searchFilter();
                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Expenses not deleted!", ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "No Expense Found!", ButtonType.OK).showAndWait();
                    clearField();

                }

        }else {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter Expense Id ", ButtonType.OK).showAndWait();
            setFocusColorRed(txtId);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllExpenses();
    }

    private void loadAllExpenses() {
        ObservableList<expenseTm> obList = FXCollections.observableArrayList();

        try {
            List<ExpenseDto> dtoList = expensesBO.getAllExpenses();

            for (ExpenseDto dto : dtoList) {
                obList.add(
                        new expenseTm(
                                dto.getId(),
                                dto.getDate(),
                                dto.getDes(),
                                dto.getType(),
                                dto.getAmount()
                        )
                );
            }

            tblExpenses.setItems(obList);
           tblExpenses.refresh();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("des"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
}
