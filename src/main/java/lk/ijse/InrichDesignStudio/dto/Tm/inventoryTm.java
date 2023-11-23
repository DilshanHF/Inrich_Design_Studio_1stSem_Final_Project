package lk.ijse.InrichDesignStudio.dto.Tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class inventoryTm {
    private String orderId;
    private String itemCode;
    private int qty;
    private LocalDate date;

    private JFXTextField button;
}
