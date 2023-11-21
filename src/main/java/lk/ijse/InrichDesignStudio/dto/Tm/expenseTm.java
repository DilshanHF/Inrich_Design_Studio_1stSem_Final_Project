package lk.ijse.InrichDesignStudio.dto.Tm;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class expenseTm {
    private String id;
    private LocalDate date;
    private String des;
    private String type;
    private double amount;
}
