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
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.bo.custom.IncomeBO;
import lk.ijse.InrichDesignStudio.bo.factory.BOFactory;
import lk.ijse.InrichDesignStudio.bo.factory.BOTypes;
import lk.ijse.InrichDesignStudio.dto.Tm.incomeTm;
import lk.ijse.InrichDesignStudio.dto.IncomeDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.SystemAlert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
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

    @FXML
    private DatePicker datePicker;

    //IncomeModel exModel = new IncomeModel();
    IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOTypes.INCOME);


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
            List<IncomeDto> dtoList = incomeBO.getAllIncome();

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {
        colIvId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void btnOnReport(ActionEvent actionEvent) {
        LocalDate date = LocalDate.parse(datePicker.getValue().toString());
        if (date != null) {
            new Thread(() -> {
                try {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/report/Income.jrxml");
                    JasperDesign load = JRXmlLoader.load(resourceAsStream);
                    //  JasperDesign design = JRXmlLoader.load("/report/AttendanceReport.jrxml");
                    JasperReport report = JasperCompileManager.compileReport(load);

                    HashMap map = new HashMap();
                    map.put("Date", java.sql.Date.valueOf(date));
                   // map.put("name", name);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Desktop\\Attendance Reports\\"+date+".pdf");

                } catch (SQLException | JRException e) {
                    // e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            }).start();
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please select an Day",ButtonType.OK).show();
        }

    }

}
