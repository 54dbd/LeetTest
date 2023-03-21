package shu.java.csky.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 54dbd
 * @Classname TestCommentParam
 * @date 2022/7/5 17:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCommentParam {
    private Integer commentid;
    private String questionid;
    private String commenttext;
    private Integer userid;
    private String title;


}
