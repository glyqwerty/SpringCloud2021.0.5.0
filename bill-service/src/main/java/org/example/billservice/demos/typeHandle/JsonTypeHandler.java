package org.example.billservice.demos.typeHandle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.example.domain.enumd.BillStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
@MappedTypes({Map.class})
public class JsonTypeHandler extends BaseTypeHandler<Map<String,Object>> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JsonTypeHandler.class);

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Map<String, Object> stringObjectMap, JdbcType jdbcType) throws SQLException {
        log.info("1");
        try {
            String json = mapper.writeValueAsString(stringObjectMap);
            preparedStatement.setString(i,json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Map转换JSON失败",e);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        log.info("2");
        String json = resultSet.getString(columnName);
        return  parseJson(json);
    }

    private Map<String, Object> parseJson(String json)   {

        if(json==null){
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(json, new TypeReference<Map < String, Object >>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON转Map失败",e);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, int idx) throws SQLException {
        log.info("3");
        String json = resultSet.getString(idx);
        return parseJson(json);
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement callableStatement, int idx) throws SQLException {
        log.info("4");
        String string = callableStatement.getString(idx);
        return parseJson(string);
    }
}
