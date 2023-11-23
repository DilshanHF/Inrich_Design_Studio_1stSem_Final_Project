package lk.ijse.InrichDesignStudio.dto.Tm;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class incomeTm {
    private String invoiceId;
    private String orderId;
    private String type;
    private double amount;
    private LocalDate date;
}
