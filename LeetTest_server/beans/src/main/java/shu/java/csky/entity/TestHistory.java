package shu.java.csky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("problem_collection")
public class TestHistory {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer tid;

    private String sname;

    private Integer userid;

    private String wronganswer;

    private Timestamp createdate;

    private Integer score;

    private boolean iscorrect;
}
