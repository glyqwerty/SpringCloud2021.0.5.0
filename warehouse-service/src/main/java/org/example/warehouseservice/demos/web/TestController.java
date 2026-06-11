//package org.example.warehouseservice.demos.web;
//
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.example.service.WarehouseService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
//@RestController
//public class TestController {
//
//     @DubboReference
//     private WarehouseService warehouseService;
//
//    @GetMapping("/test")
//    public String testDubbo() {
//        // 2. 调用远程方法
//        // String result = billService.getBillInfo(1L);
//
//        return "如果代码能运行到这里且不报错，说明订阅成功！";
//    }
//}