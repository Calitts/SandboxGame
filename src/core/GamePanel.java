package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.lang.Thread;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.IOException;

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
    private int penSize = 5;
    long nextStat = System.nanoTime();
    Pixel[][] screen = new Pixel[col][row];
    Elemento currentType = new Ar();
    Pixel cursorPixel = new Pixel();
    // Load images
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

    HashMap<String, Boolean> unlockMap = new HashMap<>();

    // Main Loop
    @Override
    public void run() {
        double drawInterval = 10e9 / UPS; // 0.017 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        unlockMap.put(new Ar().getNome(), true);
        unlockMap.put(new Agua().getNome(), true);
        unlockMap.put(new Pedra().getNome(), true);
        unlockMap.put(new Areia().getNome(), true);
        unlockMap.put(new Fogo().getNome(), true);
        unlockMap.put(new Acido().getNome(), true);
        unlockMap.put(new Alcool().getNome(), true);
        unlockMap.put(new Eletricidade().getNome(), true);
        unlockMap.put(new Madeira().getNome(), true);
        unlockMap.put(new Lava().getNome(), true);
        unlockMap.put(new Metal().getNome(), true);
        unlockMap.put(new Neve().getNome(), true);
        unlockMap.put(new Oleo().getNome(), true);

        menuPage();
        loadTiles();

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                screen[x][y] = new Pixel();
            }
        }

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
        this.setPreferredSize(new Dimension(width, height + menuHeight));
        ticks++;
        handleInput();
        handlePhysics();
        handleChemistry();
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
                if (unlockMap.get(new Sal().getNome()) != null) {
                    currentType = new Sal();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 644, 666)) {
                if (unlockMap.get(new Lama().getNome()) != null) {
                    currentType = new Lama();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 682, 709)) {
                if (unlockMap.get(new Fumaca().getNome()) != null) {
                    currentType = new Fumaca();
                }
            } else if (inRange(x, 508, 587) && inRange(y, 729, 748)) {
                if (unlockMap.get(new Vapor().getNome()) != null) {
                    currentType = new Vapor();
                }
            } else if (inRange(x, 467, 634) && inRange(y, 764, 789)) {
                if (unlockMap.get(new MetalDerretido().getNome()) != null) {
                    currentType = new MetalDerretido();
                }

                // Coluna V
            } else if (inRange(x, 621, 785) && inRange(y, 602, 627)) {
                if (unlockMap.get(new AguaSalgada().getNome()) != null) {
                    currentType = new AguaSalgada();
                }
            } else if (inRange(x, 647, 745) && inRange(y, 644, 666)) {
                if (unlockMap.get(new Vidro().getNome()) != null) {
                    currentType = new Vidro();
                }
            } else if (inRange(x, 647, 745) && inRange(y, 682, 709)) {
                if (unlockMap.get(new Cinzas().getNome()) != null) {
                    currentType = new Cinzas();
                }
            }
            input.setType(currentType);

        }

        if (input.getReset()) {
            for (int n = 0; n < col; n++) {
                for (int m = 0; m < row; m++) {
                    screen[n][m] = new Pixel();
                }
            }
            input.setReset(false);
        }

        if (x >= 0 && x < width && y >= 0 && y < height) {

            cursorPixel = screen[x / (tileSize)][y / (tileSize)];
            currentType = input.getType();

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

        }

        input.clearMouseClick();
    }

    public void handleChemistry() {
        Pixel[][] oldScreen = new Pixel[col][row];
        Pixel[][] newScreen = new Pixel[col][row];
        GerenciadorReacoes gerenciador = new GerenciadorReacoes();

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                oldScreen[x][y] = new Pixel(screen[x][y].getType());
                newScreen[x][y] = new Pixel();
            }
        }

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                Pixel pixel = new Pixel(oldScreen[x][y].getType());
                // int gravity = pixel.isGas() ? 0 : 1;

                if (pixel.getName() == "Ar") {
                    continue;
                }

                boolean success = false;
                for (int n = x - 1; n < x + 1; n++) {
                    for (int m = y - 1; m < y + 1; m++) {
                        try {
                            Pixel nearPixel = oldScreen[n][m];
                            if (gerenciador.combina(pixel.getType(), nearPixel.getType())) {
                                Elemento[] par = gerenciador.processar(pixel.getType(), nearPixel.getType());
                                newScreen[x][y] = new Pixel(par[0]);
                                newScreen[n][m] = new Pixel(par[1]);
                                success = true;
                                for (int i = 0; i < 2; i++) {
                                    if (unlockMap.get(par[i].getNome()) == null) {
                                        unlockMap.put(par[i].getNome(), true);
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
                if (success) {
                    continue;
                }
                newScreen[x][y] = oldScreen[x][y];
            }
        }

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                screen[x][y] = new Pixel(newScreen[x][y].getType());
            }
        }
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
                int gravity = pixel.isGas() ? -1 : 1;

                if (pixel.getName() == "Ar") {
                    continue;
                }

                if (y + gravity >= row || y + gravity < 0) {
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

        renderTiles(g2);

        g2.setColor(new Color(currentType.getCor()));
        g2.fillRect(width - 20, height + 10, 10, menuHeight - 20);

        // Information on Screen
        g2.setColor(Color.WHITE);
        g2.drawString(String.format("ELEMENTO: %s    POSIÇÃO: { %d, %d }", cursorPixel.getName(),
                input.getPosition().intX(), input.getPosition().intY()), 10, 15);
        g2.drawString(String.format("FPS: %5d   TPS: %4d", fps, tps), width - 130, 15);

        g2.dispose();
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

        g2.drawImage(background, 0, height, width, menuHeight, null);

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

        if (unlockMap.get(new Sal().getNome()) != null) {
            g2.drawImage(sal, 490, height - 94, 180, 180, null);
        }
        if (unlockMap.get(new Lama().getNome()) != null) {
            g2.drawImage(lama, 453, height - 16, 170, 170, null);
        }
        if (unlockMap.get(new Fumaca().getNome()) != null) {
            g2.drawImage(fumaca, 460, height - 15, 180, 180, null);
        }
        if (unlockMap.get(new Vapor().getNome()) != null) {
            g2.drawImage(vapor, 470, height + 37, 160, 160, null);
        }
        if (unlockMap.get(new MetalDerretido().getNome()) != null) {
            g2.drawImage(metalliquido, 460, height + 63, 180, 180, null);
        }
        if (unlockMap.get(new AguaSalgada().getNome()) != null) {
            g2.drawImage(aguasalgada, 620, height - 80, 170, 170, null);
        }
        if (unlockMap.get(new Vidro().getNome()) != null) {
            g2.drawImage(vidro, 630, height - 50, 170, 170, null);
        }
        if (unlockMap.get(new Cinzas().getNome()) != null) {
            g2.drawImage(cinzas, 630, height - 12, 170, 170, null);
        }

    }

    public void printStats() {
        if (System.nanoTime() > nextStat) {
            fps = FPS;
            tps = ticks;
            ticks = 0;
            FPS = 0;
            // System.out.println(String.format("TPS: %d FPS: %d", tps, fps));
            nextStat = System.nanoTime() + (long) 10e8;
        }
    }

    public boolean inRange(int cord, int lower, int upper) {
        if (cord >= lower && cord < upper) {
            return true;
        }
        return false;
    }

}
