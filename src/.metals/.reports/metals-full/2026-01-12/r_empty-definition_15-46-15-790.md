error id: file:///C:/Users/cauap/OneDrive/Documentos/Programs/Code/Java/SandboxGame/src/Main.java:java/awt/Frame#isResizable().
file:///C:/Users/cauap/OneDrive/Documentos/Programs/Code/Java/SandboxGame/src/Main.java
empty definition using pc, found symbol in pc: java/awt/Frame#isResizable().
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 940
uri: file:///C:/Users/cauap/OneDrive/Documentos/Programs/Code/Java/SandboxGame/src/Main.java
text:
```scala
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

// Classe Principal. Onde o jogo será renderizado.
class Main extends JFrame implements Runnable {
    // Timer t;
    // int tick = 30;
    GamePanel panel;

    public void run() {
        // setSize(500, 500);

        // ActionListener action = new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {

        //         repaint();
        //     }
        // };
        // t = new Timer(tick, action);
        // t.start();
        JFrame frame = new JFrame("Sandbox Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(255, 0, 0));
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.i@@sResizable();

        panel = new GamePanel();
        frame.add(panel);
    
        frame.pack();
        frame.setVisible(true);
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 600, 400);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/awt/Frame#isResizable().