package kot.gin.hentai_dashboard.server.modules.aria2.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import kot.gin.hentai_dashboard.server.modules.aria2.entity.Aria2DownloadTask
import kot.gin.hentai_dashboard.server.modules.aria2.mapper.Aria2DownloadTaskMapper
import kot.gin.hentai_dashboard.server.modules.aria2.service.Aria2DownloadTaskService
import kot.gin.hentai_dashboard.server.modules.aria2.utils.Aria2Client
import kot.gin.retrofit.aria2.parameter.method.AddUriParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class Aria2DownloadTaskServiceImpl : ServiceImpl<Aria2DownloadTaskMapper, Aria2DownloadTask>(),
    Aria2DownloadTaskService {
    override fun addTasks(type: String, params: List<AddUriParam>) {
        val gidList = Aria2Client.addUris(params) ?: throw RuntimeException("添加任务失败, 返回值为空")
        addGid(type, gidList)
    }

    override fun addGid(type: String, gidList: List<String>) {
        saveBatch(gidList.map {
            Aria2DownloadTask().apply {
                this.gid = it
                this.type = type
            }
        })
    }

    override fun removeByGid(gidList: List<String>) {
        Aria2Client.removes(gidList) ?: throw RuntimeException("删除任务失败, 返回值为空")
        remove(KtQueryWrapper(Aria2DownloadTask::class.java)
            .`in`(Aria2DownloadTask::gid, gidList)
        )
    }
}