package ru.geekbrains.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameField extends JPanel {
    private int cellSize = 120;
    private int mapSize = 3; //количество ячеек
    private int dotToWin = 3; // ячеек на победу
    private static String message;

    private int screenWidth;
    private int screenHeight;

    private byte[][] map;
    private boolean isGameOn;

    public GameField() {
        getScreenResolution();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isGameOn) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        int cellX = e.getX() / cellSize;
                        int cellY = e.getY() / cellSize;
                        if (setDotTo(cellX, cellY, (byte) 1)) {
                            checkWin((byte) 1);
                            checkDraw();
                            aiTurn();
                            checkWin((byte) 2);
                        }
                    }
                }
            }
        });
        startGame();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public void setDotToWin(int dotToWin) {
        this.dotToWin = dotToWin;
    }

    public void checkDraw() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == 0) {
                    return;
                }
            }
        }
        isGameOn = false;
        message = "Ничья";
        repaint();
    }


    public void startGame() {
        calcMapSize();
        this.map = new byte[mapSize][mapSize];
        this.isGameOn = true;
        repaint();
    }

    public int[] calcMapSize() {
        int gameFieldSize = mapSize * cellSize + mapSize * 3;
        if (gameFieldSize + 75 > screenHeight) {
            if (cellSize - (((gameFieldSize + 75) - screenHeight) / mapSize) < 15) {
                JOptionPane.showMessageDialog(this, "Слишком маленькие ячейки.\nУменьшите карту.");
                return new int[]{-1, -1};
            }
            cellSize = (cellSize - (((gameFieldSize + 75) - screenHeight) / mapSize));
            // получим заново новые данные по карте
            gameFieldSize = mapSize * cellSize + mapSize * 3;
        }
        return new int[]{gameFieldSize, gameFieldSize + 77};
    }

    public void checkWin(byte player) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (checkLine(j, i, player)) {
                    if (player == (byte) 1) {
                        message = "Победил игрок!";
                    }
                    if (player == (byte) 2) {
                        message = "Победил ИИ!";
                    }
                    isGameOn = false;
                    repaint();
                    return;
                }
            }
        }
    }

    /**
     * Проверка победной линии
     *
     * @param cellX координата по горизонтали
     * @param cellY координата по вертикали
     * @return true - есть победная линия, false - нет победной линии
     */
    public boolean checkLine(int cellX, int cellY, byte player_dot) {
        int horizontal = 0, vertical = 0, diagonal_up_down = 0, diagonal_down_up = 0;
        for (int i = 0; i < dotToWin; i++) {
            if (cellX + i < mapSize) {
                if (map[cellX + i][cellY] == player_dot) {
                    horizontal++;
                }
            }
            if (cellY + i < mapSize) {
                if (map[cellX][cellY + i] == player_dot) {
                    vertical++;
                }
            }
            // вид диагонали = прямой слэш, координиаты, например, {0,0},{1,1},{2,2}
            if ((cellX + i) < mapSize && (cellY + i) < mapSize) {
                if (map[cellX + i][cellY + i] == player_dot) {
                    diagonal_up_down++;
                }
            }
            // вид диагонали = обратный слэш, координиаты, например, {0,2},{1,1},{2,0}
            if ((cellY - i) >= 0 && (cellX + i) < mapSize) {
                if (map[cellX + i][cellY - i] == player_dot) {
                    diagonal_down_up++;
                }
            }
        }
        return horizontal >= dotToWin || vertical >= dotToWin || diagonal_up_down >= dotToWin
                || diagonal_down_up >= dotToWin;
    }

    public void aiTurn() {
        if (isGameOn) {
            int cellX, cellY;
            do {
                cellX = (int) (Math.random() * mapSize);
                cellY = (int) (Math.random() * mapSize);
            } while (!setDotTo(cellX, cellY, (byte) 2));
            repaint();
            checkDraw();
        }
    }

    private boolean setDotTo(int cellX, int cellY, byte dot) {
        if (cellX < 0 || cellY < 0 || cellX >= mapSize || cellY >= mapSize) {
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
        g.fillRect(0, 0, mapSize * cellSize, mapSize * cellSize);
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                ((Graphics2D) g).setStroke(new BasicStroke(3));
                g.setColor(Color.BLACK);
                g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
                if (map[i][j] == 1) {
                    ((Graphics2D) g).setStroke(new BasicStroke(6));
                    g.setColor(Color.GREEN);
                    g.drawOval(i * cellSize + 10, j * cellSize + 10, cellSize - 20, cellSize - 20);
                }
                if (map[i][j] == 2) {
                    ((Graphics2D) g).setStroke(new BasicStroke(6));
                    g.setColor(Color.RED);
                    g.drawLine(i * cellSize + 20, j * cellSize + 20, (i + 1) * cellSize - 20, (j + 1) * cellSize - 20);
                    g.drawLine(i * cellSize + 20, (j + 1) * cellSize - 20, (i + 1) * cellSize - 20, j * cellSize + 20);
                }
            }
        }
        if (!isGameOn) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.BOLD, 24));
            g.drawString(message, 10, 200);
//            Not work on Ubuntu 20.04 (Need to check on Windows)
//            JOptionPane.showMessageDialog(this.getParent(), message, "Message", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void getScreenResolution() {

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = graphicsDevice.getDisplayMode().getWidth();
        screenHeight = graphicsDevice.getDisplayMode().getHeight();
    }
}
