package org.example.billservice.demos.web;


import org.example.billservice.demos.domain.BMaterialMeata;
import org.example.billservice.demos.mapper.BMaterialInfoMapper;
import org.example.billservice.demos.mapper.BMaterialMeataMapper;
import org.example.domain.BMaterialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/material")
public class CaffeineController {

    @Autowired
    private BMaterialInfoMapper bMaterialInfoMapper;

    @Autowired
    private BMaterialMeataMapper bMaterialMeataMapper;
    @GetMapping("/cache/{materialCode}")
//    @Cacheable(value = "material",key = "#materialCode")
    public BMaterialInfo MaterialInfo(@PathVariable String materialCode) throws InterruptedException {
        return getMaterialInfo(materialCode);
    }
    @PostMapping
    public int addMaterial(@RequestBody BMaterialInfo bMaterialInfo) throws InterruptedException {
        return bMaterialInfoMapper.insertBMaterialInfo(Arrays.asList(bMaterialInfo));
    }
    @GetMapping("/meta")
    public List<BMaterialMeata> listMaterialMeata() throws InterruptedException {
        return bMaterialMeataMapper.listMaterialMeta();
    }
    @PostMapping("/meta")
    public int listMaterialMeata(@RequestBody BMaterialMeata bMaterialMeata) throws InterruptedException {
        return bMaterialMeataMapper.insertMaterialMeta(Arrays.asList(bMaterialMeata));
    }
    @GetMapping
//    @Cacheable(value = "material",key = "#materialCode")
    public List<BMaterialInfo> listMaterialInfo() throws InterruptedException {
        return bMaterialInfoMapper.listBMaterialInfo();
    }


    public BMaterialInfo getMaterialInfo(String materialCode) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(2);
//        BMaterialInfo materialInfo = new BMaterialInfo();
//        materialInfo.setMaterialCode(materialCode);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        materialInfo.setMaterialName(localDateTime.toString());

        return bMaterialInfoMapper.getMaterialInfo(materialCode);
    }

    @GetMapping("/updateMaterial/{materialCode}")
    @CachePut(value = "material",key = "#materialCode")
    public BMaterialInfo updateMaterial(@PathVariable String materialCode) throws InterruptedException {
        return getMaterialInfo(materialCode);
    }


}
