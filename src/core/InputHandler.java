package core;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// import javax.swing.text.html.parser.Element;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import elementos.*;
import elementos.liquido.Agua;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {

    private Position pointerPos;
    private boolean mouseClicked;
    private boolean mousePressed;

    HashMap<Integer, Elemento> typeMap = new HashMap<>();

    // private boolean[] currentlyPressed;
    // private boolean[] pressed;
    private Elemento current;
    private Boolean reset = false;

    InputHandler() {
        typeMap.put((int) 'A', new Ar());
        typeMap.put((int) 'S', new Areia());
        typeMap.put((int) 'D', new Pedra());
        typeMap.put((int) 'W', new Agua());
        typeMap.put((int) 'Q', new Fogo());

        mouseClicked = false;
        pointerPos = new Position(0, 0);
        current = new Areia();
    }

    private void setType(int key) {
        Elemento temp = typeMap.get(key);
        if (temp != null) {
            current = temp;
        }
    }

    public void setType(Elemento e) {
        current = e;
    }

    public Elemento getType() {
        return current;
    }

    public boolean getReset() {
        return reset;
    }

    public void setReset(boolean r) {
        reset = r;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 'C') {
            reset = true;
            return;
        }
        setType(key);
    }

    public void keyTyped(KeyEvent e) {

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
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
