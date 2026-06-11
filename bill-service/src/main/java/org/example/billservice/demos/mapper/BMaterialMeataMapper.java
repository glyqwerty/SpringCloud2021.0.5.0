package org.example.billservice.demos.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.billservice.demos.domain.BMaterialMeata;

import java.util.List;

public interface BMaterialMeataMapper {

    List<BMaterialMeata> listMaterialMeta();
    int insertMaterialMeta(@Param("list") List<BMaterialMeata> bMaterialMeata);
}
