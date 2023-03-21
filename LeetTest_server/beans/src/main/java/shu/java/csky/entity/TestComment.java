package shu.java.csky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 54dbd
 * @Classname TestComment
 * @date 2022/7/5 20:06
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("questions_comment")
public class TestComment {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer commentid;

    /**
     * 问题id
     */
    private String questionid;

    /**
     * 作者Id
     */
    private Integer userid;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 内容
     */
    private String commenttext;


}
