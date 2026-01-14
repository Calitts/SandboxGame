package core;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class StartMenu extends JPanel {
    
    private Image gifImage;
    private final Sound sound = new Sound(); 
    
   
    private final Color THEME_COLOR = new Color(255, 165, 0); 

    public StartMenu(CardLayout layout, JPanel container){

       
        sound.loop(0);
        
       
        URL imageUrl = getClass().getResource("/floresta.gif");
        if (imageUrl != null) {
            this.gifImage = new ImageIcon(imageUrl).getImage();
        }

        setLayout(new GridBagLayout());
        setOpaque(false);
        
        
        PixelTitle title = new PixelTitle(); 

        JButton start = createPixelButton("ENTRAR");
        JButton exit = createPixelButton("SAIR");

        start.addActionListener(e -> {
            sound.play(1); // Toca "button.wav"
            sound.stop(0); // Para a música da floresta ao entrar no jogo
            layout.show(container, "GAME"); 
        });

        exit.addActionListener(e -> {
            sound.play(1);
            try { Thread.sleep(200); } catch (Exception ex) {} 
            System.exit(0);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 40, 0); 
        add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0); 
        add(start, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0); 
        add(exit, gbc);
    }

    private JButton createPixelButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                boolean isHover = getModel().isRollover();

                if (isHover) {
                    g2.setColor(THEME_COLOR);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    setForeground(Color.BLACK);
                } else {
                    g2.setColor(new Color(0, 0, 0, 200)); // Fundo preto transparente
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    
                    g2.setColor(THEME_COLOR);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRect(1, 1, getWidth()-3, getHeight()-3);
                    
                    setForeground(THEME_COLOR);
                }
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Monospaced", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (gifImage != null) {
            g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(new Color(0, 0, 0, 100)); 
        g.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }
}