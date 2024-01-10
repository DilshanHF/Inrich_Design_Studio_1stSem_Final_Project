package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.InrichDesignStudio.bo.custom.AttendanceBO;
import lk.ijse.InrichDesignStudio.bo.custom.EmployeeBO;
import lk.ijse.InrichDesignStudio.bo.factory.BOFactory;
import lk.ijse.InrichDesignStudio.bo.factory.BOTypes;
import lk.ijse.InrichDesignStudio.dto.Tm.attendanceTm;
import lk.ijse.InrichDesignStudio.dto.AttendDto;
import lk.ijse.InrichDesignStudio.dto.EmployeeDto;
import util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class AttendanceController {
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<attendanceTm> tblMarkAttend;

    @FXML
    private JFXButton btnMarkAtt;
    ObservableList<attendanceTm> employee = FXCollections.observableArrayList();


    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOTypes.EMPLOYEE);

    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOTypes.ATTENDANCE);



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

    public void initialize() {
        loadDateandTime();
        // loadAllAttendance();
        setValueFactory();
    }

    private void setValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("attendance"));
    }

    private void loadDateandTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalTime.now().format(format2));
        }), new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void QrScanOnBtn(ActionEvent actionEvent) throws IOException {
        //mainPane.getChildren().clear();
        //mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/QrScanForm.fxml")));

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/QrScanForm.fxml"));
        Parent root = fxmlLoader.load();

        QrScanController qr = fxmlLoader.getController();
        qr.setAttController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void btnOnMarkAttendance(ActionEvent actionEvent) {
        LocalDate date = LocalDate.parse(lblDate.getText());



                ArrayList<AttendDto> adto = new ArrayList<>();
                    for (int i = 0; i <tblMarkAttend.getItems().size();i++) {
                        String id = (String) colId.getCellData(i);
                        String data = (String) colStatus.getCellData(i);
                        String name = (String) colName.getCellData(i);
                        String nic = (String) colNic.getCellData(i);

                        adto.add(new AttendDto(id, name, nic, date, data));
                    }
                    try {
                        boolean isExistId = attendanceBO.existAttendance(adto);
                        if(!isExistId){
                            boolean isSavedmodel= attendanceBO.saveAttendance(adto);
                            if (isSavedmodel){
                                //new Alert(Alert.AlertType.CONFIRMATION,"Attendance Marked").showAndWait();
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Success", "Attendance Marked", ButtonType.OK).show();
                                tblMarkAttend.getItems().clear();

                            }else {
                                //new Alert(Alert.AlertType.ERROR,"Not marked").showAndWait();
                                new SystemAlert(Alert.AlertType.ERROR, "Error", "Not Marked", ButtonType.OK).show();
                            }

                        } else {
                            //new Alert(Alert.AlertType.ERROR, "Already Marked.").showAndWait();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Aready Marked", ButtonType.OK).show();
                        }


                    } catch (SQLException | ClassNotFoundException e) {
                        //throw new RuntimeException(e);
                        new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                    }



    }

       

    public void markAttendance(String id) throws SQLException {


        String empId = id;
        System.out.println(id);

        EmployeeDto dto = null;
        try {
            dto = employeeBO.searchEmployee(empId);
            attendanceTm tm = new attendanceTm(dto.getId(), dto.getName(), dto.getNic(), "PRESENT");

            employee.add(tm);

            tblMarkAttend.setItems(employee);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }






    }
}

