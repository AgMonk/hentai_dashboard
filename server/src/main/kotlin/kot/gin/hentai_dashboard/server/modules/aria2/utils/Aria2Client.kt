package kot.gin.hentai_dashboard.server.modules.aria2.utils

import kot.gin.retrofit.aria2.parameter.method.AddUriParam
import kot.gin.retrofit.aria2.parameter.request.Aria2AllTasksRequest
import kot.gin.retrofit.aria2.parameter.request.Aria2MultiAddUriRequest
import kot.gin.retrofit.aria2.parameter.request.parameter.request.Aria2MultiRemoveResultRequest
import kot.gin.retrofit.aria2.repository.Aria2ApiRepository

/**
 * @author hjg71
 * @since 2026/6/14 15:39
 */
class Aria2Client {
    companion object {
        private val repo = Aria2ApiRepository()

        /**
         * 插叙所有下载任务
         */
        fun listAll() = repo.multiCallApi.allTasks(Aria2AllTasksRequest(num = 5000))
            .execute().body()?.result?.flatten()?.flatten()

        /**
         *   添加多个下载任务
         */
        fun addUris(params: List<AddUriParam>) = repo.multiCallApi.multiAddUri(Aria2MultiAddUriRequest(params))
            .execute().body()?.result?.flatten()

        /**
         * 移除多个任务
         */
        fun removes(gidList: List<String>) = repo.multiCallApi.multiRemoveResult(Aria2MultiRemoveResultRequest(gidList))
            .execute().body()?.result?.flatten()
    }
}