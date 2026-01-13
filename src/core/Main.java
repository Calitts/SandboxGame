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

        frame.setLocationRelativeTo(null);
        panel.startGameThread();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}