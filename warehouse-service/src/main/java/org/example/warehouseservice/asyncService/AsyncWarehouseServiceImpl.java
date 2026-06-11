//package org.example.warehouseservice.asyncService;
//
//
//import org.apache.dubbo.config.annotation.DubboService;
//import org.example.service.AsyncWarehouseService;
//import org.example.warehouseservice.domain.BWarehouseInfo;
//import org.example.warehouseservice.mapper.BWarehouseInfoMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.TimeUnit;
//
//@DubboService
////        (group = "group1",version = "1.0")
//public class AsyncWarehouseServiceImpl implements AsyncWarehouseService {
//
//    @Override
//    public CompletableFuture<String> createWarehouse(){
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            throw new RuntimeException();
////            return "异步调用成功";
//        });
//
//    }
//
//}
