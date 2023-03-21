package shu.java.csky.vo.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 54dbd
 * @Classname TestParam
 * @date 2022/7/7 8:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestParam {
    private String sname;
    private Integer num;
    private Integer correctnum;
    private double accuracy;
    private String points;
}
