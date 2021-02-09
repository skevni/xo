package ru.geekbrains.gui;

import javax.swing.*;
import java.awt.*;

public class XoForm extends JFrame {
    private GameField gameField;

    public XoForm() {

        this.setTitle("Крестики-нолики");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.gameField = new GameField();
        int[] coordinates = gameField.calcMapSize();
        if (coordinates[0] > 0 || coordinates[1] > 0) {
            this.setSize(coordinates[0], coordinates[1]);
            this.setLocation(((gameField.getScreenWidth() - coordinates[0]) / 2), ((gameField.getScreenHeight() - coordinates[0]) / 2));
            this.revalidate();
        }

        this.add(gameField);
        //кнопки появляются в одну строку, даже если col = 2
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        Button btnStart = new Button("Start New Game");
        Button btnExit = new Button("Exit Game");
        Button btnMapSize = new Button("Map size");
        bottomPanel.add(btnStart);
        bottomPanel.add(btnMapSize);
        bottomPanel.add(btnExit);
        bottomPanel.setPreferredSize(new Dimension(1, 40));
        this.add(bottomPanel, BorderLayout.SOUTH);

        btnStart.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnExit.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnMapSize.setFont(new Font("Times New Roman", Font.BOLD, 14));

        btnStart.setBackground(Color.WHITE);
        btnExit.setBackground(Color.WHITE);
        btnMapSize.setBackground(Color.WHITE);

        btnStart.addActionListener(actionEvent -> gameField.startGame());

        btnExit.addActionListener(actionEvent -> System.exit(0));

        btnMapSize.addActionListener(e -> {
            // не знаю насколько правильго делать непривязанную ссылку
//            может тогда сделать привязать, а затем приравнять к null?
            new ResizeMap(gameField);
            int[] coordinates1 = gameField.calcMapSize();
            if (coordinates1[0] > 0 || coordinates1[1] > 0) {
                setSize(coordinates1[0], coordinates1[1]);
                setLocation(((gameField.getScreenWidth() - coordinates1[0]) / 2), ((gameField.getScreenHeight() - coordinates1[0]) / 2));
                revalidate();
                gameField.startGame();
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        // фрейм в потоке диспетчеризации
        SwingUtilities.invokeLater(XoForm::new);
    }


}
