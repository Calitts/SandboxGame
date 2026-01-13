//Menu Inicial
package core;

import java.awt.*;
import javax.swing.*;

public class StartMenu extends JPanel{

    public StartMenu(CardLayout layout, JPanel container){

        setLayout(new GridBagLayout());
        setBackground(new Color(0, 0, 0, 200));
        setOpaque(true);

        JLabel title = new JLabel("Sandbox Game");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JButton start = new JButton("Entrar no Jogo");
        JButton exit = new JButton("Sair do Jogo");

        start.addActionListener(e -> {
            layout.show(container, "GAME");
            container.requestFocusInWindow();
        });

        exit.addActionListener(e -> System.exit(0));

        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(start, gbc);

        gbc.gridy = 2;
        add(exit, gbc);
    }
}