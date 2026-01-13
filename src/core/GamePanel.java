package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.lang.Thread;
import javax.swing.JPanel;

import element.*;

class GamePanel extends JPanel implements Runnable {

    protected int originalTileSize = 1;
    protected int scale = 6;

    protected int tileSize = originalTileSize * scale;
    protected int col = 14 * (10 / originalTileSize);
    protected int row = 10 * (10 / originalTileSize);
    protected int width = tileSize * col;
    protected int height = tileSize * row;

    protected int menuHeight = 200;

    Thread gameThread;
    InputHandler input = new InputHandler();

    GamePanel() {
        this.setPreferredSize(new Dimension(width, height + menuHeight));
        this.setBackground(new Color(0xAFA0A0));
        this.setDoubleBuffered(true);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.addKeyListener(input);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void menuPage() {

    }

    private int UPS = 300;
    private int FPS;
    private int ticks;
    private int penSize = 10;
    long nextStat = System.nanoTime();
    Pixel[][] screen = new Pixel[col][row];

    // Main Loop
    @Override
    public void run() {
        double drawInterval = 10e9 / UPS; // 0.017 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        menuPage();

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                screen[x][y] = new Pixel();
            }
        }
        // screen[1][1].setType(new Areia());

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Updates game variables
                update();
                delta--;
            }

            // Redraw screen
            repaint();
            // Performance check
            printStats();
        }
    }

    public void update() {
        ticks++;
        handleInput();
        handlePhysics();
    }

    public void handleInput() {
        int x = input.getPosition().intX();
        int y = input.getPosition().intY();
        if (x >= 0 && x < width && y >= 0 && y < height) {

            Pixel pixel = screen[x / (tileSize)][y / (tileSize)];
            Elemento currentType = input.getType();

            if (input.isMouseClicked()) {
                // System.out.println(
                // String.format("MOUSE CLICKED AT: { %d, %d }", x, y));

                System.out.println(
                        String.format("ELEMENT \"%s\" AT { %d, %d } ", pixel.getName(), x / (tileSize),
                                y / (tileSize)));

            }

            if (input.isMousePressed()) {
                // Bigger pencil
                for (int p_x = x - penSize; p_x < x + penSize; p_x++) {
                    for (int p_y = y - penSize; p_y < y + penSize; p_y++) {
                        if (p_x >= 0 && p_x < width && p_y >= 0 && p_y < height) {
                            Pixel penPixel = screen[p_x / (tileSize)][p_y / (tileSize)];
                            penPixel.setType(currentType);
                        }
                    }
                }
            }

            input.clearMouseClick();
        }
    }

    public void handlePhysics() {
        Pixel[][] oldScreen = new Pixel[col][row];
        Pixel[][] newScreen = new Pixel[col][row];

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                oldScreen[x][y] = new Pixel(screen[x][y].getType());
                newScreen[x][y] = new Pixel();
            }
        }
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = oldScreen[x][y];
                int gravity = pixel.getGravity() > 0 ? 1 : 0;

                if (pixel.getName() == "Ar") {
                    continue;
                }

                if (y + gravity < row && pixel.getGravity() > oldScreen[x][y + gravity].getGravity() && !oldScreen[x][y
                        + gravity].isSolid()) {
                    newScreen[x][y + gravity] = new Pixel(pixel.getType());
                } else {
                    newScreen[x][y] = new Pixel(pixel.getType());
                }

                // switch (pixel.getName()) {

                // case "Areia":
                // if (y + gravity < row && gravity > oldScreen[x][y + gravity].getGravity()) {
                // newScreen[x][y + gravity] = new Pixel(pixel.getType());
                // } else {
                // newScreen[x][y] = new Pixel(pixel.getType());
                // }
                // break;
                // default:
                // break;
                // }

            }
        }

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                screen[x][y] = new Pixel(newScreen[x][y].getType());
            }
        }
    }

    public void paintComponent(Graphics g) {
        FPS++;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Game Rendering
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = screen[x][y];
                if (pixel.getName() == "Ar") {
                    g2.setColor(pixel.getColor(50 * (y % 2)));
                } else {
                    g2.setColor(pixel.getColor(0));
                }
                g2.fillRect(x * tileSize, y * tileSize, originalTileSize * scale, originalTileSize * scale);
            }
        }

        g2.dispose();
    }

    public void printStats() {
        if (System.nanoTime() > nextStat) {
            System.out.println(String.format("TPS: %d FPS: %d", ticks, FPS));
            ticks = 0;
            FPS = 0;
            nextStat = System.nanoTime() + (long) 10e9;
        }
    }

}
