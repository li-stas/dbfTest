package com.lista;

import com.lista.dao.OracleDAOConnect;
import com.lista.entry.DeleteOneEmp;
import com.lista.entry.InsertData2Emp;
import com.lista.entry.ShowAllEmp;
import com.lista.entry.ShowOneEmp;
import com.lista.service.ConnectDbStudent;
import com.lista.view.lib.menuto.Menu;
import com.lista.view.lib.menuto.MenuEntry;

import java.sql.Connection;

public class AppJdbc {
    public static void main( String[] args ) {
        int nRet;
        OracleDAOConnect dbCon = null;
        Connection conn = null;

        while (true) {

            if (conn == null) {
                Menu menu = new Menu(1);
                menu.addEntry(new MenuEntry("1 - Подключится к базе данных", true) { public void run() {} });
                //System.out.print("Сделайет выбор");
                nRet = menu.run();
                switch (nRet){
                    case 0:
                        System.exit(0);
                        break;
                    case 1: // добавить день
                        dbCon = new ConnectDbStudent().eval();
                        conn = dbCon.getConnection();
                        break;
                }
            } else {
                //System.exit(0);
                final Connection finalConn = conn;
                Menu menu = new Menu(1);
                menu.addEntry(new MenuEntry("1 - Данные всех работников", false) { public void run() {new ShowAllEmp().eval(finalConn);} });
                menu.addEntry(new MenuEntry("2 - Данные об одном работнике", false) { public void run() {new ShowOneEmp().eval(finalConn);} });
                menu.addEntry(new MenuEntry("3 - Добавить работника", false) { public void run() {new InsertData2Emp().eval(finalConn);} });
                menu.addEntry(new MenuEntry("4 - Удалить работника", false) { public void run() {new DeleteOneEmp().eval(finalConn); } });
                System.out.print("\n\n");
                nRet = menu.run();
                if (nRet ==0 ) {
                    dbCon.disconnect();
                    System.exit(0);
                }
            }
        }
    }
}
