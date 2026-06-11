//package org.example.billservice.demos.web;
//
//import io.seata.rm.tcc.api.BusinessActionContext;
//import io.seata.spring.annotation.GlobalTransactional;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.example.billservice.demos.domain.InBillInfo;
//import org.example.billservice.demos.mapper.InBillMapper;
//import org.example.billservice.tccconfig.BillTccConfig;
//import org.example.domain.BMaterialStock;
//import org.example.service.MaterialStockService;
//import org.example.service.WarehouseService;
//import org.example.domain.BWarehouseInfo;
//import org.example.service.AsyncWarehouseService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//import java.util.List;
//@RestController
//public class TestController {
//
//
//    private static final Logger log = LoggerFactory.getLogger(TestController.class);
//    @DubboReference
//    private WarehouseService warehouseService;
//
//    @DubboReference
//    private AsyncWarehouseService asyncWarehouseService;
//    @DubboReference
//    private MaterialStockService materialStockService;
//
//    @Autowired
//    BillTccConfig billTccConfig;
//    @Autowired
//       private InBillMapper inBillMapper;
//
//    @GlobalTransactional
//    @GetMapping("/warehouse")
//    public String getWarehouse() {
//        InBillInfo inBillInfo = new InBillInfo();
//        inBillInfo.setBillCode("T");
//        inBillInfo.setBillType("T");
//        inBillMapper.inserInBillInfo(inBillInfo);
//        warehouseService.createMaterialStock(new BMaterialStock());
//        return "OKP";
//    }
//    @GetMapping("/warehouseList")
//    public String listWarehouseInfo() {
//
//        List<BWarehouseInfo> bWarehouseInfoList = warehouseService.listBWarehouseInfo(new BWarehouseInfo());
//        for(BWarehouseInfo bWarehouseInfo: bWarehouseInfoList){
//            log.info(bWarehouseInfo.getWarehouseCode());
//        }
//        return "OKP";
//    }
//
//    @GetMapping("/materialStockService")
////    @GlobalTransactional
//    public int materialStockService() {
//        return materialStockService.createMaterialStock();
//    }
//
//    @GetMapping("/asyncwarehouse")
//    @GlobalTransactional
//    public String getAsyncwarehouse() throws ExecutionException, InterruptedException {
//        InBillInfo inBillInfo = new InBillInfo();
//        inBillInfo.setBillCode("T");
//        inBillInfo.setBillType("T");
//        inBillMapper.inserInBillInfo(inBillInfo);
//        CompletableFuture<String> warehouse = asyncWarehouseService.createWarehouse();
////        warehouse.get();
//        return "OK";
//
//    }
//
//
//    @GetMapping("/tccTest")
//    @GlobalTransactional
//    public String tccTest(){
//        BusinessActionContext businessActionContext = new BusinessActionContext();
////        businessActionContext.setXid("XXX");
////        businessActionContext.setBranchId(1);
//        billTccConfig.prepare(businessActionContext,"GLy",10);
//        return "OK";
//    }
//}
