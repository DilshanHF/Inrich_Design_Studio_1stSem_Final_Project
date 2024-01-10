package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.bo.custom.InventoryBO;
import lk.ijse.InrichDesignStudio.bo.factory.BOFactory;
import lk.ijse.InrichDesignStudio.bo.factory.BOTypes;
import lk.ijse.InrichDesignStudio.dto.Tm.inventoryTm;
import lk.ijse.InrichDesignStudio.dto.InventoryDto;
import util.SystemAlert;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;



public class InventoryDetailController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<inventoryTm> tblInventory;


    InventoryBO inventoryBO = (InventoryBO) BOFactory.getBoFactory().getBO(BOTypes.INVENTORY);


    public void btnOnInventoryDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/inventoryDetailForm.fxml")));

    }

    public void btnOnItem(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/inventoryForm.fxml")));
    }

    public void initialize() {
        setCellValueFactory();
        loadAllInventory();
       
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    private void loadAllInventory() {
        ObservableList<inventoryTm> obList = FXCollections.observableArrayList();

        try {
            List<InventoryDto> dtoList =inventoryBO.getAllInventory();

            for (InventoryDto dto : dtoList) {
                JFXTextField button = new JFXTextField("Pending");
                button.getStyleClass().add("pendingBtn");
                button.getStyleClass().add("infoBtn");
                setButtonOnAction(button);
                button.setCursor(Cursor.HAND);
                obList.add(
                        new inventoryTm(
                                dto.getOrderId(),
                                dto.getItemCode(),
                                dto.getQty(),
                                dto.getDate(),
                                button
                        )
                );
            }

            tblInventory.setItems(obList);
            tblInventory.refresh();
        } catch (SQLException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    private void setButtonOnAction(JFXTextField button) {
        button.setOnAction(e -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new SystemAlert(Alert.AlertType.INFORMATION,"Information","Are you sure that order is complete '",yes,no).showAndWait();
            if (type.orElse(no) == yes){
                button.setText("Completed");
                button.setStyle("-fx-background-color: green");
                button.setCursor(Cursor.HAND);
            }


        });
    }


    public void btnOnReport(ActionEvent actionEvent) {
        try {
            InputStream design = getClass().getResourceAsStream("/report/Invoice.jrxml");
            // System.out.println(getClass().getResource("../report/Invoice_form.jrxml"));
            JasperDesign load = JRXmlLoader.load(design);

            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.getMessage();

            //new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
