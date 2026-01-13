package core;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.text.html.parser.Element;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.HashMap;

import element.*;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {

    private Position pointerPos;
    private boolean mouseClicked;
    private boolean mousePressed;

    HashMap<Integer, Elemento> typeMap = new HashMap<>();

    // private boolean[] currentlyPressed;
    // private boolean[] pressed;
    private Elemento current;

    InputHandler() {
        typeMap.put((int)'A', new Ar());
        typeMap.put((int)'S', new Areia());
        typeMap.put((int)'D', new Pedra());


        mouseClicked = false;
        pointerPos = new Position(0, 0);
        current = new Areia();
    }

    private void setType(int key) {
        current = typeMap.get(key);
    }

    public Elemento getType() {
        return current;
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
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
