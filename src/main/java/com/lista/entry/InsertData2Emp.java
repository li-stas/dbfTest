package com.lista.entry;

import com.lista.view.screens.doSrcIOException;

import java.sql.*;

public class InsertData2Emp {
    public void eval(Connection conn) {
        String sql;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            sql = String.format(
                    "SELECT EmpNo"
                            + ""
                            + " FROM emp e"
                            + " WHERE e.EmpNo = %d", 9000);

            //System.out.printf("sql = %s \n",sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                sql = "INSERT INTO emp VALUES (9000,'DAVID','SALESMAN',7698,TO_DATE('1991-09-29','YYYY-MM-DD'), 1550,1700,30)";
                stmt.executeUpdate(sql);

                InsertRow(conn);

            } else {
                System.out.println("\n!!!!! EmpNo = 9000 уже добавлен !!!!!");
            }
        } catch (SQLException e) {
            new doSrcIOException().Screen();
            //e.printStackTrace();
        }
    }
    private void InsertRow(Connection conn) {
        try {
            PreparedStatement prepareStatement = conn.prepareStatement("INSERT INTO emp  VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            prepareStatement.setInt(1, 9001);
            prepareStatement.setString(2, "OLGA");
            prepareStatement.setString(3, "SALESMAN");
            prepareStatement.setInt(4, 7698);
            prepareStatement.setDate(5, java.sql.Date.valueOf("2013-09-04"));
            prepareStatement.setInt(6, 1999);
            prepareStatement.setNull(7, java.sql.Types.INTEGER);
            prepareStatement.setInt(8, 30);
            prepareStatement.execute();
        } catch (SQLException e) {
            System.out.println("IT DOES NOT WORK");
        }
    }
}

