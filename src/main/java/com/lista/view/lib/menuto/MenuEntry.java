package com.lista.view.lib.menuto;

/**
 * элементы меню
 * http://siargei-stepanov.blogspot.com/2015/02/java.html
 */
public abstract class MenuEntry {
    private String title;
    private boolean lExit;

    public MenuEntry(String title) {
        this.title = title;
    }
    public MenuEntry(String title, boolean lExit) {
        this.title = title;
        this.lExit = lExit;
    }

    public String getTitle() {
        return title;
    }

    public boolean islExit() {
        return lExit;
    }

    public abstract void run();
}
