package shu.java.csky.vo.page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.vo.SchoolVo;
import shu.java.csky.vo.TestVo;

import java.util.List;
/**
 * @author 54dbd
 * @Classname TestPageVo
 * @date 2022/6/22 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPageVo extends Page{
    List<TestVo> testVoList;
}
