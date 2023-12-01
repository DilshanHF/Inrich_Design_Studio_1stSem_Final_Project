package lk.ijse.InrichDesignStudio.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpenseDto {
    private String id;
    private LocalDate date;
    private String des;
    private String type;
    private double amount;
}
