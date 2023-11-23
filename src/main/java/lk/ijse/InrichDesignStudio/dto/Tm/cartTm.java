package lk.ijse.InrichDesignStudio.dto.Tm;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class cartTm {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double tot;
    private JFXButton button;
}
