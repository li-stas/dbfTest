package com.lista.service;

import com.lista.config.GetDataSource;
import com.lista.dao.OracleDAOConnect;

import java.sql.Connection;

public class ConnectDbStudent {
    public OracleDAOConnect eval() {
        String connectionUrl,  driverClass,  userName,  passWord;
        Connection conn;

        connectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        driverClass = "oracle.jdbc.OracleDriver";
        userName = "STUDENT";
        passWord = "admin";

        GetDataSource dtSrc = new GetDataSource("DataConnect.xml", "dataSource.dtd");
        System.out.println(dtSrc.toString());

        OracleDAOConnect dbCon = new OracleDAOConnect();
        // чз ВебЛоджик, если поднят
        dbCon.connectWebLogic(dtSrc.getSourceName());
        conn = dbCon.getConnection();

        if (conn == null) {
            connectionUrl = dtSrc.getConnectionUrl();
            driverClass = dtSrc.getDriverClass();
            userName = dtSrc.getUserName();
            passWord = dtSrc.getPassWord();

            dbCon.connect(connectionUrl,  driverClass,  userName,  passWord);

        }
        return dbCon;
    }

}
