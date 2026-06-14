package kot.gin.hentai_dashboard.server.modules.props.enums

/**
 * @author hjg71
 * @since 2026/6/14 11:38
 */
enum class PropConst(val key: String, val description: String, val defaultValue: String) {
    WORK_SPACE("WORK_SPACE","工作目录, 所有文件都存储在这个目录下","d:/workspace"),
    PROXY_URL("PROXY_URL","代理地址","127.0.0.1:10809"),
    EX_HENTAI_COOKIE("EX_HENTAI_COOKIE","ExHentai使用的Cookie",""),
    PIXIV_COOKIE("PIXIV_COOKIE","Pixiv使用的Cookie",""),
    PIXIV_TOKEN("PIXIV_TOKEN","Pixiv使用的Token(自动更新)",""),
    IWARA_USERNAME("IWARA_USERNAME","Iwara使用的用户名",""),
    IWARA_PASSWORD("IWARA_PASSWORD","Iwara使用的密码",""),
    IWARA_ACCESS_TOKEN("IWARA_ACCESS_TOKEN","Iwara使用的AccessToken(自动更新)",""),
    IWARA_REFRESH_TOKEN("IWARA_REFRESH_TOKEN","Iwara使用的RefreshToken(自动更新)",""),
    IWARA_LAST_CREATED_AT("IWARA_LAST_CREATED_AT","Iwara拉取的最后创建时间(自动更新)",""),
}