package shu.java.csky;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestBody;
import shu.java.csky.entity.TestComment;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.SchoolNameVo;
import shu.java.csky.vo.TestCommentNameVo;
import shu.java.csky.vo.TestCommentVo;
import shu.java.csky.vo.param.ArticleParam;
import shu.java.csky.vo.param.TestCommentParam;
import shu.java.csky.vo.param.IdParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 54dbd
 * @Classname TestCommentService
 * @date 2022/7/5 19:59
 */
public interface TestCommentService {

    /**
     * 根据题目的Id，获取题目的评论
     */
    TestComment getCommentDetailByCommentId(Integer commentid);

    List<TestComment> getTestCommentByQuestionid(String questionid);

    ResultVO getCommentVoList(Page<TestComment> pageParam);

    List<TestCommentNameVo> getCommentNameList();

    ResultVO insertComment(TestCommentParam commentParam);


}
