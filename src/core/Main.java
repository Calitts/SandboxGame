package core;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


// Classe Principal. Onde o jogo será renderizado.
class Main extends JFrame implements Runnable {
    GamePanel panel;

    public void run() {
        JFrame frame = new JFrame("Sandbox Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        panel = new GamePanel();
        frame.add(panel);
        
        frame.pack();
        frame.setVisible(true);

        KeyHandler keyHandler = new keyHandler(frame, panel);
        frame.addKeyListener(keyHandler);

        StartMenu menu = new StartMenu(frame, panel);
        menu.setVisible(true);

        frame.setLocationRelativeTo(null);
        panel.startGameThread();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}