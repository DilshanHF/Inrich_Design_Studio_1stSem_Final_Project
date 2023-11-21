package lk.ijse.InrichDesignStudio.dto.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class itemTm {
    private  String id;
    private  String name;
    private String type;
    private  double amount;
    private JFXButton remove;
}
