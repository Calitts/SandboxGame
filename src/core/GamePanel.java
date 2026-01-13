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
        this.setBackground(new Color(0xAFA010));
        this.setDoubleBuffered(true);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void menuPage() {

    }

    public int UPS = 200;
    private int FPS;
    private int ticks;
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
                // Redraw screen
                repaint();
                delta--;
            }

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
        Pixel pixel = screen[x / (tileSize)][y / (tileSize)];

        if (input.isMouseClicked()) {
            // System.out.println(
            // String.format("MOUSE CLICKED AT: { %d, %d }", x, y));

            if (x >= 0 && x < width && y >= 0 && y < height) {
                System.out.println(
                        String.format("ELEMENT \"%s\" AT { %d, %d } ", pixel.getName(), x / (tileSize),
                                y / (tileSize)));
                // screen[x / (tileSize * col)][y / (tileSize * row)] = pixel;

            }

        }

        if (input.isMousePressed()) {
            pixel.setType(new Areia());
        }

        input.clearMouseClick();
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
                int gravity = pixel.getGravity();
                if (y + gravity < row) {
                    Pixel prevpixel = newScreen[x][y + gravity];
                    newScreen[x][y + gravity] = new Pixel(pixel.getType());
                    newScreen[x][y] = prevpixel;
                } else {
                    newScreen[x][row - 1] = pixel;
                }
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

        // g2.setColor(Color.BLACK);
        // g2.fillRect(0, 0, width, height);

        // Game Rendering
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = screen[x][y];
                g2.setColor(pixel.getColor());
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
