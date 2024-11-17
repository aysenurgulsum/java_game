/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */
import java.awt.*;
import java.awt.event.KeyEvent;

class Character {
    private int x, y, speed, yVelocity;
    private boolean up, down, left, right, onGround;
    private final int width = 50, height = 50;
    private final Color color;

    public Character(int startX, int startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.speed = 5;
        this.color = color;
        this.yVelocity = 0;
        this.onGround = false;
    }
    
    public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    }

    public void update(int screenWidth, int screenHeight) {
        if (left) x = Math.max(0, x - speed); // Sol sınıra dikkat
        if (right) x = Math.min(screenWidth - width, x + speed); // Sağ sınıra dikkat
        if (up && onGround) {
            yVelocity = -15;
            onGround = false;
        }
        y += yVelocity;
        yVelocity += 1; // Yerçekimi etkisi

        // Zemine çarpışma kontrolü
        if (y >= screenHeight - height - 100) { // 100 piksel yüksekliğinde bir zemin ekledik
            y = screenHeight - height - 100;
            yVelocity = 0;
            onGround = true;
        }
    }
    
    public void keyPressed(KeyEvent e, String characterName) {
        if (characterName.equals("Character1")) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> up = true;
                case KeyEvent.VK_S -> down = true;
                case KeyEvent.VK_A -> left = true;
                case KeyEvent.VK_D -> right = true;
            }
        } else if (characterName.equals("Character2")) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> up = true;
                case KeyEvent.VK_DOWN -> down = true;
                case KeyEvent.VK_LEFT -> left = true;
                case KeyEvent.VK_RIGHT -> right = true;
            }
        }
    }


    public void keyReleased(KeyEvent e, String characterName) {
        if (characterName.equals("Character1")) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> up = false;
                case KeyEvent.VK_S -> down = false;
                case KeyEvent.VK_A -> left = false;
                case KeyEvent.VK_D -> right = false;
            }
        } else if (characterName.equals("Character2")) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> up = false;
                case KeyEvent.VK_DOWN -> down = false;
                case KeyEvent.VK_LEFT -> left = false;
                case KeyEvent.VK_RIGHT -> right = false;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

