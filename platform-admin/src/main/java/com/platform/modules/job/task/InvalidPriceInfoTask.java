package com.platform.modules.job.task;

import com.platform.modules.price.service.PriceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 报价单失效处理
 * @author Mr.panbb
 * @date 2020-03-27
 */
@Slf4j
@Component("invalidPriceInfoTask")
public class InvalidPriceInfoTask {

    @Autowired
    private PriceInfoService priceInfoService;

    public void toInvalid() {
        priceInfoService.toInvalid();
    }

}
