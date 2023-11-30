package lk.ijse.InrichDesignStudio.dto.Tm;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class markAttendanceTm {
    private  String e_id;
    private String e_name;

    private String nic;
    private String status;
}
