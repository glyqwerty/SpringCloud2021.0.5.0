//package org.example.warehouseservice.scheduleTask;
//
//import org.example.warehouseservice.domain.GroupKey;
//import org.example.warehouseservice.domain.BMaterialStockTask;
//import org.example.warehouseservice.mapper.BMaterialStockMapper;
//import org.example.warehouseservice.mapper.BMaterialStockTaskMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class MyBusinessTask {
//
//    @Autowired
//    BMaterialStockTaskMapper bMaterialStockTaskMapper;
//    @Autowired
//    BMaterialStockMapper  bMaterialStockMapper;
//    // 每分钟执行一次
//    @Scheduled(cron = "0 1 * * * ?")
//    public void stockTask() {
//        List<BMaterialStockTask> bMaterialStockTasks =
//                bMaterialStockTaskMapper.listMaterialStockTask();
//        Map<GroupKey, Integer> result = bMaterialStockTasks.stream()
//                .collect(Collectors.groupingBy(
//                        item -> new GroupKey(item.getPositionCode(), item.getMaterialCode()),
//                        Collectors.reducing(0,, Integer::sum)
//                ));
//        Set<Map.Entry<GroupKey, Integer>> entries = result.entrySet();
//
//        for (Map.Entry<GroupKey, Integer> groupKeyIntegerEntry : entries) {
//
//        }
//        bMaterialStockMapper.updateBMaterialStock();
//
//    }
//}