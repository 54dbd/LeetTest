package shu.java.csky.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.vo.TestCommentVo;

import java.util.List;

/**
 * @author 54dbd
 * @Classname TestCommentPageVo
 * @date 2022/7/6 13:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCommentPageVo extends Page{
    List<TestCommentVo> testCommentVoList;
}
