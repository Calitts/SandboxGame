package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.lang.Thread;
import javax.swing.JPanel;
import java.util.Random;
import java.lang.Math;

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
    private int FPS = 0;
    private int ticks = 0;
    private int fps = 0;
    private int tps = 0;
    private int penSize = 1;
    long nextStat = System.nanoTime();
    Pixel[][] screen = new Pixel[col][row];
    Elemento currentType = new Ar();
    Pixel cursorPixel = new Pixel();

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

            // Measure Performance
            printStats();
        }
    }

    public void update() {
        ticks++;
        handleInput();
        handlePhysics();
        handleChemistry();
    }

    public void handleInput() {
        int x = input.getPosition().intX();
        int y = input.getPosition().intY();
        if (x >= 0 && x < width && y >= 0 && y < height) {

            cursorPixel = screen[x / (tileSize)][y / (tileSize)];
            currentType = input.getType();

            if (input.isMouseClicked()) {
                // System.out.println(
                // String.format("MOUSE CLICKED AT: { %d, %d }", x, y));

                System.out.println(
                        String.format("ELEMENT \"%s\" AT { %d, %d } ", cursorPixel.getName(), x / (tileSize),
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

    public void handleChemistry() {

    }

    public void handlePhysics() {
        Random gen = new Random();
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
                Pixel pixel = new Pixel(oldScreen[x][y].getType());
                int gravity = pixel.isGas() ? 0 : 1;

                if (pixel.getName() == "Ar") {
                    continue;
                }

                if (y + gravity >= row) {
                    newScreen[x][y] = new Pixel(pixel.getType());

                } else if (pixel.getWeight() > oldScreen[x][y + gravity].getWeight()
                        && !oldScreen[x][y + gravity].isSolid()) {
                    Pixel tempPixel = oldScreen[x][y + gravity];
                    newScreen[x][y + gravity] = new Pixel(pixel.getType());
                    newScreen[x][y] = new Pixel(tempPixel.getType());

                } else {
                    // newScreen[x][y] = new Pixel(pixel.getType());

                    // switch (pixel.getName()) {
                    // case "Areia":

                    int dir = gen.nextInt(3);
                    int end, inc;
                    if (dir != 0) {
                        inc = 1;
                        // start = x - pixel.getFlow();
                        end = x + pixel.getFlow() + 1;
                    } else {
                        inc = -1;
                        end = x - pixel.getFlow() - 1;
                        // start = x + pixel.getFlow();
                    }

                    boolean succes = false;

                    for (int i = x + inc; i != end; i += inc) {
                        try {
                            if (!oldScreen[i][y + 1].isSolid()) {
                                if (pixel.getWeight() > oldScreen[i][y + 1].getWeight()) {
                                    Pixel tempPixel = oldScreen[i][y + 1];
                                    newScreen[i][y + 1] = new Pixel(pixel.getType());
                                    newScreen[x][y] = new Pixel(tempPixel.getType());
                                    succes = true;
                                    break;
                                }
                            } else {
                                // newScreen[x][y] = new Pixel(pixel.getType());
                                break;
                            }

                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    if (succes) {
                        continue;
                    }
                    newScreen[x][y] = new Pixel(pixel.getType());
                    

                    // break;
                    // default:
                    // newScreen[x][y] = new Pixel(pixel.getType());
                    // break;
                    // }
                }
            }
        }

        for (

                int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                screen[x][y] = new Pixel(newScreen[x][y].getType());
            }
        }
    }

    public void paintComponent(Graphics g) {
        FPS++;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(currentType.getCor()));
        g2.fillRect(width - 20, height + 10, 10, menuHeight - 20);

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

        // Information on Screen
        g2.setColor(Color.WHITE);
        g2.drawString(String.format("ELEMENTO: %s    POSIÇÃO: { %d, %d }", cursorPixel.getName(),
                input.getPosition().intX(), input.getPosition().intY()), 10, 15);
        g2.drawString(String.format("FPS: %5d   TPS: %4d", fps, tps), width - 130, 15);

        g2.dispose();
    }

    public void printStats() {
        if (System.nanoTime() > nextStat) {
            fps = FPS;
            tps = ticks;
            ticks = 0;
            FPS = 0;
            System.out.println(String.format("TPS: %d FPS: %d", tps, fps));
            nextStat = System.nanoTime() + (long) 10e8;
        }
    }

}
