package core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputHandler implements MouseListener, MouseMotionListener {

    private Position pointerPos;
    private boolean mouseClicked;
    private boolean mousePressed;


    InputHandler() {
        mouseClicked = false;
        pointerPos = new Position(0, 0);
    }

    public void clearMouseClick() {
        mouseClicked = false;
    }

    Position getPosition() {
        return pointerPos;
    }

    boolean isMouseClicked() {
        return mouseClicked;
    }
    
    boolean isMousePressed() {
        return mousePressed;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        pointerPos = new Position(e.getPoint().getX(), e.getPoint().getY());
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        pointerPos = new Position(e.getPoint().getX(), e.getPoint().getY());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
   public void mouseExited(MouseEvent e) {}
    
}
