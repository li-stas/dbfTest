package com.lista.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Hashtable;



public class OracleDAOConnect implements DAOConnect {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static OracleDAOConnect oracleDAOConnect;

    public OracleDAOConnect() {
    }

    public static OracleDAOConnect getInstance() {
        if (oracleDAOConnect != null) {
            return oracleDAOConnect;
        } else {
            oracleDAOConnect = new OracleDAOConnect();
            return oracleDAOConnect;
        }
    }

    @Override
    public void connect(){
    }

    public void connect(String connectionUrl, String driverClass, String userName, String passWord) {

        if (connectionUrl.isEmpty()) {
            connectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            driverClass = "oracle.jdbc.OracleDriver";
            userName = "STUDENT";
            passWord = "admin";
        }


        try {
            Class.forName(driverClass);
            DriverManager.setLogStream(System.out);
            // Попытка соединения с драйвером. Каждый из
            // зарегистрированных драйверов будет загружаться, пока
            // не будет найден тот, который сможет обработать этот URL
            connection = DriverManager.getConnection(connectionUrl, userName, passWord);
            // Если соединиться не удалось, то произойдет exception (исключительная ситуация).
            // Получить DatabaseMetaData объект и показать информацию о соединении
            DatabaseMetaData dma = connection.getMetaData();
            // Печать сообщения об успешном соединении
            System.out.println("\njdbc");
            System.out.println("Connected to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Version " + dma.getDriverVersion());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {
        // Закрыть соединение
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectAllStudent() {

    }
/*
* https://docs.oracle.com/cd/A97329_03/web.902/a95879/ds.htm#1005742
 */
    public void connectWebLogic(String sourceName) {

        Context ic = null;
        try {
            String sp = "weblogic.jndi.WLInitialContextFactory";
            String file = "t3://localhost:7001";
            String dataSourceName = sourceName;//"JNDINamedbStudentWL";

            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, sp);
            env.put(Context.PROVIDER_URL, file);
            ic = new InitialContext(env);

            DataSource ds = (DataSource) ic.lookup(dataSourceName);

            connection =  ds.getConnection();

            DatabaseMetaData dma = connection.getMetaData();
            // Печать сообщения об успешном соединении
            System.out.println("\nWebLogic");
            System.out.println("Connected to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Version " + dma.getDriverVersion());

        } catch (SQLException | NamingException e) {
            System.out.println("\n" + e.getMessage());
                //e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
