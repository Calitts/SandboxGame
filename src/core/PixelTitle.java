package core;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class PixelTitle extends JPanel {

    // --- MUDANÇA 1: NOVO NOME ---
    private final String text = "MIX BOX";
    private float angle = 0;

    // Aumentei o pixelScale de 9 para 11, pois o nome é mais curto
    // Assim ele preenche melhor a tela
    private final int pixelScale = 11; 
    private final int letterSpacing = 3; 

    // --- PALETA VIBRANTE (Mantive o Ouro/Fogo que destaca na floresta) ---
    private final Color[] vibrantPalette = {
        new Color(255, 215, 0),   // Dourado
        new Color(255, 165, 0),   // Laranja
        new Color(255, 255, 100), // Amarelo Claro
        new Color(255, 140, 0)    // Laranja Escuro
    };

    private final Map<Character, int[][]> pixelFont = new HashMap<>();

    public PixelTitle() {
        setOpaque(false);
        setPreferredSize(new Dimension(900, 200)); // Ajuste de altura
        loadPixelFontData();

        Timer timer = new Timer(30, e -> {
            angle += 0.15f; 
            if (angle > Math.PI * 2) angle = 0;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawBackgroundGlow(g2);

        int totalWidthPixels = 0;
        for (char c : text.toCharArray()) {
            if (c == ' ') totalWidthPixels += (4 * pixelScale);
            else totalWidthPixels += (5 * pixelScale) + (letterSpacing * pixelScale);
        }

        int startX = (getWidth() - totalWidthPixels) / 2;
        int currentX = startX;
        int baseY = 60;

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == ' ') {
                currentX += (4 * pixelScale);
                continue;
            }

            int[][] matrix = pixelFont.getOrDefault(c, pixelFont.get('?'));
            
            // Onda suave
            int waveOffset = (int) (Math.sin(angle + (i * 0.6)) * 8); 
            int currentY = baseY + waveOffset;

            Color mainColor = vibrantPalette[i % vibrantPalette.length];

            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 5; col++) {
                    if (matrix[row][col] == 1) {
                        int drawX = currentX + (col * pixelScale);
                        int drawY = currentY + (row * pixelScale);

                        // Contorno Preto Grosso
                        g2.setColor(Color.BLACK);
                        g2.fillRect(drawX - 2, drawY - 2, pixelScale + 4, pixelScale + 4);
                        
                        // Cor Principal
                        g2.setColor(mainColor);
                        g2.fillRect(drawX, drawY, pixelScale, pixelScale);

                        // Brilho
                        g2.setColor(new Color(255, 255, 255, 180));
                        g2.fillRect(drawX, drawY, pixelScale / 3, pixelScale / 3);
                    }
                }
            }
            currentX += (5 * pixelScale) + (letterSpacing * pixelScale);
        }
    }

    private void drawBackgroundGlow(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        float[] dist = {0.0f, 0.7f};
        Color[] colors = {new Color(0, 0, 0, 150), new Color(0, 0, 0, 0)};
        
        RadialGradientPaint p = new RadialGradientPaint(
            new Point(w/2, h/2), w/2, dist, colors
        );
        g2.setPaint(p);
        g2.fillRect(0, 0, w, h);
    }

    private void loadPixelFontData() {
        pixelFont.put('M', new int[][]{{1,0,0,0,1},{1,1,0,1,1},{1,0,1,0,1},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1}});
        pixelFont.put('I', new int[][]{{0,1,1,1,0},{0,0,1,0,0},{0,0,1,0,0},{0,0,1,0,0},{0,0,1,0,0},{0,0,1,0,0},{0,1,1,1,0}});
        pixelFont.put('X', new int[][]{{1,0,0,0,1},{1,0,0,0,1},{0,1,0,1,0},{0,0,1,0,0},{0,1,0,1,0},{1,0,0,0,1},{1,0,0,0,1}});
        pixelFont.put('B', new int[][]{{1,1,1,1,0},{1,0,0,0,1},{1,0,0,0,1},{1,1,1,1,0},{1,0,0,0,1},{1,0,0,0,1},{1,1,1,1,0}});
        pixelFont.put('O', new int[][]{{0,1,1,1,0},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{0,1,1,1,0}});
        pixelFont.put('?', new int[][]{{0,1,1,1,0},{1,0,0,0,1},{0,0,0,1,0},{0,0,1,0,0},{0,0,0,0,0},{0,0,1,0,0},{0,0,0,0,0}});
    }
}