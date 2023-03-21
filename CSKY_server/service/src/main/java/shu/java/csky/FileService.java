package shu.java.csky;

import com.baomidou.mybatisplus.core.metadata.IPage;
import shu.java.csky.entity.MyFile;
import shu.java.csky.vo.ResultVO;


public interface FileService {
    ResultVO getFilePageBySubjectCode(String subject, IPage<MyFile> page);
}
