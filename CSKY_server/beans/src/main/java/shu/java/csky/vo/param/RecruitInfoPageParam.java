package shu.java.csky.vo.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.entity.Articles;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitInfoPageParam {
    private Integer schoolId;
    private Page<Articles> page;
}
