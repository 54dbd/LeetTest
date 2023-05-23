package shu.java.csky.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.vo.TestCommentVo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCommentPageVo extends Page{
    List<TestCommentVo> testCommentVoList;
}
