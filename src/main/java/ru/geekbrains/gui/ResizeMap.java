package ru.geekbrains.gui;

import javax.swing.*;
import java.awt.*;

public class ResizeMap extends JDialog {
    public ResizeMap(GameField gameField) {
//        JFrame jfResizeMap = new JFrame();
        this.setTitle("Choose map size");
        this.setResizable(false);
        this.setModal(true);
        this.setSize(300, 150);
        this.setLocationRelativeTo(XoForm.getFrames()[0]);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // основная панель

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 5, 15));

        JLabel jLabelMap = new JLabel();
        jLabelMap.setText("Размер масива");
        mainPanel.add(jLabelMap);

        JTextField jTextFieldMap = new JTextField();
        jTextFieldMap.setToolTipText("Введите размер массива");
        mainPanel.add(jTextFieldMap);

        JLabel jLabelDotWin = new JLabel();
        jLabelDotWin.setText("Фишек для победы");
        mainPanel.add(jLabelDotWin);

        JTextField jTextFieldDotWin = new JTextField();
        jTextFieldDotWin.setToolTipText("Количество подряд одинаковых фишек для победы");
        mainPanel.add(jTextFieldDotWin);

        this.add(mainPanel, BorderLayout.CENTER);

        // панель кнопок
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setPreferredSize(new Dimension(1, 40));
        Button btnOK = new Button("Save");
        Button btnCancel = new Button("Cancel");
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        this.add(buttonPanel, BorderLayout.SOUTH);

        btnOK.addActionListener(e -> {
            gameField.setMapSize(Integer.parseInt(jTextFieldMap.getText()));
            gameField.setDotToWin(Integer.parseInt(jTextFieldDotWin.getText()));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        this.setVisible(true);
    }
}
