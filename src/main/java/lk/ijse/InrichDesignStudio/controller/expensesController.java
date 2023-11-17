package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class expensesController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<?> tableView;

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
    public void btnOnExpensesDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));
    }

    public void btnOnIncomeDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));
    }

    public void btnOnAddExpenses(ActionEvent actionEvent) {

    }

    public void btnOnUpdateExpenses(ActionEvent actionEvent) {
    }

    public void btnOnDeleteExpenses(ActionEvent actionEvent) {
    }
}
