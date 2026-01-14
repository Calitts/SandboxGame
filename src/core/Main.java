package core;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Classe Principal. Onde o jogo será renderizado.
class Main extends JFrame implements Runnable {

    // int width = 1280;
    // int height = 720;
    protected int originalTileSize = 1;
    protected int scale = 6;

    protected int tileSize = originalTileSize * scale;
    protected int col = 14 * (10 / originalTileSize);
    protected int row = 10 * (10 / originalTileSize);
    protected int panelWidth = tileSize * col;
    protected int menuHeight = 200;
    protected int panelHeight = tileSize * row + menuHeight;
    int width = panelWidth;
    int height = panelHeight;

    @Override
    public void run() {

        JFrame frame = new JFrame("Mix Box");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);

        GamePanel panel = new GamePanel();
        frame.add(panel);
        panel.playMusic(0);

        PauseMenu pauseMenu = new PauseMenu(panel);
        panel.setPauseMenu(pauseMenu);

        panel.setLayout(null);
        panel.add(pauseMenu);
        pauseMenu.setBounds(0, 0, width, height);
        pauseMenu.setVisible(false);

        frame.setVisible(true);
        panel.startGameThread();

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        JLayeredPane startScreen = new JLayeredPane();
        startScreen.setPreferredSize(new Dimension(width, height));

        AnimatedBackground bg = new AnimatedBackground();
        bg.setBounds(0, 0, width, height);

        StartMenu menu = new StartMenu(cardLayout, container);
        menu.setBounds(0, 0, width, height);

        startScreen.add(bg, Integer.valueOf(0));
        startScreen.add(menu, Integer.valueOf(1));

        panel.setComponentZOrder(pauseMenu, 0);

        container.add(menu, "MENU");
        container.add(panel, "GAME");

        frame.add(container, BorderLayout.CENTER);

        // frame.setSize(width, height);
        frame.setSize(panelWidth, panelHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // MOSTRA O MENU PRIMEIRO
        cardLayout.show(container, "MENU");
        // startScreen.setPreferredSize(new Dimension(width, height));
        panel.startGameThread();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
