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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.InrichDesignStudio.Model.attendanceModel;
import lk.ijse.InrichDesignStudio.Model.employeeModel;
import lk.ijse.InrichDesignStudio.dto.Tm.attendanceTm;
import lk.ijse.InrichDesignStudio.dto.attendDto;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class attendanceController {
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

    employeeModel emodel = new employeeModel();
    attendanceModel model = new attendanceModel();


    public void btnOnAttendance(ActionEvent actionEvent) {
    }

    public void btnOnAttendanceDetails(ActionEvent actionEvent) {
    }

    public void btnOnEmployeeDetails(ActionEvent actionEvent) {
    }

    public void initialize(){
        loadDateandTime();
        loadAllAttendance();
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalTime.now().format(format2));
        }), new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void QrScanOnBtn(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/QrScanForm.fxml")));

    }

    public void btnOnMarkAttendance(ActionEvent actionEvent) {
        LocalDate date = LocalDate.parse(lblDate.getText());
        boolean isMarked = false;
        try {
            isMarked = model.existAttendance(date);
        } catch (SQLException e) {
           // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
        if (!isMarked){
            //ArrayList<attendDto> att = new ArrayList<attendDto>();
            //attendDto att = new attendDto();
            ObservableList<attendDto> att = FXCollections.observableArrayList();
            for(attendanceTm tm : tblMarkAttend.getItems()){
                String data = tm.getAttendance();
                String id = tm.getE_id();
                LocalTime time = LocalTime.parse(lblTime.getText());
                try {
                    attendDto Att;

                    if (data.equals("Present")){
                        Att = new attendDto(id,time,date,"Present");
                    }else {
                        Att = new attendDto(id,time,date,"Absent");
                    }
                    att.add(Att);

                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    //new SystemAlert(Alert.AlertType.ERROR,"Error","Something went wrong!", ButtonType.OK).show();
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
                }
            }
            try {
                boolean isSaved = model.saveAttendance(att);
                if (isSaved){
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Attendance saved successfully!",ButtonType.OK).show();
                    loadAllAttendance();
                }else {
                    new SystemAlert(Alert.AlertType.WARNING,"Warning","Attendence not saved!",ButtonType.OK).show();
                }
            } catch (SQLException e) {
                //throw new RuntimeException(e);
                new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Attendance already marked!",ButtonType.OK).show();
        }


    }

    private void loadAllAttendance() {
        try {
            boolean isMarked = model.existAttendance(LocalDate.now());
            if (!isMarked){
                List<employeeDto> edto = emodel.getAllEmployee();
                ObservableList<attendanceTm> temp = FXCollections.observableArrayList();
                for(employeeDto dto : edto){
                    temp.add(new attendanceTm(dto.getId(), dto.getName(), dto.getNic(), "Not Marked"));
                }
                employee = temp;
                tblMarkAttend.setItems(employee);
            }else {
                //ObservableList<attendanceTm> marked = model.
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    public void markAttendance(String id) {
        for (attendanceTm attend : employee) {
            if (attend.getE_id().equals(id)) {
                attend.setAttendance("Present");
                tblMarkAttend.refresh();
                break;
            }
        }


    }
}
