//Menu Inicial
package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartMenu extends JDialog{

    public StartMenu(JFrame parent, GamePanel panel){

        super(parent, "SandBox Game", true);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sandbox Game", SwingConstants.CENTER);
        title.setFont(new Font("Arial", font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        JPanel buttons = new Jpanel();
        JButton start = new Jbutton(Entrar no Jogo);
        JButton exit = new JButton("Sair do Jogo");
    
        start.addActionListener((ActionEvent e) -> {dispose();
            panel.requestFocus();
        });

        exit.addActionListener(e->System.exit(0));

        buttons.add(start);
        buttons.add(exit);

        add(buttons, BorderLayout.CENTER);

        setSize(300, 200);
        setLocationRelativeTo(parent);
    }
}