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
import lk.ijse.InrichDesignStudio.Model.IncomeModel;
import lk.ijse.InrichDesignStudio.dto.Tm.incomeTm;
import lk.ijse.InrichDesignStudio.dto.IncomeDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FinancialController implements Initializable {

    public TableColumn colIvId;
    public TableColumn colOrderId;
    public TableColumn colType;
    public TableColumn colAmount;
    public TableColumn colDate;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<incomeTm> tblIncome;

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

    IncomeModel exModel = new IncomeModel();


    public void btnOnExpensesDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));
    }

    public void btnOnIncomeDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllIncome();
    }

    private void loadAllIncome() {
        ObservableList<incomeTm> obList = FXCollections.observableArrayList();

        try {
            List<IncomeDto> dtoList = exModel.getAllIncome();

            for (IncomeDto dto : dtoList) {
                obList.add(
                        new incomeTm(
                                dto.getInvoiceId(),
                                dto.getOrderId(),
                                dto.getType(),
                                dto.getAmount(),
                                dto.getDate()
                        )
                );
            }

            tblIncome.setItems(obList);
            tblIncome.refresh();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    private void setCellValueFactory() {
        colIvId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
}
