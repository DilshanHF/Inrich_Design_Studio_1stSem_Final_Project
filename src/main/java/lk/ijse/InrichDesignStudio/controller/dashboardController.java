package lk.ijse.InrichDesignStudio.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.InrichDesignStudio.Model.*;
import util.SystemAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    public AnchorPane pane;
    public Pane sidepane;
    public AnchorPane mainPane;
    @FXML
    private Label lblOrders;

    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployee;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;
    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private PieChart pieChart;
    
    queryModel qModel = new queryModel();

    customerModel cusModel = new customerModel();
    orderModel oModel = new orderModel();
    employeeModel eModel = new employeeModel();

    incomeModel iModel = new incomeModel();
    expensesModel exModel = new expensesModel();


    public void btnOnDasboard(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/Dashbord.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    public void btnOnCustomer(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));

    }

    public void btnOnEmployee(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml")));
    }

    public void btnOnOrders(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ordersForm.fxml")));
    }



    public void btnOnInventory(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/inventoryForm.fxml")));
    }

    public void btnOnIncome(ActionEvent actionEvent) throws IOException {
        /*mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/incomeForm.fxml")));*/
    }

    public void btnOnExpenses(ActionEvent actionEvent) throws IOException {
        /*mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));*/
    }

    public void btnOnFinance(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));


    }

    public void btnOnLogOut(ActionEvent actionEvent) throws IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new SystemAlert(Alert.AlertType.INFORMATION, "Logout", "Do you want to logout?", yes, no).showAndWait();
        if (result.orElse(no) == yes) {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Log In Page");
            stage.centerOnScreen();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateandTime();
        //setValueFactory();
        //populateUpcomingTable();
        loadBarChart();
        loadDetails();
        setPieChart();
    }

    private void loadBarChart() {
        try {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Income", iModel.getTotalIncome()));
            series.getData().add(new XYChart.Data<>("Expenses", exModel.getTotalExpenses()));
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    private void setPieChart() {
        try {
            double totalIncome = iModel.getTotalIncome();
            double totalExpenses = exModel.getTotalExpenses();
            pieChart.getData().add(new PieChart.Data("Income", Double.parseDouble(String.valueOf(totalIncome))));
            pieChart.getData().add(new PieChart.Data("Expenses", Double.parseDouble(String.valueOf(totalExpenses))));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    private void loadDetails() {
        try {
            String totalCustomer = cusModel.getTotalCustomers();
            String totalOrders = oModel.getTotalOrders();
            String totalEmployees = eModel.getTotalEmployees();
            lblCustomer.setText(totalCustomer);
            lblOrders.setText(totalOrders);
            lblEmployee.setText(totalEmployees);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
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
}

