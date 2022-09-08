package com.company;

import javax.swing.*;
import java.awt.*;

public class AboutCoursework {
    JFrame frame = new JFrame();

    AboutCoursework(){
        JLabel TaskImage = new JLabel();
        ImageIcon image = new ImageIcon("123.png");
        TaskImage.setBounds(0,0,740,420);
        TaskImage.setIcon(image);

        frame.setTitle("AboutCoursework");
        frame.setBounds(100,100,750,460);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(TaskImage);
        frame.pack();
    }
}
