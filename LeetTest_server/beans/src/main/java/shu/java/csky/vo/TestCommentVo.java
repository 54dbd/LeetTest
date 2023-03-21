package shu.java.csky.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.entity.TestComment;

/**
 * @author 54dbd
 * @Classname TestCommentVo
 * @date 2022/7/5 20:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCommentVo {
    private Integer commentid;
    private String questionid;
    private Integer userid;
    private String commenttext;
    private String title;

}
