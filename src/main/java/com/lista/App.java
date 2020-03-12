package com.lista;

import java.sql.*;

/**
 * Hello world!
 * http://bigfatball.blogspot.com/2016/03/how-to-enable-jdbc-odbc-bridge-for-jdk-8.html
 * 1) First make sure if your going for 64 bit environment choose Java JRE 7 or SDK 7 64 bit version.
 * 2) Once you have Separated the files in c:\sun folder run the command from c:\jar -cvf jdbc.jar sun.
 * 3) Once your jdbc.jar is created in my case I had to put it in
 * C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext folder,
 * instructions said to put it in lib which gave me the error class not found
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            /*String connString="jdbc:odbc:Driver={Microsoft dBASE Driver (*.dbf)}"+";DefaultDir=C:\\Users\\Pro\\4del\\sbarost.dbf";//DeafultDir indicates the location of the db*/
            String connString="jdbc:odbc:Driver={Microsoft Visual FoxPro Driver};SourceType=DBF;SourceDB=C:\\Users\\Pro\\4del;Exclusive=No;NULL=NO;Collate=Machine;BACKGROUNDFETCH=NO;DELETED=NO;";
            Connection connection = DriverManager.getConnection(connString);
            String sql="SELECT * FROM sbarost";// usual sql query where kg=341
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();

            int cols = md.getColumnCount();
            for (int i = 0; i < cols; i++) {
                String name = md.getColumnLabel(i + 1);
                print(name + "\t");
            }
//java.sql.SQLException: [Microsoft][????????? ????????? ODBC] ???????? ?????? ?? ?????? ? ?? ?????? ???????, ???????????? ?? ?????????
            println("");
            while (rs.next()) {
                for (int i = 0; i < cols; i++) {
                    String value = rs.getString(i + 1);
                    print(value + "\t");
                }
                println("");
            }


/*
            while(resultSet.next())
            {
                System.out.println();
            }
            System.out.println();
*/
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
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
