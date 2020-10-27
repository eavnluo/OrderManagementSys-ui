package com.platform.modules.web;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseIndustryTypeEntity;
import com.platform.modules.base.service.BaseIndustryTypeService;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.requesting.entity.RequestingResearchEntity;
import com.platform.modules.requesting.service.RequestingResearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 需求调研接口
 * @author Mr.panbb
 * @date 2020-03-18
 */
@Slf4j
@RestController
@RequestMapping("/web/request")
@Api(tags = "RequestingResearchController|需求调研接口")
public class RequestingResearchController extends ApiBaseController {
    @Autowired
    private RequestingResearchService requestingResearchService;
    @Autowired
    private BaseIndustryTypeService baseIndustryTypeService;
    @Autowired
    private RegisterUserService registerUserService;

    /**
     * 保存需求调研
     * @param requestingResearch 调研参数
     * @return RestResponse
     */
    @IgnoreAuth
    @RequestMapping("/save")
    @ApiOperation(value = "保存需求调研", notes = "保存需求调研")
    public RestResponse save(@RequestBody RequestingResearchEntity requestingResearch) {
        // 保存需求调研
        requestingResearchService.add(requestingResearch);
        // 转化成注册信息
        registerUserService.addFromRequesting(requestingResearch);
        return RestResponse.success();
    }

    /**
     * 查询所有的行业类型
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @IgnoreAuth
    @RequestMapping("/findIndustryList")
    @ApiOperation(value = "查询所有的行业类型", notes = "查询所有的行业类型")
    public RestResponse findIndustryList(@RequestParam Map<String, Object> params) {
        List<BaseIndustryTypeEntity> list = baseIndustryTypeService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

}
