package shu.java.csky.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import shu.java.csky.TestCommentService;
import shu.java.csky.TestHistoryService;
import shu.java.csky.TestService;
import shu.java.csky.entity.Test;
import shu.java.csky.entity.TestComment;
import shu.java.csky.entity.TestHistory;
import shu.java.csky.vo.ResStatus;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.param.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 54dbd
 * @date 2022/6/22 20:31
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private TestService testService;

    @Resource
    TestCommentService testCommentService;

    @Resource
    TestHistoryService testHistoryService;

    @PostMapping("/list")
    public ResultVO testList(@RequestBody Page<Test> pageParam) {
        if (pageParam == null) {
            return new ResultVO(400, "参数不能为空", null);
        }
        return testService.getTestVoList(pageParam);
    }

    @PostMapping("/comment/list")
    public ResultVO testCommentList(@RequestBody Page<TestComment> pageParam) {
        if (pageParam == null) {
            return new ResultVO(400, "参数不能为空", null);
        }
        return testCommentService.getCommentVoList(pageParam);
    }

    @GetMapping("/detail/{tid}")
    public ResultVO getTestDetail(@PathVariable Integer tid) {
        if (tid == null || tid == 0) {
            return new ResultVO(400, "参数不能为空", null);
        }
        Test test = testService.getTestDetailByTid(tid);
        return new ResultVO(200, "成功", test);
    }

    @GetMapping("/comment/{questionid}")
    public ResultVO getTestCommentByQuestionid(@PathVariable String questionid) {
        if (questionid == null) {
            return new ResultVO(400, "参数不能为空", null);
        }
        List<TestComment> comment = testCommentService.getTestCommentByQuestionid(questionid);
        return new ResultVO(200, "成功", comment);
    }

    @GetMapping("/testNameList")
    public ResultVO getTestNameList() {
        return new ResultVO(ResStatus.OK, "成功", testService.getTestNameList());
    }

    @PostMapping("/query")
    public ResultVO fuzzyQueryTest(@RequestBody QueryPageParam queryPageParam) {
        return testService.fuzzyQueryTest(queryPageParam);
    }

    @PostMapping("/add")
    public ResultVO saveComment(@RequestBody TestCommentParam commentParam) {
        return new ResultVO(200, "成功", testCommentService.insertComment(commentParam));
    }

    @PostMapping("addNum")
    public ResultVO addNum(@RequestBody TestParam testParam) {
        return new ResultVO(200, "成功", testService.setNumByAid(testParam));

    }

    @GetMapping("/history/{sname}/{userid}")
    public ResultVO getProblemCollectionBySname(@PathVariable("sname") String sname, @PathVariable("userid") String userid) {
        if (sname == null) {
            return new ResultVO(400, "参数不能为空", null);
        }
        Integer userID = Integer.valueOf(userid);
        TestHistoryParam testHistoryParam = new TestHistoryParam();
        testHistoryParam.setSname(sname);
        testHistoryParam.setUserid(userID);
        List<TestHistory> history = testHistoryService.getProblemCollectionBySnameAndUserid(testHistoryParam);
        return new ResultVO(200, "成功", history);
    }

    @PostMapping("/history/submit_choice")
    public ResultVO submitChoice(@RequestBody TestHistoryParam testHistoryParam) {
        TestHistory history = testHistoryService.submitChoice(testHistoryParam);
        return new ResultVO(200, "成功", history);
    }

    @PostMapping("/history/submit_text")
    public ResultVO submitText(@RequestBody TestHistoryParam testHistoryParam) {
        TestHistory history = testHistoryService.submitText(testHistoryParam);
        return new ResultVO(200, "成功", history);
    }

}
