//Menu Inicial
package core;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class StartMenu extends JPanel{
    
    private Image gifImage;

    public StartMenu(CardLayout layout, JPanel container){
        
        URL imageUrl = getClass().getResource("/floresta.gif");

        if (imageUrl != null) {
            this.gifImage = new ImageIcon(imageUrl).getImage();
        }

        setLayout(new GridBagLayout());
        setOpaque(false);
        

        PixelTitle title = new PixelTitle();


        JButton start = new JButton("Entrar no Jogo");
        JButton exit = new JButton("Sair");

        start.addActionListener(e -> layout.show(container, "GAME"));
        exit.addActionListener(e -> System.exit(0));

        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(start, gbc);

        gbc.gridy = 2;
        add(exit, gbc);
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

    private void estilizarBotao(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 50));
    }
}
