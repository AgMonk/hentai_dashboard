package kot.gin.hentai_dashboard.server.modules.aria2.service

import kot.gin.hentai_dashboard.server.modules.aria2.entity.Aria2DownloadTask
import kot.gin.retrofit.aria2.response.DownloadTask

/**
 * @author hjg71
 * @since 2026/6/14 15:51
 */
interface Aria2TaskHandler {
    /**
     *处理任务
     * @param tasks 实时任务列表
     * @param list 已管理的任务列表
     */
    fun handle(tasks: List<DownloadTask>, list: List<Aria2DownloadTask>)
}