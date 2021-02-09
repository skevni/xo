package ru.geekbrains.gui.temp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    int value1;

    public MainForm() {
        this.setBounds(500, 300, 800, 400);
        this.setTitle("Simple Swing App");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JRadioButton jrb1 = new JRadioButton("1");
        JRadioButton jrb2 = new JRadioButton("2");
        JRadioButton jrb3 = new JRadioButton("3");
        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(jrb1);
        rbGroup.add(jrb2);
        rbGroup.add(jrb3);

//        JButton buttonC = new JButton("[ CENTER ] Click Me!!!");
//        this.add(buttonC, BorderLayout.CENTER);
//
//        JButton buttonN = new JButton("[ NORTH ] Click Me!!!");
//        this.add(buttonN, BorderLayout.NORTH);
//
//        JButton buttonW = new JButton("[ WEST ] Click Me!!!");
//        this.add(buttonW, BorderLayout.WEST);
//
//        buttonC.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("CENTER CLICK!!!");
//            }
//        });

        JTextArea textArea = new JTextArea();
        JScrollPane jsp = new JScrollPane(textArea);
        this.add(jsp, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(1, 10, 5, 0));
        this.add(southPanel, BorderLayout.SOUTH);

        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            southPanel.add(button);

            final int index = i;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    value1 = index;
                    textArea.append(String.valueOf(index) + '\n');
                }
            });
        }

        JTextField textField = new JTextField();
        this.add(textField, BorderLayout.NORTH);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.append(textField.getText() + '\n');
                textField.setText("");
            }
        });

        this.setVisible(true);
    }
}
