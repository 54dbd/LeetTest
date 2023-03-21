package shu.java.csky.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 54dbd
 * @Classname TestNameVo
 * @date 2022/6/22 22:34
 */
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
