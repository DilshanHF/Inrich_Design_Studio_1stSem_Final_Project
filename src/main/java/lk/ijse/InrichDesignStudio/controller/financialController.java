package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class financialController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<?> tableview;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtType;

    public void btnOnExpensesDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));
    }

    public void btnOnIncomeDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));
    }

    public void btnOnSaveIncome(ActionEvent actionEvent) {
    }

    public void btnOnUpdateIncome(ActionEvent actionEvent) {
    }

    public void btnOnDeleteIncome(ActionEvent actionEvent) {
    }
}
