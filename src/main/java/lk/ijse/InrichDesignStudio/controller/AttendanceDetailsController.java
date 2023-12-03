package lk.ijse.InrichDesignStudio.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.Model.AttendanceModel;
import lk.ijse.InrichDesignStudio.Model.EmployeeModel;
import lk.ijse.InrichDesignStudio.dto.Tm.attendanceTm;
import lk.ijse.InrichDesignStudio.dto.EmployeeDto;
import lk.ijse.InrichDesignStudio.qr.QrGenerator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.SystemAlert;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AttendanceDetailsController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbEmployeeId;
    @FXML
    private Label lblName;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView pc;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<attendanceTm> tblAttendance;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXComboBox<String> cmbOnReport;

    @FXML
    private Label lblName1;

    EmployeeModel eModel = new EmployeeModel();
    AttendanceModel model = new AttendanceModel();

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
            EmployeeDto dto = eModel.searchEmployee(id);
            lblName.setText(dto.getName());


        } catch (SQLException e) {
            // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("attendance"));
    }

    private void loadEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> empDto = eModel.getAllEmployee();

            for (EmployeeDto dto : empDto) {
                obList.add(dto.getId());
            }
            cmbEmployeeId.setItems(obList);
            cmbOnReport.setItems(obList);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

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

    public void datePickerOnAction(ActionEvent actionEvent)  {
        LocalDate date = LocalDate.parse(txtDate.getValue().toString());
        ObservableList<attendanceTm> attendance = null;
        try {
            attendance = model.getAttendanceOfDay(String.valueOf(date));
            tblAttendance.setItems(attendance);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    public void cmbOnGetReport(ActionEvent actionEvent) {
        String id = (String) cmbOnReport.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            EmployeeDto dto = eModel.searchEmployee(id);
            lblName1.setText(dto.getName());


        } catch (SQLException e) {
            // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();

        }

    }

    public void btnOnGetReport(ActionEvent actionEvent) {
        String id = (String) cmbOnReport.getSelectionModel().getSelectedItem();
        String name = lblName1.getText();
        if (id != null) {
            new Thread(() -> {
                try {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/report/AttendanceReport.jrxml");
                    JasperDesign load = JRXmlLoader.load(resourceAsStream);
                  //  JasperDesign design = JRXmlLoader.load("/report/AttendanceReport.jrxml");
                    JasperReport report = JasperCompileManager.compileReport(load);

                    HashMap map = new HashMap();
                    map.put("id", id);
                    map.put("name", name);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Desktop\\Attendance Reports\\"+id+".pdf");

                } catch (SQLException | JRException e) {
                   // e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            }).start();
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please select an employee",ButtonType.OK).show();
        }

    }
}
