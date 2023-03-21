package shu.java.csky.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPageParam extends QueryPageParam{
    private Integer sid;
}
