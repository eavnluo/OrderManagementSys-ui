package com.platform.modules.web.base;

import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.web.ApiBaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * web商品接口
 *
 * @author 朱黎明
 * @date 2020-03-24
 */
@Slf4j
@RestController
@RequestMapping("/web/goods")
@Api(tags = "BaseGoodsInfoController|web商品接口")
public class BaseGoodsInfoController extends ApiBaseController {
	@Autowired
	private BaseGoodsInfoService baseGoodsInfoService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseGoodsInfoEntity> list = baseGoodsInfoService.queryAll(params);
		return RestResponse.success().put("list", list);
	}
}
