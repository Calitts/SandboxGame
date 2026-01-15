package core;

import elementos.*;
import elementos.liquido.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.KeyStroke;

class GamePanel extends JPanel implements Runnable {

    protected int originalTileSize = 1;
    protected int scale = 6;

    protected int tileSize = originalTileSize * scale;
    protected int col = 14 * (10 / originalTileSize);
    protected int row = 10 * (10 / originalTileSize);
    protected int width = tileSize * col;
    protected int height = tileSize * row;
    protected int menuHeight = 200;
    protected boolean paused = false;

    // Resolução base 16:9
    // private final int BASE_WIDTH = 1280;
    // private final int BASE_HEIGHT = 720;
    private final int BASE_WIDTH = width;
    private final int BASE_HEIGHT = height + menuHeight;
    private PauseMenu pauseMenu;

    protected Thread gameThread;
    private final InputHandler input = new InputHandler();

    private final Sound sound = new Sound();

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height + menuHeight));
        // this.setSize(width, height + menuHeight);
        this.setBackground(new Color(0xAFA010));
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.addKeyListener(input);

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "pause");

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("C"), "reset");

        this.getActionMap().put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetScreen();
            }
        });

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
        pauseMenu = menu;
    }

    protected int UPS = 300;
    private int FPS = 0;
    private int ticks = 0;
    private int fps = 0;
    private int tps = 0;
    protected int penSize = 5;
    long nextStat = System.nanoTime();
    Screen screen = Screen.getEmptyScreen(col, row);
    Elemento currentType = new Ar();
    Pixel cursorPixel = new Pixel();

    //region Load images
    BufferedImage acido;
    BufferedImage agua;
    BufferedImage aguasalgada;
    BufferedImage alcool;
    BufferedImage ar;
    BufferedImage areia;
    BufferedImage cinzas;
    BufferedImage eletricidade;
    BufferedImage fogo;
    BufferedImage fumaca;
    BufferedImage lama;
    BufferedImage lava;
    BufferedImage madeira;
    BufferedImage metal;
    BufferedImage metalliquido;
    BufferedImage neve;
    BufferedImage oleo;
    BufferedImage pedra;
    BufferedImage sal;
    BufferedImage vapor;
    BufferedImage vidro;
    BufferedImage background;
    //endregion

    HashMap<String, Boolean> unlockMap = new HashMap<>();

    // Main Loop
    @Override
    public void run() {
        double drawInterval = 10e9 / UPS; // 0.017 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        unlockMap.put(new Ar().getName(), true);
        unlockMap.put(new Agua().getName(), true);
        unlockMap.put(new Pedra().getName(), true);
        unlockMap.put(new Areia().getName(), true);
        unlockMap.put(new Fogo().getName(), true);
        unlockMap.put(new Acido().getName(), true);
        unlockMap.put(new Alcool().getName(), true);
        unlockMap.put(new Eletricidade().getName(), true);
        unlockMap.put(new Madeira().getName(), true);
        unlockMap.put(new Lava().getName(), true);
        unlockMap.put(new Metal().getName(), true);
        unlockMap.put(new Neve().getName(), true);
        unlockMap.put(new Oleo().getName(), true);

        loadTiles();

        while (gameThread != null) {
            tileSize = originalTileSize * scale;
            col = 14 * (10 / originalTileSize);
            row = 10 * (10 / originalTileSize);
            width = tileSize * col;
            height = tileSize * row;
            drawInterval = 10e9 / UPS; // 0.017 seconds

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
        this.setPreferredSize(new Dimension(width, height + menuHeight));
        if (paused)
            return;

        ticks++;
        handleInput();
        handlePhysics();
        handleChemistry();
    }

    public void resetScreen() {
        screen.clearScreen();
    }

    public void handleInput() {
        int x = input.getPosition().intX();
        int y = input.getPosition().intY();

        if (input.isMouseClicked()) {
            // System.out.println(String.format("MOUSE CLICKED AT: { %d, %d }", x, y));

            // System.out.println(
            // String.format("ELEMENT \"%s\" AT { %d, %d } ", cursorPixel.getName(), x /
            // (tileSize),
            // y / (tileSize)));

            // Coluna I
            if (inRange(x, 33, 120) && inRange(y, 602, 627)) {
                currentType = new Ar();
            } else if (inRange(x, 33, 120) && inRange(y, 644, 672)) {
                currentType = new Agua();
            } else if (inRange(x, 33, 120) && inRange(y, 684, 710)) {
                currentType = new Pedra();
            } else if (inRange(x, 33, 120) && inRange(y, 724, 755)) {
                currentType = new Areia();
            } else if (inRange(x, 33, 120) && inRange(y, 763, 789)) {
                currentType = new Fogo();

                // Coluna II
            } else if (inRange(x, 174, 262) && inRange(y, 602, 627)) {
                currentType = new Acido();
            } else if (inRange(x, 174, 262) && inRange(y, 643, 666)) {
                currentType = new Alcool();
            } else if (inRange(x, 150, 291) && inRange(y, 683, 709)) {
                currentType = new Eletricidade();
            } else if (inRange(x, 174, 262) && inRange(y, 729, 748)) {
                currentType = new Madeira();
            } else if (inRange(x, 174, 262) && inRange(y, 764, 789)) {
                currentType = new Lava();

                // Coluna III
            } else if (inRange(x, 314, 405) && inRange(y, 602, 627)) {
                currentType = new Metal();
            } else if (inRange(x, 314, 405) && inRange(y, 644, 668)) {
                currentType = new Neve();
            } else if (inRange(x, 314, 405) && inRange(y, 683, 705)) {
                currentType = new Oleo();

                // Coluna IV
            } else if (inRange(x, 508, 587) && inRange(y, 602, 627)) {
                if (unlockMap.get(new Sal().getName()) != null) {
                    currentType = new Sal();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 644, 666)) {
                if (unlockMap.get(new Lama().getName()) != null) {
                    currentType = new Lama();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 682, 709)) {
                if (unlockMap.get(new Fumaca().getName()) != null) {
                    currentType = new Fumaca();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 729, 748)) {
                if (unlockMap.get(new Vapor().getName()) != null) {
                    currentType = new Vapor();
                }
            } else if (inRange(x, 467, 634) && inRange(y, 764, 789)) {
                if (unlockMap.get(new MetalDerretido().getName()) != null) {
                    currentType = new MetalDerretido();
                }

                // Coluna V
            } else if (inRange(x, 621, 785) && inRange(y, 602, 627)) {
                if (unlockMap.get(new AguaSalgada().getName()) != null) {
                    currentType = new AguaSalgada();
                }
            } else if (inRange(x, 647, 745) && inRange(y, 644, 666)) {
                if (unlockMap.get(new Vidro().getName()) != null) {
                    currentType = new Vidro();
                }
            } else if (inRange(x, 647, 745) && inRange(y, 682, 709)) {
                if (unlockMap.get(new Cinzas().getName()) != null) {
                    currentType = new Cinzas();
                }
            }
            input.setType(currentType);
        }

        if (input.getReset()) {
            resetScreen();
            input.setReset(false);
        }

        if (x >= 0 && x < width && y >= 0 && y < height) {

            cursorPixel = screen.getPixel(new Vector2D(x / (tileSize), y / (tileSize)));
            currentType = input.getType();

            if (input.isMousePressed()) {
                // Bigger pencil
                for (int p_x = x - penSize; p_x < x + penSize; p_x++) {
                    for (int p_y = y - penSize; p_y < y + penSize; p_y++) {
                        if (p_x >= 0 && p_x < width && p_y >= 0 && p_y < height) {
                            Pixel penPixel = screen.getPixel(new Vector2D(p_x / (tileSize), p_y / (tileSize)));
                            penPixel.setType(currentType);
                        }
                    }
                }
            }

        }

        input.clearMouseClick();
    }

    public void handleChemistry() {
        Screen oldFrame = screen;
        Screen newFrame = Screen.getEmptyScreen(col, row);
        GerenciadorReacoes gerenciador = new GerenciadorReacoes();
        Vector2D position = new Vector2D();

        for (int x = 0; x < col; x++) {
            position.setX(x);
            for (int y = 0; y < row; y++) {
                Pixel currentPixel = oldFrame.getPixel(x, y);

                if (currentPixel.getType() instanceof Ar) continue;
                boolean success = false;
                position.setY(y);

                for (Pixel neighborPixel : oldFrame.getNeighborPixels(position)){
                    Elemento[] resultado = gerenciador.tryReact(currentPixel.getType(), neighborPixel.getType());
                    if (resultado[0] == null || resultado[1] == null) continue;
                    success = true;

                    newFrame.setPixel(new Pixel(resultado[0]), x, y);
                    newFrame.setPixel(new Pixel(resultado[1]), neighborPixel.getPosition());
                    break;
                }

//
//                for (int n = x - 1; n < x + 1; n++) {
//                    for (int m = y - 1; m < y + 1; m++) {
//                        try {
//                            Elemento nearElement = oldFrame.getPixel(n, m).getType();
//                            Elemento[] resultado = gerenciador.tryReact(currentPixel.getType(), nearElement);
//
//                            if (resultado[0] == null || resultado[1] == null) continue;
//
//                            newFrame.setPixel(new Pixel(resultado[0]), x, y);
//                            newFrame.setPixel(new Pixel(resultado[1]), n, m);
//                            success = true;
//
//                            for (int i = 0; i < 2; i++) {
//                                if (unlockMap.get(resultado[i].getName()) == null) {
//                                    unlockMap.put(resultado[i].getName(), true);
//                                    playSE(1);
//                                }
//                            }
//                        } catch (IndexOutOfBoundsException ignored) {
//                        }
//                    }
//                }
                if (success) continue;

                newFrame.setPixel(oldFrame.getPixel(position), position);
            }
        }

        screen = newFrame;
    }

    public void handlePhysics() {
        Random random = new Random();
        Pixel[][] oldFrame = new Pixel[col][row];
        Pixel[][] newFrame = new Pixel[col][row];

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                oldFrame[x][y] = screen.getPixel(x, y);
                newFrame[x][y] = new Pixel();
            }
        }
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = oldFrame[x][y];
                int gravity = pixel.isGas() ? -1 : 1;

                if (pixel.getType() instanceof Ar) {
                    continue;
                }

                if (y + gravity >= row || y + gravity < 0) {
                    newFrame[x][y] = pixel;
                } else if (pixel.getWeight() > oldFrame[x][y + gravity].getWeight() && !oldFrame[x][y + gravity].isSolid()) {
                    Pixel tempPixel = oldFrame[x][y + gravity];
                    newFrame[x][y + gravity] = pixel;
                    newFrame[x][y] = tempPixel;
                } else {

                    int dir = random.nextInt(3);
                    int end, inc;
                    if (dir != 0) {
                        inc = 1;
                        end = x + pixel.getFlow() + 1;
                    } else {
                        inc = -1;
                        end = x - pixel.getFlow() - 1;
                    }

                    boolean success = false;

                    for (int i = x + inc; i != end; i += inc) {
                        try {
                            if (!oldFrame[i][y + 1].isSolid()) {
                                if (pixel.getWeight() > oldFrame[i][y + 1].getWeight()) {
                                    Pixel tempPixel = oldFrame[i][y + 1];
                                    newFrame[i][y + 1] = pixel;
                                    newFrame[x][y] = tempPixel;
                                    success = true;
                                    break;
                                }
                            } else {
                                break;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    if (success) {
                        continue;
                    }
                    newFrame[x][y] = pixel;
                }
            }
        }

        screen.copyData(newFrame);
    }

    public void paintComponent(Graphics g) {
        FPS++;
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // Game Rendering
        double scaleX = getWidth() / (double) BASE_WIDTH;
        double scaleY = getHeight() / (double) BASE_HEIGHT;
        double scaleFactor = Math.min(scaleX, scaleY);

        int renderWidth = (int) (BASE_WIDTH * scaleFactor);
        int renderHeight = (int) (BASE_HEIGHT * scaleFactor);

        int offsetX = (getWidth() - renderWidth) / 2;
        int offsetY = (getHeight() - renderHeight) / 2;

        // Fundo preto (Barras laterais)
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        // desenha o jogo centralizado
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = screen.getPixel(x, y);
                if (pixel.getType() instanceof Ar) {
                    g2D.setColor(pixel.getColor(50 * (y % 2)));
                } else {
                    g2D.setColor(pixel.getColor(0));
                }

                int drawX = offsetX + (int) (x * tileSize * scaleFactor);
                int drawY = offsetY + (int) (y * tileSize * scaleFactor);
                int drawSize = (int) (tileSize * scaleFactor);

                g2D.fillRect(drawX, drawY, drawSize, drawSize);
            }
        }

        renderTiles(g2D);

        g2D.setColor(new Color(currentType.getCor()));
        g2D.fillRect(width - 40, height + 10, 10, menuHeight - 20);

        // Information on Screen
        g2D.setColor(Color.WHITE);
        g2D.drawString(String.format("ELEMENTO: %s    POSIÇÃO: { %d, %d }", cursorPixel.getName(),
                input.getPosition().intX(), input.getPosition().intY()), 10, 15);
        g2D.drawString(String.format("FPS: %5d   TPS: %4d", fps, tps), width - 150, 15);

        g2D.dispose();
    }

    public void loadTiles() {
        try {
            acido = ImageIO.read(getClass().getResourceAsStream("../assets/acido.png"));
            agua = ImageIO.read(getClass().getResourceAsStream("../assets/agua.png"));
            aguasalgada = ImageIO.read(getClass().getResourceAsStream("../assets/aguasalgada.png"));
            alcool = ImageIO.read(getClass().getResourceAsStream("../assets/alcool.png"));
            ar = ImageIO.read(getClass().getResourceAsStream("../assets/ar.png"));
            areia = ImageIO.read(getClass().getResourceAsStream("../assets/areia.png"));
            cinzas = ImageIO.read(getClass().getResourceAsStream("../assets/cinzas.png"));
            eletricidade = ImageIO.read(getClass().getResourceAsStream("../assets/eletricidade.png"));
            fogo = ImageIO.read(getClass().getResourceAsStream("../assets/fogo.png"));
            fumaca = ImageIO.read(getClass().getResourceAsStream("../assets/fumaca.png"));
            lama = ImageIO.read(getClass().getResourceAsStream("../assets/lama.png"));
            lava = ImageIO.read(getClass().getResourceAsStream("../assets/lava.png"));
            madeira = ImageIO.read(getClass().getResourceAsStream("../assets/madeira.png"));
            metal = ImageIO.read(getClass().getResourceAsStream("../assets/metal.png"));
            metalliquido = ImageIO.read(getClass().getResourceAsStream("../assets/metalliquido.png"));
            neve = ImageIO.read(getClass().getResourceAsStream("../assets/neve.png"));
            oleo = ImageIO.read(getClass().getResourceAsStream("../assets/oleo.png"));
            pedra = ImageIO.read(getClass().getResourceAsStream("../assets/pedra.png"));
            sal = ImageIO.read(getClass().getResourceAsStream("../assets/sal.png"));
            vapor = ImageIO.read(getClass().getResourceAsStream("../assets/vapor.png"));
            vidro = ImageIO.read(getClass().getResourceAsStream("../assets/vidro.png"));
            vidro = ImageIO.read(getClass().getResourceAsStream("../assets/vidro.png"));
            background = ImageIO.read(getClass().getResourceAsStream("../assets/background.jpg"));

        } catch (IOException e) {
            System.out.println("Could not load from path");
            e.printStackTrace();
        }
    }

    public void renderTiles(Graphics2D g2) {

        g2.drawImage(background, 0, height, getWidth(), menuHeight, null);

        g2.drawImage(ar, 5, height - 75, 150, 150, null);
        g2.drawImage(agua, -23, height - 20, 180, 180, null);
        g2.drawImage(pedra, -1, height + 35, 170, 170, null);
        g2.drawImage(areia, -1, height + 75, 170, 170, null);
        g2.drawImage(fogo, -20, height + 105, 170, 170, null);

        g2.drawImage(acido, 165, height - 90, 175, 175, null);
        g2.drawImage(alcool, 150, height - 53, 175, 175, null);
        g2.drawImage(eletricidade, 140, height + 50, 160, 160, null);
        g2.drawImage(madeira, 140, height + 30, 175, 175, null);
        g2.drawImage(lava, 138, height + 73, 170, 170, null);

        g2.drawImage(metal, 290, height - 88, 170, 170, null);
        g2.drawImage(neve, 300, height - 56, 180, 180, null);
        g2.drawImage(oleo, 300, height - 18, 180, 180, null);

        // * Desbloqueáveis

        if (unlockMap.get(new Sal().getName()) != null) {
            g2.drawImage(sal, 490, height - 94, 180, 180, null);
        }
        if (unlockMap.get(new Lama().getName()) != null) {
            g2.drawImage(lama, 453, height - 16, 170, 170, null);
        }
        if (unlockMap.get(new Fumaca().getName()) != null) {
            g2.drawImage(fumaca, 460, height - 15, 180, 180, null);
        }
        if (unlockMap.get(new Vapor().getName()) != null) {
            g2.drawImage(vapor, 470, height + 37, 160, 160, null);
        }
        if (unlockMap.get(new MetalDerretido().getName()) != null) {
            g2.drawImage(metalliquido, 460, height + 63, 180, 180, null);
        }
        if (unlockMap.get(new AguaSalgada().getName()) != null) {
            g2.drawImage(aguasalgada, 620, height - 80, 170, 170, null);
        }
        if (unlockMap.get(new Vidro().getName()) != null) {
            g2.drawImage(vidro, 630, height - 50, 170, 170, null);
        }
        if (unlockMap.get(new Cinzas().getName()) != null) {
            g2.drawImage(cinzas, 630, height - 12, 170, 170, null);
        }
    }

    public void printStats() {
        if (System.nanoTime() > nextStat) {
            fps = FPS;
            tps = ticks;
            ticks = 0;
            FPS = 0;
            width = getWidth();
            height = getHeight();
            // System.out.println(String.format("TPS: %d FPS: %d", tps, fps));
            nextStat = System.nanoTime() + (long) 10e8;
        }
    }

    public boolean inRange(int cord, int lower, int upper) {
        return cord >= lower && cord < upper;
    }

    private void togglePauseMenu() {
        paused = !paused;
        System.out.println("PAUSE = " + paused);

        if (pauseMenu != null) {
            pauseMenu.setVisible(paused);
            pauseMenu.repaint();
        }

        if (!paused) {
            requestFocusInWindow();
        }
    }

    public void playMusic(int i) {
        sound.loop(i);
    }

    public void stopMusic() {
        sound.stop(0);
    }

    public void playSE(int i) {
        sound.play(i);
    }

}