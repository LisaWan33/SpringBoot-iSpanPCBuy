package com.ispan.pcbuy.rowmapper;

import com.ispan.pcbuy.model.OrderInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderInfoRowMapper implements RowMapper<OrderInfo> {

    @Override
    public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderInfoId(resultSet.getInt("order_info_id"));
        orderInfo.setOrderId(resultSet.getInt("order_id"));
        orderInfo.setName(resultSet.getString("name"));
        orderInfo.setTel(resultSet.getString("tel"));
        orderInfo.setAddr(resultSet.getString("addr"));
        orderInfo.setPayment(resultSet.getString("payment"));
        orderInfo.setAssemble(resultSet.getString("assemble"));
        return orderInfo;
    }
}
