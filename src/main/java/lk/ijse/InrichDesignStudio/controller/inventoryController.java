package lk.ijse.InrichDesignStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.InrichDesignStudio.Model.itemModel;
import lk.ijse.InrichDesignStudio.dto.Tm.customerTm;
import lk.ijse.InrichDesignStudio.dto.Tm.itemTm;
import lk.ijse.InrichDesignStudio.dto.customerDto;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import lk.ijse.InrichDesignStudio.dto.itemDto;
import util.Regex;
import util.SystemAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class inventoryController implements Initializable {
    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrize;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colWood;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<itemTm> tblItems;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrise;

    @FXML
    private TextField txtType;

    itemModel iModel = new itemModel();

    public void btnOnInventoryDetails(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/inventoryDetailForm.fxml")));

    }

    public void btnOnItems(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/inventoryForm.fxml")));

    }

    public void btnOnAddItem(ActionEvent actionEvent) {
        boolean isExists = false;
        try {
            isExists = iModel.exitItem(txtId.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }

        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtType.getText().isEmpty() || txtPrise.getText().isEmpty())) {
            if (Regex.getItemCode().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);
                if (Regex.getDoublePattern().matcher(txtPrise.getText()).matches()) {
                    resetFieldStyle(txtPrise);

                    if (!isExists) {
                        resetFieldStyle(txtId);

                        String id = txtId.getText();
                        String name = txtName.getText();
                        String type = txtType.getText();
                        double amount = Double.parseDouble(txtPrise.getText());

                        //Double salary = Double.valueOf(txtSalary.getText());
                        var dto = new itemDto(id, name,type,amount);

                        try {
                            boolean isSaved = iModel.saveItem(dto);
                            if (isSaved) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Item  saved!", ButtonType.OK).show();
                                //tblCustomer.refresh();
                                clearField();
                                loadAllItems();


                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Employee not saved!", ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                            //new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                        }


                    } else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", " Item Id already exist", ButtonType.OK).show();
                        setFocusColorRed(txtId);
                    }
                } else {
                    new SystemAlert(Alert.AlertType.ERROR, "Error", " Item Id already exist", ButtonType.OK).show();
                    setFocusColorRed(txtPrise);
                }
            } else {
                new SystemAlert(Alert.AlertType.ERROR, "Error", "Invalid Item Id ", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        } else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtType);
            setFocusColorRed(txtPrise);

        }


    }


    private void setFocusColorRed(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }

    private void resetFieldStyle(TextField field) {
        field.setStyle("");
    }

    public void btnOnUpdateItem(ActionEvent actionEvent) {
        if (!(txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtType.getText().isEmpty() || txtPrise.getText().isEmpty() )) {
            if (Regex.getItemCode().matcher(txtId.getText()).matches()) {
                resetFieldStyle(txtId);

                    if (Regex.getDoublePattern().matcher(txtPrise.getText()).matches()) {
                        resetFieldStyle(txtPrise);




                        String id = txtId.getText();
                        String name = txtName.getText();
                        String type = txtType.getText();
                        double amount = Double.parseDouble(txtPrise.getText());


                        var dto = new itemDto(id, name, type,amount);

                        try {
                            boolean isUpdated = iModel.updateItem(dto);
                            if (isUpdated) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Item updated!", ButtonType.OK).show();
                                clearField();
                                loadAllItems();

                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Item not updated!", ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Somehing went wrong!", ButtonType.OK).show();
                        }

                    }else {
                        new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Amount", ButtonType.OK).show();
                        setFocusColorRed(txtPrise);

                    }

            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Item Id", ButtonType.OK).show();
                setFocusColorRed(txtId);
            }
        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please fill fields", ButtonType.OK).show();
            setFocusColorRed(txtId);
            setFocusColorRed(txtName);
            setFocusColorRed(txtType);
            setFocusColorRed(txtPrise);


        }

    }

    public void btnOnDeleteItem(ActionEvent actionEvent) {

        if (!txtId.getText().isEmpty()) {
            if (Regex.getItemCode().matcher(txtId.getText()).matches()) {
                boolean isExists = false;
                try {
                    isExists = iModel.exitItem(txtId.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
                if (isExists) {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new SystemAlert(Alert.AlertType.INFORMATION, "Information", "Are you sure to delete ?", yes, no).showAndWait();

                    String id = txtId.getText();

                    if (result.orElse(no) == yes) {
                        resetFieldStyle(txtId);
                        //lblError.setText("");

                        try {
                            boolean isDeleted = iModel.deleteItem(id);
                            if (isDeleted) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Item has deleted!", ButtonType.OK).show();
                                clearField();
                                loadAllItems();
                                //populateEmployeeTable();
                                //searchFilter();
                            } else {
                                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Item not deleted!", ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
                        }
                    }
                }else {
                    new SystemAlert(Alert.AlertType.WARNING, "Warning", "No Item Found!", ButtonType.OK).showAndWait();
                    clearField();

                }
            }else {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Invalid Item Id ", ButtonType.OK).showAndWait();
                setFocusColorRed(txtId);

                //clearField();
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter Item Id ", ButtonType.OK).showAndWait();
            setFocusColorRed(txtId);
        }

    }
    private void clearField() {
        txtId.clear();
        txtName.clear();
        txtType.clear();
        txtPrise.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            loadAllItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllItems() throws SQLException {
        ObservableList<itemTm> obList = FXCollections.observableArrayList();

        List<itemDto> dtoList = iModel.getAllItem();
        for (itemDto dto : dtoList) {
            JFXButton button = new JFXButton("edit", new ImageView("assets/edit-97@30x.png"));
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.getStyleClass().add("infoBtn");
            setButtonOnAction(button);
            obList.add(
                    new itemTm(
                            dto.getId(),
                            dto.getName(),
                            dto.getType(),
                            dto.getAmount(),
                            button
                    )
            );
        }

        tblItems.setItems(obList);
        tblItems.refresh();

    }

    private void setButtonOnAction(JFXButton button) {
        button.setOnAction((e) -> {
            int index = -1;

            for (int i=0 ; i < tblItems.getItems().size(); i++){
                if (colAction.getCellData(i).equals(button)){
                    index = i;
                }
            }
            itemTm item = tblItems.getItems().get(index);
            txtId.setText(item.getId());
            txtName.setText(item.getName());
            txtType.setText(item.getType());
            txtPrise.setText(String.valueOf(item.getAmount()));

        });
    }

    private void setCellValueFactory() {
        colItem.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWood.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrize.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }
}
