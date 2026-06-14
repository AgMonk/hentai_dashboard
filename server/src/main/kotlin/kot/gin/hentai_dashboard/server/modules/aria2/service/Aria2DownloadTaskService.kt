package kot.gin.hentai_dashboard.server.modules.aria2.service

import com.baomidou.mybatisplus.extension.service.IService
import kot.gin.hentai_dashboard.server.modules.aria2.entity.Aria2DownloadTask
import kot.gin.retrofit.aria2.parameter.method.AddUriParam

/**
 * @author hjg71
 * @since 2026/6/14 16:22
 */
interface Aria2DownloadTaskService : IService<Aria2DownloadTask> {
    /**
     * 添加多个下载任务
     */
    fun addTasks(type: String, params: List<AddUriParam>)

    /**
     * 将已有下载任务gid添加到数据库进行管理
     */
    fun addGid(type: String, gidList: List<String>)

    /**
     * 删除下载任务
     */
    fun removeByGid(gidList: List<String>)
}