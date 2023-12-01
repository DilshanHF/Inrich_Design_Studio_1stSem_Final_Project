package lk.ijse.InrichDesignStudio.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import lk.ijse.InrichDesignStudio.dto.Tm.attendanceTm;
import lombok.Setter;

//import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class QrScanController {
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnStop;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane minipane;

    @FXML
    private ProgressBar progress;

    @FXML
    private JFXTextArea txtArea;
    private  boolean isReading = false;
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    @FXML
    private AnchorPane mainPane;
    ObservableList<attendanceTm> employee = FXCollections.observableArrayList();

    @Setter
    private AttendanceController attController ;

    public void initialize(){
        setGif();
        progress.setVisible(false);

    }

    private void setGif() {
        Image image = new Image(new File("src/main/resources/assets/loading-75.gif").toURI().toString());
        imageView.setImage(image);
    }

    public void startBtnOnAction(ActionEvent actionEvent) {
        progress.setVisible(true);
        isReading = (!isReading) ? startWebcam() : stopWebcam();


    }

    private boolean stopWebcam() {
        if (webcamPanel != null) {
            webcamPanel.stop();
            webcamPanel = null;
        }
        if (webcam != null) {
            webcam.close();
            webcam = null;
        }
        return false;
    }

    private boolean startWebcam() {

        if (webcam == null) {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);

            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(size);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setMirrored(true);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(webcamPanel);

            minipane.getChildren().clear();
            minipane.getChildren().add(swingNode);
            //progress.progressProperty().bind(webcam.;
        }

        Thread thread = new Thread(() -> {
            while (isReading) {
                try {
                    Thread.sleep(1000);
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result result = new MultiFormatReader().decode(binaryBitmap);
                        Platform.runLater(() -> {
                            if (result != null) {
                                webcam.close();
                                txtArea.appendText(result.getText() + "\n");
                                String id = result.getText();
                                //String name = lblName.getText();
                                try {
                                    attController.markAttendance(id);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                new Alert(Alert.AlertType.INFORMATION, "Data Scanned Successfully!").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "No Data Found!").showAndWait();
                            }
                        });
                    }
                } catch (NotFoundException | InterruptedException | RuntimeException ignored) {
                    // ignored
                }
            }
        });
        thread.start();
        return true;


    }

    public void stopBtnOnAction(ActionEvent actionEvent) {
            stopWebcam();

    }







    public void btnOnBack(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/markAttendanceForm.fxml")));
    }
}
