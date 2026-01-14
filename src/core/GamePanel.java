package core;

import element.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


class GamePanel extends JPanel implements Runnable {

    // Resolução base 16:9
    private final int BASE_WIDTH = 1280;
    private final int BASE_HEIGHT = 720;
    private PauseMenu pauseMenu;
    


    protected int originalTileSize = 1;
    protected int scale = 6;

    protected int tileSize = originalTileSize * scale;
    protected int col = 14 * (10 / originalTileSize);
    protected int row = 10 * (10 / originalTileSize);
    protected int width = tileSize * col;
    protected int height = tileSize * row;
    protected boolean paused = false;


    protected int menuHeight = 200;

    Thread gameThread;
    InputHandler input = new InputHandler();

    GamePanel() {
        this.setLayout(null); 
        this.setBackground(new Color(0xAFA010));
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        this. getInputMap(WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("ESCAPE"), "pause");

        this.setPreferredSize(new Dimension(width, height + menuHeight));


        this.getActionMap().put("pause", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        togglePauseMenu();
        }   
    });

    }

    public void resumeGame() {
    paused = false;
    if (pauseMenu != null) {
        pauseMenu.setVisible(false);
    }
    requestFocusInWindow();
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPauseMenu(PauseMenu menu) {
    this.pauseMenu = menu;
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
    if (paused) return;

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
    double scaleX = getWidth() / (double) BASE_WIDTH;
    double scaleY = getHeight() / (double) BASE_HEIGHT;
    double scaleFactor = Math.min(scaleX, scaleY);

    int renderWidth = (int) (BASE_WIDTH * scaleFactor);
    int renderHeight = (int) (BASE_HEIGHT * scaleFactor);

    int offsetX = (getWidth() - renderWidth) / 2;
    int offsetY = (getHeight() - renderHeight) / 2;

    // fundo preto (barras laterais)
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, getWidth(), getHeight());

    // desenha o jogo centralizado
    for (int x = 0; x < col; x++) {
        for (int y = 0; y < row; y++) {
            Pixel pixel = screen[x][y];
            g2.setColor(pixel.getColor());

            int drawX = offsetX + (int) (x * tileSize * scaleFactor);
            int drawY = offsetY + (int) (y * tileSize * scaleFactor);
            int drawSize = (int) (tileSize * scaleFactor);

            g2.fillRect(drawX, drawY, drawSize, drawSize);
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

    private void togglePauseMenu() {
    paused = !paused;
    System.out.println("PAUSE = " + paused);

    if (pauseMenu != null) {
        pauseMenu.setVisible(paused);
        pauseMenu.repaint();

        }

        if(!paused){
            requestFocusInWindow();
        }

    }

}

