package core;

import java.awt.*;
import javax.swing.*;

public class AnimatedBackground extends JPanel {

    private int offsetX = 0;
    private int offsetY = 0;

    private final int SPEED_X = 1;
    private final int SPEED_Y = 0;

    public AnimatedBackground() {
        setOpaque(true);

        Timer timer = new Timer(30, e -> {
            offsetX -= SPEED_X;
            offsetY -= SPEED_Y;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        
        g2.setColor(new Color(80, 140, 200));
        g2.fillRect(0, 0, getWidth(), getHeight());

    
        g2.setColor(new Color(255, 255, 255, 180));

        int cloudSize = 120;

        for (int x = offsetX; x < getWidth() + cloudSize; x += 200) {
            g2.fillRect(x, 80, cloudSize, 40);
            g2.fillRect(x + 30, 60, cloudSize - 40, 30);
        }

       
        g2.setColor(new Color(60, 120, 80));
        g2.fillRect(0, getHeight() - 150, getWidth(), 150);

        g2.dispose();
    }
}

