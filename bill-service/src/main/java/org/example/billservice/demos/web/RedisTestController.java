package org.example.billservice.demos.web;

import org.example.billservice.demos.reids.RedisTimeSerialService;
import org.example.billservice.demos.reids.RedissonTopKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisTestController {
    @Autowired
    private RedissonTopKService redissonTopKService;
    @Autowired
    private RedisTimeSerialService redisTimeSerialService;

    @GetMapping("/redis")
    public String test() {
        redissonTopKService.addItem("z","x","s");
        return "Redis Ok ";
    }


    @GetMapping("/getTopKList")
    public String getTopKList() {
        List<String> topKList = redissonTopKService.getTopKList();
        return String.join(", ", topKList);
    }


    @GetMapping("/isItemInTopK/{value}")
    public Boolean isItemInTopK(@PathVariable String value) {

        return redissonTopKService.isItemInTopK(value);
    }


    @GetMapping("/getItemCount/{value}")
    public Long getItemCount(@PathVariable String value) {

        return redissonTopKService.getItemCount(value);
    }
    @GetMapping("/incrementWithLimit/{key}")
    public Long incrementWithLimit(@PathVariable String key) {

        return redissonTopKService.incrementWithLimit(key,1L);
    }
    @GetMapping("/timeSerial/{key}")
    public String TimeSerial(@PathVariable String key) {
        redisTimeSerialService.add(key,1L);
        return "1L";
    }


}
