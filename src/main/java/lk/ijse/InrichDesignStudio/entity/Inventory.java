package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Inventory {
    private String orderId;
    private String itemCode;
    private int qty;
    private LocalDate date;
}
