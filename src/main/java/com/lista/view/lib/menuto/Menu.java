package com.lista.view.lib.menuto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * контейнер хранения элементов меню
 * http://siargei-stepanov.blogspot.com/2015/02/java.html
 * и запуск выбора п. меню
 * nTypeMenu - 0 - горизонтальное меню, 1- вертикальное меню
 * isExit - что делать после успешного выбора п.меню,
 *      true - выход и возврат номера п. меню
 *      fasls - меню зацикливается и если есть методы для п.меню, то выполнятся.
 *      п.0 - выполняет выход (добавляется автоматически последним)
 */
public class Menu  {
    private ArrayList<MenuEntry> entries = new ArrayList<>();
    private boolean isExit = false;
    private int nTypeMenu;

    public Menu() {
        this.nTypeMenu = 0;
    }

    public Menu(int nTypeMenu) {
        this.nTypeMenu = nTypeMenu;
    }

    public void addEntry(MenuEntry oMenuEntry) {
        entries.add(oMenuEntry);
    }

    public int run() {
        int choice = 0;
        int nMaxNumElem = entries.size();
        // Добавляем пункт меню Exit
        entries.add(new MenuEntry("0 - Выход", true) {
            @Override
            public void run() {
                //isExit = true;
            }
        });
        // Бесконечный цикл, пока не нажали кнопку выход
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!isExit) {
            printMenu();
            try {
                String line = reader.readLine();
                choice = Integer.parseInt(line);

                if (choice < 0 || choice > nMaxNumElem) {
                    System.out.println((char)27 + "[31mЦифровое значение в не диапазовна"
                            + " 1-" + nMaxNumElem + ", 0" + (char) 27 + "[37m");
                    continue;
                }

                if (choice == 0) {
                    choice = nMaxNumElem + 1;
                }

                // Выбираем нажатый пункт меню и выполняем его код
                MenuEntry entry =  entries.get(choice - 1);
                entry.run();
                isExit = entry.islExit();

            } catch (IOException e) {
                System.out.println((char)27 + "[31m" + "Ошибка ввода вывода"+ (char) 27 + "[37m");
            }
            catch (NumberFormatException e) {
                System.out.println((char)27 + "[31m" + "Только цифры:"
                        + " 1-" + nMaxNumElem + ", 0" + (char) 27 + "[37m");
            }
        }

        return choice - 1 == nMaxNumElem  ? 0 : choice;
    }

    private void printMenu() {
        System.out.print((char)27 + "[37m");
        for (int i = 0; i < entries.size() - 1; i++) {
            MenuEntry entry =  entries.get(i);
            if (nTypeMenu == 0) {
                System.out.print(entry.getTitle() + ", ");
            } else {
                System.out.println(entry.getTitle());
            }
        }
        MenuEntry entry = entries.get(entries.size() - 1);
        if (nTypeMenu == 0) {
            System.out.print(entry.getTitle() + ": ");
        } else {
            System.out.println(entry.getTitle());
            System.out.println("");
            System.out.print("Сделайте выбор" + ": ");
        }
    }


}