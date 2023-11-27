package lk.ijse.InrichDesignStudio.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Model.employeeModel;
import lk.ijse.InrichDesignStudio.dto.customerDto;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import lk.ijse.InrichDesignStudio.dto.itemDto;
import lk.ijse.InrichDesignStudio.qr.QrGenerator;
import util.SystemAlert;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

public class attendanceDetailsController implements Initializable {
    @FXML
    private JFXComboBox<String> cmbEmployeeId;
    @FXML
    private Label lblName;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView pc;

    employeeModel eModel = new employeeModel();

    public void btnOnGetQR(ActionEvent actionEvent) {
        if (cmbEmployeeId.getSelectionModel().getSelectedItem() != null){
            String id = cmbEmployeeId.getSelectionModel().getSelectedItem();
            QrGenerator qrGenerator = new QrGenerator();
            qrGenerator.setData(id);
            try {
                  qrGenerator.getGenerator();

            } catch (IOException | WriterException e) {
                //throw new RuntimeException(e);
                new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }
            File file = new File(qrGenerator.getPath());
            System.out.println(file);
            cmbEmployeeId.getSelectionModel().clearSelection();
            lblName.setText("");
            //Image image = new Image(file.toURI().toString());
            //pc.setImage(image);
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please select employee Id to generate QR.", ButtonType.OK).show();
        }

    }

    public void cmbOnAction(ActionEvent actionEvent) {
        String id =  cmbEmployeeId.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            employeeDto dto = eModel.searchEmployee(id);
            lblName.setText(dto.getName());


        } catch (SQLException e) {
            // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeId();
    }

    private void loadEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<employeeDto> empDto = eModel.getAllEmployee();

            for (employeeDto dto : empDto) {
                obList.add(dto.getId());
            }
            cmbEmployeeId.setItems(obList);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    public void btnOnAttendance(ActionEvent actionEvent) {
    }

    public void btnOnAttendanceDetails(ActionEvent actionEvent) {
    }

    public void btnOnEmployeeDetails(ActionEvent actionEvent) {
    }
}
