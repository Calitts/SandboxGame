package core;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

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