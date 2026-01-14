package core;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class AnimatedBackground extends JPanel {

    private Image gifImage;

    private int offsetX = 0;
    private int offsetY = 0;

    private final int SPEED_X = 1;
    private final int SPEED_Y = 0;

    public AnimatedBackground() {


        URL imageUrl = getClass().getResource("/floresta.gif");
        if (imageUrl != null) {
            this.gifImage = new ImageIcon(imageUrl).getImage();
        }  

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

        if (gifImage != null) {
            g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Imagem não encontrada", 20, 20);
        }
    }
}
