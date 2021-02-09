package ru.geekbrains.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameField extends JPanel {
    private final int CELL_SIZE = 120;
    private final int MAP_SIZE = 3;

    private byte[][] map;
    private boolean isGameOn;

    public GameField() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isGameOn) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        int cellX = e.getX() / CELL_SIZE;
                        int cellY = e.getY() / CELL_SIZE;
                        if (setDotTo(cellX, cellY, (byte) 1)) {
                            checkDraw();
                            aiTurn();
                        }
                    }
                }
            }
        });
        startGame();
    }

    public void checkDraw() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] == 0) {
                    return;
                }
            }
        }
        isGameOn = false;
        repaint();
    }

    public void startGame() {
        this.map = new byte[MAP_SIZE][MAP_SIZE];
        this.isGameOn = true;
        repaint();
    }

    public void aiTurn() {
        if (isGameOn) {
            int cellX, cellY;
            do {
                cellX = (int) (Math.random() * MAP_SIZE);
                cellY = (int) (Math.random() * MAP_SIZE);
            } while (!setDotTo(cellX, cellY, (byte) 2));
            repaint();
            checkDraw();
        }
    }

    private boolean setDotTo(int cellX, int cellY, byte dot) {
        if (cellX < 0 || cellY < 0 || cellX >= MAP_SIZE || cellY >= MAP_SIZE) {
            return false;
        }
        if (map[cellX][cellY] == 0) {
            map[cellX][cellY] = dot;
            repaint();
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, MAP_SIZE * CELL_SIZE, MAP_SIZE * CELL_SIZE);
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                ((Graphics2D) g).setStroke(new BasicStroke(3));
                g.setColor(Color.BLACK);
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (map[i][j] == 1) {
                    ((Graphics2D) g).setStroke(new BasicStroke(6));
                    g.setColor(Color.GREEN);
                    g.drawOval(i * CELL_SIZE + 10, j * CELL_SIZE + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                }
                if (map[i][j] == 2) {
                    ((Graphics2D) g).setStroke(new BasicStroke(6));
                    g.setColor(Color.RED);
                    g.drawLine(i * CELL_SIZE + 20, j * CELL_SIZE + 20, (i + 1) * CELL_SIZE - 20, (j + 1) * CELL_SIZE - 20);
                    g.drawLine(i * CELL_SIZE + 20, (j + 1) * CELL_SIZE - 20, (i + 1) * CELL_SIZE - 20, j * CELL_SIZE + 20);
                }
            }
        }
        if (!isGameOn) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.BOLD, 48));
            g.drawString("GAME OVER", 10, 160);
        }
    }
}
