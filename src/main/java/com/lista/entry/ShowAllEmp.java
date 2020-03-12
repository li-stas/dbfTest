package com.lista.entry;

import java.sql.*;

public class ShowAllEmp {
    public void eval(Connection conn)  {
        //Создает объект Statement для отправки операторов SQL в базу данных.
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT EmpNo, EName, d.DName, Sal,"
                    + " (select grade FROM salgrade WHERE e.sal >= minsal and e.sal <= hisal ) as ETC "
                    + " FROM emp e, dept d "
                    + " WHERE e.DeptNo = d.DeptNo";

            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();

            int cols = md.getColumnCount();
            for (int i = 0; i < cols; i++) {
                String name = md.getColumnLabel(i + 1);
                print(name + "\t");
            }
            println("");
            while (rs.next()) {
                for (int i = 0; i < cols; i++) {
                    String value = rs.getString(i + 1);
                    print(value + "\t");
                }
                println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    private static void print(String cOut) {
        System.out.print(cOut);
    }
    private static void println(String cOut) {
        System.out.println(cOut);
    }
}
