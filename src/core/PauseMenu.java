//Menu de pause
package core;

import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JDialog {
    
    public PauseMenu(JFrame parent, GamePanel panel){

        super(parent, "Pause", false);
        setLayout(new GridLayout(5, 1));

        JButton exit = new JButton("Sair do jogo");
        exit.addActionListener(e-> System.exit(0));

        JSlider scaleSlider = new JSlider(1, 10, panel.scale);
        scaleSlider.setBorder(BorderFactory.createTitledBorder("Tamanho do Pixel"));
        scaleSlider.addChangeListener(e-> panel.scale = scaleSlider.getValue());

        JSlider upsSlider = new JSlider(10, 120, panel.UPS);
        upsSlider.setBorder(BorderFactory.createTitledBorder("Velocidade do Jogo (UPS)"));
        upsSlider.addChangeListener(e -> panel.UPS = upsSlider.getValue());

        JSlider tileSlider = new JSlider(1, 5, panel.originalTileSize);
        tileSlider.setBorder(BorderFactory.createTitledBorder("Quantidade de Pixels"));
        tileSlider.addChangeListener(e -> panel.originalTileSize = tileSlider.getValue());

        add(scaleSlider);
        add(upsSlider);
        add(tileSlider);
        add(exit);

        pack();
        setLocationRelativeTo(parent);
    }
}