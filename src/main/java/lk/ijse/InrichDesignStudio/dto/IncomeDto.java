package lk.ijse.InrichDesignStudio.dto;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IncomeDto {
    private String invoiceId;
    private String orderId;
    private String type;
    private double amount;
    private LocalDate date;
}
