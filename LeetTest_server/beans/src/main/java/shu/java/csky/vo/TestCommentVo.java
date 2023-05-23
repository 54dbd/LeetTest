package shu.java.csky.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.entity.TestComment;

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
