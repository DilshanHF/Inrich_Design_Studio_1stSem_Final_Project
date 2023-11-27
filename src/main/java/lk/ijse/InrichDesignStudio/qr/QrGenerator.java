package lk.ijse.InrichDesignStudio.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.control.Alert;
import util.SystemAlert;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrGenerator {
    private String data;
    private String path;

    public void setData(String data) {
        this.data = data;
    }
    public String getPath(){
        return path;
    }

    public void getGenerator() throws IOException, WriterException {
        path = "C:\\Users\\User\\Desktop\\" + data + ".png";
        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 300, 300);
        Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode, path.substring(path.lastIndexOf('.') + 1), path1);
        //new SystemAlert(Alert.AlertType.INFORMATION,data+": QR Successfully Generated").show();
        new SystemAlert(Alert.AlertType.INFORMATION, data +"Information", "QR Succesfully Generate").showAndWait();

    }

}
