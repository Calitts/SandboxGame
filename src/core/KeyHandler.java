package core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class KeyHandler extends KeyAdapter { 
    private JFrame frame;
    private GamePanel panel;
    private PauseMenu pauseMenu;

    public KeyHandler(JFrame frame, GamePanel panel){
        this.frame = frame;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (pauseMenu == null || !pauseMenu.isVisible()) {
                pauseMenu.setVisible(true);
            }
        }
    }
}