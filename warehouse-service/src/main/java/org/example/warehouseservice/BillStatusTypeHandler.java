package org.example.warehouseservice;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.example.domain.enumd.BillStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillStatusTypeHandler extends BaseTypeHandler<BillStatus> {
    private static final Logger log = LoggerFactory.getLogger(BillStatusTypeHandler.class);

    // 执行之前，将Java类型转换为对应的jdbc类型，用于赋值sql中参数
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BillStatus billStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,billStatus.getCode());
    }
    // 根据列名从resultSet中获取，将JDBC类型转换为Java类型
    @Override
    public BillStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return resultSet.wasNull()?null:BillStatus.getByCode(code);
    }
    // 根据下标从resultSet中获取，将JDBC类型转换为Java类型
    @Override
    public BillStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return resultSet.wasNull()?null:BillStatus.getByCode(code);
    }
    // 用于在执行完存储过程后，将JDBC类型转换为Java类型
    @Override
    public BillStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return callableStatement.wasNull()?null:BillStatus.getByCode(code);
    }
}
