package core;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PixelTitle extends JPanel {

    private final String text = "SANDBOX GAME";
    private final int pixelSize = 10;
    private final Color[] palette = {
        new Color(255, 95, 86),
        new Color(255, 189, 46),
        new Color(39, 201, 63),
        new Color(10, 132, 255),
        new Color(191, 90, 242)
    };

    public PixelTitle() {
        setOpaque(false);
        setPreferredSize(new Dimension(600, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Random rand = new Random(1);
        int x = 20;
        int y = 40;

        for (char c : text.toCharArray()) {
            Color color = palette[rand.nextInt(palette.length)];
            drawPixelChar(g2, c, x, y, color);
            x += pixelSize * 6;
        }
    }

    private void drawPixelChar(Graphics2D g2, char c, int x, int y, Color color) {
        g2.setColor(color);
        Font font = new Font("Monospaced", Font.BOLD, pixelSize * 2);
        g2.setFont(font);
        g2.drawString(String.valueOf(c), x, y);
    }
}
