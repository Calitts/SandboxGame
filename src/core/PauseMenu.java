//Menu de pause
package core;

import java.awt.*;
import javax.swing.*;

public class PauseMenu extends JPanel {
    
    public PauseMenu(GamePanel panel){

        setLayout(new GridBagLayout());
        setBackground(new Color(0, 0, 0, 180));
        setOpaque(true);
        setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);


        JLabel title = new JLabel("PAUSE");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        add(title, gbc);


        gbc.gridy++;
        JSlider scaleSlider = new JSlider(1, 10, panel.scale);
        scaleSlider.addChangeListener(e -> {
            panel.scale = scaleSlider.getValue();
        });
        add(new JLabel("Tamanho do Pixel"), gbc);
        gbc.gridy++;
        add(scaleSlider, gbc);


        gbc.gridy++;
        JSlider upsSlider = new JSlider(30, 300, panel.UPS);
        upsSlider.addChangeListener(e -> {
            panel.UPS = upsSlider.getValue();
        });
        add(new JLabel("Velocidade do Jogo"), gbc);
        gbc.gridy++;
        add(upsSlider, gbc);


        gbc.gridy++;
        JSlider tileSlider = new JSlider(1, 4, panel.originalTileSize);
        tileSlider.addChangeListener(e -> {
            panel.originalTileSize = tileSlider.getValue();
        });
        add(new JLabel("Quantidade de Pixels"), gbc);
        gbc.gridy++;
        add(tileSlider, gbc);


        gbc.gridy++;
        JButton exit = new JButton("Sair do Jogo");
        exit.addActionListener(e -> System.exit(0));
        add(exit, gbc);
        
    }
}