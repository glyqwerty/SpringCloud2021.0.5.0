package org.example.billservice.demos.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.billservice.demos.domain.BMaterialMeata;
import org.example.domain.BMaterialInfo;

import java.util.List;

public interface BMaterialInfoMapper {
    BMaterialInfo getMaterialInfo(String materialCode);
    List<BMaterialInfo> listBMaterialInfo( );

    int insertBMaterialInfo(@Param("list") List<BMaterialInfo> list);
}
