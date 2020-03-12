package com.lista.entry;

import com.lista.view.lib.getread.readNumSayGetValid;
import com.lista.view.screens.doSrcIOException;


import java.sql.*;

public class ShowOneEmp {

    public void eval(Connection conn) {
        int nEmpNo;

        nEmpNo = new readNumSayGetValid().getread("Идентификатор работника: ", 1, 9999);

        //Создает объект Statement для отправки операторов SQL в базу данных.
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format(
                    "SELECT EmpNo, EName, d.DName, Sal,"
                    + " (select grade FROM salgrade WHERE e.sal >= minsal and e.sal <= hisal ) as ETC "
                    + " FROM emp e, dept d "
                    + " WHERE e.DeptNo = d.DeptNo and  e.EmpNo = %d", nEmpNo);

            //System.out.printf("sql = %s \n",sql);

            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();

            if (!rs.next() ) {
                System.out.println("\n!!!!! No data !!!!!");
            } else {
                int cols = md.getColumnCount();
                for (int i = 0; i < cols; i++) {
                    String name = md.getColumnLabel(i + 1);
                    print(name + "\t");
                }
                println("");

                do {
                    for (int i = 0; i < cols; i++) {
                        String value = rs.getString(i + 1);
                        print(value + "\t");
                    }
                    println("");
                } while (rs.next());
            }
        } catch (SQLException e) {
            new doSrcIOException().Screen();
            //e.printStackTrace();
        }


    }

    private static void print(String cOut) {
        System.out.print(cOut);
    }

    private static void println(String cOut) {
        System.out.println(cOut);
    }


}
