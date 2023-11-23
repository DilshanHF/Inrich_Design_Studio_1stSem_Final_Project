package lk.ijse.InrichDesignStudio.dto;

import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class placeOrderDto {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private String invoiceId;
    private LocalDate handOverdate;
    private String type;
    private double amount;
    private List<cartTm> cartTmList = new ArrayList<>();
}
