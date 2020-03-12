package com.lista.view.lib.getread;

import com.lista.view.screens.doSrcIOException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class readNumSayGetValid {
    final Logger log = Logger.getLogger(readNumSayGetValid.class.getName());
    /**
     * GET|READ - числовых значений с проверкой вхождения в диапазон
     * @param s1
     * @param from
     * @param to
     * @return
     */
    public int getread(String s1, int from, int to) {
        int[] paramInt = new int[1];
        System.out.print(s1);
        readNum(paramInt, from, to);
        return paramInt[0];
    }
    /**
     * ввод чисел с проверкой диапзона
     *
     * @param nNumParam
     * @param from
     * @param to
     */
    private void readNum(int[] nNumParam, int from, int to) {
        int nNum;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String line = reader.readLine();
                nNum = Integer.parseInt(line);
                if (nNum >= from && nNum <= to) {
                    nNumParam[0] = nNum;
                    break;
                } else {
                    System.out.println((char) 27 + "[31mДиапазон: "
                            + " " + from + " - " + to + (char) 27 + "[37m");
                }
            } catch (IOException e) {
                log.error("IOException", e);
                new doSrcIOException().Screen();
            } catch (NumberFormatException e) {
                System.out.println((char)27 + "[31m" + "Только цифры:"
                        + " " + from + " - " + to + (char)27 + "[37m");
            }
        }
    }
}

