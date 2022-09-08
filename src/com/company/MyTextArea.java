package com.company;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyTextArea extends JTextArea {
    public MyTextArea(int rows, int columns){
        setRows(rows);
        setColumns(columns);
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!isLetter(ch)) e.consume();
            }
        });
    }
    private boolean isLetter(char ch){
        return ch >= 'a' && ch <= 'z';
    }
}
