package com.company;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.*;

import java.io.*;
import java.util.Objects;
import javax.sound.sampled.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Encryption implements ActionListener {
    JFrame EncryptionApp = new JFrame();
    private final JButton button = new JButton("Зашифрувати");
    private final JButton button2 = new JButton("Розшифрувати");
    private final JButton button3 = new JButton("Очистити");
    private final MyTextArea  input = new MyTextArea (50,50);
    private final MyTextArea  input2 = new MyTextArea (50,50);

    JMenuBar menu;
    JMenu FileMenu, AboutProgramMenu;
    JMenuItem OpenMessage,SaveMessage,OpenKey,SaveKey,ExitItem,
            AboutAuthorItem,AboutCourseworkItem,CertificateItem;

    JLabel BackgroundLabel;

    String OriginalString;

    char[] EncryptedCharArray;
    char[] DecryptionCharArray;
    String KeyString = "";
    char[] KeyCharArray;

    String EncryptedString;

    private final JFileChooser fileChooser;

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Текстовий документ (*.txt)", "txt");

    File LinkToMessage;
    File LinkToKey;

    public Encryption() {
        EncryptionApp.setTitle("EncryptionProgram");
        EncryptionApp.setBounds(100, 100, 500, 350);
        EncryptionApp.setLayout(null);
        EncryptionApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EncryptionApp.setResizable(false);
        ImageIcon image = new ImageIcon("logo.png");
        EncryptionApp.setIconImage(image.getImage());

        // Создание экземпляра JFileChooser 
        fileChooser = new JFileChooser();
        // Подключение слушателей к кнопкам
        fileChooser.setFileFilter(filter);
        
        //MenuBar
        {
            menu = new JMenuBar();
            FileMenu = new JMenu("Файл");
            AboutProgramMenu = new JMenu("Про програму");

            OpenMessage = new JMenuItem("Відкрити повідомлення");
            SaveMessage = new JMenuItem("Зберегти повідомлення");
            OpenKey = new JMenuItem("Відкрити ключ");
            SaveKey = new JMenuItem("Зберегти ключ");
            ExitItem = new JMenuItem("Вихід");

            CertificateItem = new JMenuItem("Довідка");
            AboutAuthorItem = new JMenuItem("Про автора");
            AboutCourseworkItem = new JMenuItem("Про шифрування");

            OpenMessage.addActionListener(this);
            SaveMessage.addActionListener(this);
            OpenKey.addActionListener(this);
            SaveKey.addActionListener(this);
            ExitItem.addActionListener(this);

            CertificateItem.addActionListener(this);
            AboutAuthorItem.addActionListener(this);
            AboutCourseworkItem.addActionListener(this);

            OpenMessage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)); // гарячі клавіші
            SaveMessage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
            OpenKey.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
            SaveKey.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));

            CertificateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
            AboutAuthorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
            AboutCourseworkItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));

            FileMenu.addSeparator();
            FileMenu.add(OpenMessage);
            FileMenu.add(SaveMessage);
            FileMenu.addSeparator();
            FileMenu.add(OpenKey);
            FileMenu.add(SaveKey);
            FileMenu.addSeparator();
            FileMenu.add(ExitItem);

            AboutProgramMenu.addSeparator();
            AboutProgramMenu.add(CertificateItem);
            AboutProgramMenu.addSeparator();
            AboutProgramMenu.add(AboutAuthorItem);
            AboutProgramMenu.add(AboutCourseworkItem);
            AboutProgramMenu.addSeparator();

            menu.add(FileMenu);
            menu.add(AboutProgramMenu);

            EncryptionApp.setJMenuBar(menu);
        }
        //Елементи управління головного вікна
        {
            ImageIcon BackgroundImage = new ImageIcon("background.png");
            BackgroundLabel = new JLabel();
            BackgroundLabel.setIcon(BackgroundImage);
            BackgroundLabel.setBounds(0, 0, 500, 500);

            ImageIcon ILock = new ImageIcon("lock.png");
            button.addActionListener(this);
            button.setBounds(305, 50, 150, 30);
            button.setFocusable(false);
            button.setIcon(ILock);
            EncryptionApp.add(button);

            ImageIcon IKey = new ImageIcon("key.png");
            button2.addActionListener(this);
            button2.setBounds(305, 100, 150, 30);
            button2.setFocusable(false);
            button2.setIcon(IKey);
            EncryptionApp.add(button2);

            ImageIcon IEraser = new ImageIcon("eraser.png");
            button3.addActionListener(this);
            button3.setBounds(305, 150, 150, 30);
            button3.setFocusable(false);
            button3.setIcon(IEraser);
            EncryptionApp.add(button3);

            JLabel label = new JLabel("Введіть текст, який хочете зашифрувати/розшифрувати:");
            label.setBounds(25, 15, 350, 25);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            EncryptionApp.add(label);

            input.setBounds(25, 50, 250, 75);
            input.setLineWrap(true);
            EncryptionApp.add(input);

            JLabel label2 = new JLabel("Результат шифрування/розшифрування:");
            label2.setBounds(25, 135, 250, 25);
            label2.setFont(new Font("Arial", Font.BOLD, 12));
            label2.setForeground(Color.WHITE);
            EncryptionApp.add(label2);

            input2.setBounds(25, 175, 250, 75);
            input2.setLineWrap(true);
            input2.setEditable(false);
            EncryptionApp.add(input2);
        }
        EncryptionApp.add(BackgroundLabel);
        EncryptionApp.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        BufferedReader br;
        String line;
        if(e.getSource()==OpenMessage) {
            input.setText("");
            fileChooser.setDialogTitle("Вибір повідомлення");

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(EncryptionApp);

            if (result == JFileChooser.APPROVE_OPTION) {
                LinkToMessage = fileChooser.getSelectedFile();
                if(!isFileEmpty(LinkToMessage)) {
                    try {
                        br = new BufferedReader(new FileReader(LinkToMessage));
                        while ((line = br.readLine()) != null) {
                            input.append(line);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else JOptionPane.showMessageDialog(EncryptionApp, "Файл пустий.","Помилка",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==SaveMessage) {
        fileChooser.setDialogTitle("Збереження повідомлення");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(EncryptionApp);
        if (result == JFileChooser.APPROVE_OPTION ) {
            LinkToMessage = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(LinkToMessage + ".txt");
                writer.write(input2.getText());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(EncryptionApp, "Повідомлення збережено");
        }
    }
        if(e.getSource()==OpenKey) {

            fileChooser.setDialogTitle("Вибір ключа");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(EncryptionApp);

            if (result == JFileChooser.APPROVE_OPTION) {
                KeyString = "";
                LinkToKey = fileChooser.getSelectedFile();
                if(!isFileEmpty(LinkToKey)) {
                    try {
                        br = new BufferedReader(new FileReader(LinkToKey));
                        while ((line = br.readLine()) != null) {
                            KeyString += line;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    KeyCharArray = KeyString.toCharArray();
                }
                else JOptionPane.showMessageDialog(EncryptionApp, "Файл пустий.","Помилка",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==SaveKey) {

            fileChooser.setDialogTitle("Збереження ключа");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showSaveDialog(EncryptionApp);
            if (result == JFileChooser.APPROVE_OPTION ) {
                LinkToKey = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(LinkToKey + ".txt");
                    writer.write(KeyCharArray);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(EncryptionApp, "Ключ збережено");
            }
        }
        if(e.getSource()==ExitItem) System.exit(0);
        if(e.getSource()==AboutCourseworkItem) new AboutCoursework();
        if(e.getSource()==AboutAuthorItem) new AboutAuthor();
        if(e.getSource()==button){
            if(!input.getText().equals("")) {
                OriginalString = input.getText();

                char[] OriginalCharArray = OriginalString.toCharArray();

                EncryptedCharArray = new char[OriginalString.length()];
                DecryptionCharArray = new char[OriginalString.length()];
                KeyCharArray = new char[OriginalString.length()];
                GenerationKey(KeyCharArray, OriginalCharArray);
                KeyString =  String.valueOf(KeyCharArray);

                EncryptionMetod(OriginalCharArray, EncryptedCharArray, KeyCharArray);

                String EncryptedString = String.valueOf(EncryptedCharArray);
                input2.setText(EncryptedString);
                try {
                    new Sound("true.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(EncryptionApp, "Поле пусте. Введіть повідомлення для шифрування","Помилка",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==button2){
            if(!input.getText().equals("")) {
                if(!Objects.equals(KeyString, "")) {
                    EncryptedString = input.getText();
                    if(KeyString.length() == EncryptedString.length()) {
                        EncryptedCharArray = EncryptedString.toCharArray();

                        DecryptionCharArray = new char[EncryptedString.length()];

                        DecryptionMetod(EncryptedCharArray, DecryptionCharArray, KeyCharArray);

                        String DecryptionString = String.valueOf(DecryptionCharArray);
                        input2.setText(DecryptionString);

                        try {
                            new Sound("true.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(EncryptionApp, "Ключ не підходить по розміру.\nВідкрийте вірний ключ або зашифруйте повідомлення \nдля генерації нового ключа","Помилка",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(EncryptionApp, "Ключ не вибрано чи не згенеровано.\nВідкрийте ключ або зашифруйте повідомлення \nдля генерації нового ключа","Помилка",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(EncryptionApp, "Поле пусте. Введіть повідомлення для розшифрування","Помилка",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==button3){input.setText(""); input2.setText("");}
    }
    public static void EncryptionMetod(char[]OriginalCharArray, char[]EncryptedCharArray, char[]KeyCharArray){
        String ABC = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < OriginalCharArray.length;i++){
            EncryptedCharArray[i] = ABC.charAt(
                        Math.floorMod(ABC.indexOf(OriginalCharArray[i])+ABC.indexOf(KeyCharArray[i]),ABC.length()));
        }
    }
    public static void DecryptionMetod(char[]EncryptedCharArray,char[]DecryptionCharArray,char[]KeyCharArray){
        String ABC = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < EncryptedCharArray.length;i++){
            DecryptionCharArray[i] = ABC.charAt(
                        Math.floorMod(ABC.indexOf(EncryptedCharArray[i])-ABC.indexOf(KeyCharArray[i]),ABC.length()));
        }
    }
    public static void GenerationKey(char [] KeyCharArray, char [] OriginalCharArray){
        String ABC = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < OriginalCharArray.length; i++){
                KeyCharArray[i] = ABC.charAt((int) (Math.random() * ABC.length()));
        }
    }
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }
}

