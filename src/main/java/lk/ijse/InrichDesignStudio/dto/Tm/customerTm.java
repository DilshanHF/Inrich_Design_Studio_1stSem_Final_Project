package lk.ijse.InrichDesignStudio.dto.Tm;


import com.jfoenix.controls.JFXButton;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class customerTm {
    private String id;
    private  String name;
    private String address;
    private  String tel;
    private  String email;
    private JFXButton remove;
}
