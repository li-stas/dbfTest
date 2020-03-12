package com.lista.entry;

import com.lista.view.lib.getread.readNumSayGetValid;
import com.lista.view.screens.doSrcIOException;

import java.sql.*;

public class DelOneEmp {
    public void eval(Connection conn) {
        String sql;
        int nEmpNo;
        nEmpNo = new readNumSayGetValid().getread("Удалить.Идентификатор работника: ", 9000, 9002);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            sql = String.format(
                    "SELECT EmpNo"
                            + ""
                            + " FROM emp e"
                            + " WHERE e.EmpNo = %d", nEmpNo);

            //System.out.printf("sql = %s \n",sql);

            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();

            if (!rs.next()) {
                System.out.println("\n!!!!! No data !!!!!");
            } else {
                DeleteRow(conn, String.valueOf(nEmpNo));
            }
        } catch (SQLException e) {
            new doSrcIOException().Screen();
            //e.printStackTrace();
        }

    }
    private  void DeleteRow(Connection conn, String cEmpNo) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM emp WHERE EmpNo = ?");
            st.setString(1, cEmpNo);
            st.executeUpdate();
        } catch(Exception e) {
            new doSrcIOException().Screen();
            System.out.println(e);
            //e.printStackTrace();
        }
    }
}
