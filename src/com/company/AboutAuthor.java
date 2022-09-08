package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutAuthor {
    JFrame frame = new JFrame();
    JLabel MyPhoto = new JLabel();
    JLabel MyInfo1 = new JLabel("         Лесько Олексій Віталійович         ");
    JLabel MyInfo2 = new JLabel("Вінницький Національно Технічний Університет");
    JLabel MyInfo3 = new JLabel("                   ФІТКІ                    ");
    JLabel MyInfo4 = new JLabel("               Кібербезпека                 ");
    JLabel MyInfo5 = new JLabel("                  2 курс                    ");
    ImageIcon image = new ImageIcon("author.png");

    private JButton button = new JButton("Закрити");

    AboutAuthor(){
        frame.setTitle("AboutAuthor");
        frame.setBounds(100,100,500,230);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        //frame.setLayout(null);
        MyPhoto.setBounds(0,0,160,160);
        MyPhoto.setIcon(image);

        MyInfo1.setBounds(210,20,250,20);
        MyInfo2.setBounds(180,40,300,20);
        MyInfo3.setBounds(245,60,250,20);
        MyInfo4.setBounds(235,80,250,20);
        MyInfo5.setBounds(247,100,250,20);

        frame.add(MyPhoto);
        frame.add(MyInfo1);
        frame.add(MyInfo2);
        frame.add(MyInfo3);
        frame.add(MyInfo4);
        frame.add(MyInfo5);

        button.setBounds(100,100,100,100);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {frame.dispose();}
        });
        frame.add(button, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
