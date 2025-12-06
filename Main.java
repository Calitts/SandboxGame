import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

class Main implements Runnable {
    public void run() {
        JFrame frame = new JFrame("Sandbox Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pref = new Dimension(500, 500);
        frame.setPreferredSize(pref);

        frame.setBackground(new Color(255, 0, 0));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}