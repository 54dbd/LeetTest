package com.shu.leettest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.param.QueryPageParam;
import com.shu.leettest.vo.param.TestCommentParam;
import com.shu.leettest.vo.param.TestHistoryParam;
import com.shu.leettest.vo.param.TestParam;
import org.springframework.web.bind.annotation.*;
import com.shu.leettest.TestCommentService;
import com.shu.leettest.TestHistoryService;
import com.shu.leettest.TestService;
import com.shu.leettest.entity.Test;
import com.shu.leettest.entity.TestComment;
import com.shu.leettest.entity.TestHistory;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/query/{userid}")
    public ResultVO queryTestByID(@PathVariable("userid") int userid) {
        List<Test> history = testHistoryService.queryTestByID(userid);
        return new ResultVO(200, "成功", history);
    }


}
