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
    
    @Override
    public void run() {


        JFrame frame = new JFrame("Sandbox Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        GamePanel panel = new GamePanel();
        frame.add(panel);

        PauseMenu pauseMenu = new PauseMenu(panel);
        panel.setPauseMenu(pauseMenu);

        panel.setLayout(null);
        panel.add(pauseMenu);
        pauseMenu.setBounds(0, 0, 1280, 720);
        pauseMenu.setVisible(false);

        frame.setVisible(true);
        panel.startGameThread();


        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        //menu
        JLayeredPane startScreen = new JLayeredPane();
        startScreen.setPreferredSize(new Dimension(1280, 720));

        AnimatedBackground bg = new AnimatedBackground();
        bg.setBounds(0, 0, 1280, 720);

        StartMenu menu = new StartMenu(cardLayout, container);
        menu.setBounds(0, 0, 1280, 720);

        startScreen.add(bg, Integer.valueOf(0));
        startScreen.add(menu, Integer.valueOf(1));

        panel.setComponentZOrder(pauseMenu, 0);

        container.add(menu, "MENU");
        container.add(panel, "GAME");
        

        frame.add(container, BorderLayout.CENTER);

        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        // MOSTRA O MENU PRIMEIRO
        cardLayout.show(container, "MENU");
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
