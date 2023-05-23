package shu.java.csky.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestNameVo {
    private Integer tid;

    private String sname;

    private String points;

    private String qtype;

    private String question;

    private Integer atype;

    private double accuracy;

    private String difficulty;

    private Integer num;

    private Integer correctnum;
}
