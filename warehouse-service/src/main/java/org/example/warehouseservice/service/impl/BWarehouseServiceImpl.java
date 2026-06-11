//package org.example.warehouseservice.service.impl;
//
//import org.apache.dubbo.config.annotation.DubboService;
//import org.example.domain.BMaterialStock;
//import org.example.service.WarehouseService;
//import org.example.domain.BWarehouseInfo;
//import org.example.warehouseservice.mapper.BWarehouseInfoMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@DubboService
//public class BWarehouseServiceImpl implements WarehouseService {
//    @Autowired
//    private BWarehouseInfoMapper bWarehouseInfoMapper;
//
//    public List<BWarehouseInfo> listBWarehouseInfo(BWarehouseInfo bWarehouseInfo){
//        return bWarehouseInfoMapper.listBWarehouseInfo(bWarehouseInfo);
//    }
//    @Override
//    public int createMaterialStock(BMaterialStock bMaterialStock) {
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        throw new RuntimeException();
////        System.out.println("调用成功");
////        return 1;
//    }
//}
