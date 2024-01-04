package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.Mail.MailUtil;
import lk.ijse.InrichDesignStudio.Model.*;
import lk.ijse.InrichDesignStudio.bo.custom.PlaceOrderBO;
import lk.ijse.InrichDesignStudio.bo.custom.impl.PlaceOrderBOImpl;
import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.dto.PlaceOrderDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.SystemAlert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderController {
    @FXML
    private JFXButton btnAddToCart;


    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItem;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblCustomerEmail;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblInvoiceId;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<cartTm> tblCart;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtPaymentType;

    @FXML
    private JFXComboBox<String> cmbPaymentType;

    @FXML
    private TextField txtQty;

    PlaceOrderBO placeOrderBO = new PlaceOrderBOImpl();


    private OrderModel oModel = new OrderModel();
   

    private PlaceModel pModel = new PlaceModel();

    private ObservableList<cartTm> obList = FXCollections.observableArrayList();


    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        generateNextInvoiceId();
        setDate();
        loadCustomerIds();
        loadItemCodes();
        loadPaymentTypes();
    }

    private void loadPaymentTypes() {
        List<String> types = Arrays.asList("Cash","Card","Cheque");
        ObservableList<String> typeList = FXCollections.observableArrayList(types);
        cmbPaymentType.setItems(typeList);

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDtos = placeOrderBO.getAllItem();

            for (ItemDto dto : itemDtos) {
                obList.add(dto.getId());
            }
            cmbItem.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
           // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> idList = placeOrderBO.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomerId.setItems(obList);
            //cmbCustomerId.getItems().addAll(obList);

        } catch (SQLException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    private void generateNextInvoiceId() {
        try {
            String id = placeOrderBO.generateNewInvoiceId();
            lblInvoiceId.setText(id);

        } catch (SQLException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    private void generateNextOrderId() {

        try {
            String id = oModel.generateNextId();
            lblOrderId.setText(id);

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        
    }


    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String id =  cmbCustomerId.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            CustomerDto customerDto = placeOrderBO.searchCustomer(id);
            lblCustomerName.setText(customerDto.getName());
            lblCustomerEmail.setText(customerDto.getEmail());

        } catch (SQLException | ClassNotFoundException e) {
           // throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    public void cmbItemOnAction(ActionEvent actionEvent) {
        String code =  cmbItem.getValue();

        txtQty.requestFocus();
        try {
            ItemDto dto = placeOrderBO.searchItem(code);
            lblItemName.setText(dto.getName());
            lblPrice.setText(String.valueOf(dto.getAmount()));

        } catch (SQLException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }

    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String id = lblOrderId.getText();
        String code = cmbItem.getValue();
        String description = lblItemName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblPrice.getText());
        double tot = unitPrice * qty;

        JFXButton button = new JFXButton("delete", new ImageView("assets/trash-can@1.5x.png"));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.getStyleClass().add("infoBtn");
        setRemoveBtnOnAction(button);
        button.setCursor(Cursor.HAND);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(tot);

                    calculateTotal();
                    tblCart.refresh();
                    return;
                }
            }
        }
        var cartTm = new cartTm(code, description, qty, unitPrice, tot, button);

        obList.add(cartTm);

        tblCart.setItems(obList);
        calculateTotal();
        txtQty.clear();


    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void setRemoveBtnOnAction(JFXButton button) {
        button.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new SystemAlert(Alert.AlertType.INFORMATION,"Information","Are you sure to remove '",yes,no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblCart.refresh();
                calculateTotal();
            }else {
                new Alert(Alert.AlertType.ERROR).showAndWait();
            }
        });
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    public void btnOnPlaceOrder(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String customerId = cmbCustomerId.getValue();
        String invoiceId = lblInvoiceId.getText();
        LocalDate handOverdate = txtDate.getValue();
        String type = cmbPaymentType.getValue();
        double amount = Double.parseDouble(lblTotal.getText());
        String email = lblCustomerEmail.getText();
        String name = lblCustomerName.getText();
        String tot = lblTotal.getText();


        List<cartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            cartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        System.out.println("Place order form controller: " + cartTmList);


        var dto = new PlaceOrderDto(orderId, date, customerId, invoiceId, handOverdate, type,amount, cartTmList);
        try {
            boolean isSuccess = pModel.placeOrder(dto);
            if (isSuccess) {
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Order has placed!",ButtonType.OK).show();
                generateInvoice(invoiceId, name,email ,orderId,tot);
                tblCart.getItems().clear();
                clearAll();

            }else {
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Order Not Placed'").showAndWait();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            //throw new RuntimeException(e);
        }
    }

    private void generateInvoice(String invoiceId, String name, String email, String orderId, String tot) {
        if (orderId != null) {
            new Thread(() -> {
                try {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/report/Receipt.jrxml");
                    JasperDesign load = JRXmlLoader.load(resourceAsStream);
                    //  JasperDesign design = JRXmlLoader.load("/report/AttendanceReport.jrxml");
                    JasperReport report = JasperCompileManager.compileReport(load);

                    HashMap map = new HashMap();
                    map.put("id", orderId);
                    map.put("tot", tot);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Desktop\\Attendance Reports\\"+orderId+".pdf");

                    MailUtil mail = new MailUtil();
                    mail.setFile(new File("C:\\Users\\User\\Desktop\\Attendance Reports\\" + orderId + ".pdf"));
                    mail.setTo(email);
                    mail.setSubject("Order Invoice");
                    mail.setMsg("Your Order has Placed and Invoice is attached here");

                    Thread thread = new Thread(mail);
                    thread.start();

                } catch (SQLException | JRException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            }).start();
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please select an employee",ButtonType.OK).show();
        }
    }

    private void clearAll() {
        lblCustomerEmail.setText("");
        lblCustomerName.setText("");
        lblDate.setText("");
        lblInvoiceId.setText("");
        lblTotal.setText("");
        lblOrderId.setText("");
        txtDate.setValue(null);
        lblPrice.setText("");
        //lblTotal.setText("");
        lblItemName.setText("");
        //cmbPaymentType.getSelectionModel().clearSelection();
        cmbCustomerId.getItems().clear();
        cmbItem.getItems().clear();
        cmbPaymentType.getItems().clear();
    }




    public void cmbTypeOnAction(ActionEvent actionEvent) {
    }
}
