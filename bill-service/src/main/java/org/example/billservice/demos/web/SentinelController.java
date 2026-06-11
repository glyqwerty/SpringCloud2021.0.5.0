//package org.example.billservice.demos.web;
//
//
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.example.billservice.tccconfig.BillTccConfig;
//import org.example.service.MaterialStockService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SentinelController {
//    Logger logger = LoggerFactory.getLogger(SentinelController.class);
//
//
//    @DubboReference
//    private MaterialStockService materialStockService;
//
//    @GetMapping("/sentienl/{id}")
//    @SentinelResource(value = "sentinelWebTest", blockHandler="handleBlock")
//    public String test(@PathVariable String id) {
//        logger.info("Success");
//        materialStockService.createMaterialStock();
//        return "hello";
//    }
//    /**
//     * Sentinel 限流/降级后的处理方法
//     * 注意：参数必须与原方法一致，最后多一个 必须是BlockException 参数
//     */
//    public String handleBlock(String id, BlockException ex){
//        logger.warn("订单服务被限流或降级: {}", ex.getMessage());
//        return "系统繁忙";
//    }
//
//
//    @GetMapping("/degradeRule")
//    @SentinelResource(value = "degradeRuleTest", blockHandler="handldeDegradeRuleBlock")
//    public String degradeRule() {
//        logger.info("degradeRule Success");
//        throw new RuntimeException("E");
//    }
//
//    public String handldeDegradeRuleBlock(  BlockException ex){
//        logger.warn("订单服务被限流或降级: {}", ex.getMessage());
//        return "系统繁忙";
//    }
//
//
//}
