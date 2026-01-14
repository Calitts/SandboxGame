package core;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


// Classe Principal. Onde o jogo será renderizado.
class Main extends JFrame implements Runnable {
    

    public void run() {
        JFrame frame = new JFrame("Sandbox Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        GamePanel panel = new GamePanel();
        StartMenu menu = new StartMenu(cardLayout, container);

        PauseMenu pauseMenu = new PauseMenu(panel);
        panel.setPauseMenu(pauseMenu);

        container.add(pauseMenu, "PAUSE");
        container.add(menu, "MENU");
        container.add(panel, "GAME");

        frame.add(container, BorderLayout.CENTER);

        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // MOSTRA O MENU PRIMEIRO
        cardLayout.show(container, "MENU");
        panel.startGameThread();

        panel.setPauseMenu(pauseMenu);

        // IMPORTANTE
        panel.setLayout(null);
        panel.add(pauseMenu);
        pauseMenu.setBounds(0, 0, 1280, 720);
        pauseMenu.setVisible(false);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
