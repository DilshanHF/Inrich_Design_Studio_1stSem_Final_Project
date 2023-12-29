package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Income {
    private String invoiceId;
    private String orderId;
    private String type;
    private double amount;
    private LocalDate date;
}
