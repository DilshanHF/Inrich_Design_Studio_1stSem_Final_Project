package lk.ijse.InrichDesignStudio.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class expenseDto {
    private String id;
    private LocalDate date;
    private String des;
    private String type;
    private double amount;
}
