package com.platform.modules.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import com.platform.modules.website.entity.WebsiteNewsEntity;
import com.platform.modules.website.service.WebsiteNewsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * WebsiteController
 *
 * @author zlm
 * @date 2020-04-16
 */
@RestController
@RequestMapping("/web/website")
@Api(tags = "WebsiteController | 网站维护接口")
public class WebsiteController extends ApiBaseController {

    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;
    @Autowired
    private WebsiteNewsService websiteNewsService;

    /**
     * 根据belongTo获取首页图片
     *
     * @param params
     * @return
     */
    @IgnoreAuth
    @RequestMapping("/getIndexImage")
    public RestResponse getIndexImage(@RequestParam Map<String, Object> params) {

        if (StringUtils.isBlank(params.get("belongTo").toString()) || !params.get("belongTo").toString().contains("WEBSITE")) {
            return RestResponse.success().put("list", null);
        }
        List<SysFileAttachmentEntity> list = sysFileAttachmentService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 获取网站新闻列表
     *
     * @param params
     * @return
     */
    @IgnoreAuth
    @RequestMapping("/getWebSiteNews")
    public RestResponse getWebSiteNews(@RequestParam Map<String, Object> params) {

        Page page = websiteNewsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @IgnoreAuth
    @RequestMapping("/newsInfo")
    public RestResponse newsInfo(@RequestParam String id) {

        WebsiteNewsEntity info = websiteNewsService.getNewsInfo(id);

        return RestResponse.success().put("info", info);
    }


}
