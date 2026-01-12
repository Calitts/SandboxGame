import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.lang.Thread;

import javax.swing.JPanel;

class GamePanel extends JPanel implements Runnable {

    protected int originalTileSize = 10;
    protected int scale = 6;

    protected int tileSize = originalTileSize * scale;
    protected int col = 14;
    protected int row = 12;
    protected int width = tileSize * col;
    protected int height = tileSize * row;

    Thread gameThread;

    GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        while (gameThread != null) {
            // Updates game variables
            update();

            // Redraw screen
            repaint();
        }
    }

    public void update() {
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, width/2, height/2);

        g2.dispose();
    }


}
