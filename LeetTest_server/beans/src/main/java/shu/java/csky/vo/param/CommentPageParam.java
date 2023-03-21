package shu.java.csky.vo.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.entity.Comment;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPageParam {
    private Integer articleId;
    private Integer userId;
    private Page<Comment> page;
}
