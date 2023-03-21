package shu.java.csky.vo.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 54dbd
 * @Classname TestHistoryParam
 * @date 2022/7/7 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestHistoryParam {
    private String wronganswer;
    private Integer tid;
    private Integer userid;
    private String sname;
    private Integer score;
    private boolean iscorrect;
}
