package kot.gin.hentai_dashboard.server.modules.aria2.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import kot.gin.hentai_dashboard.server.modules.aria2.entity.Aria2DownloadTask
import org.apache.ibatis.annotations.Mapper

/**
 * @author hjg71
 * @since 2026/6/14 16:21
 */
@Mapper
interface Aria2DownloadTaskMapper :BaseMapper<Aria2DownloadTask>