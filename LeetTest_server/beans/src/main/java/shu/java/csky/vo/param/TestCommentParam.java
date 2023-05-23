package shu.java.csky.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
