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
import java.util.List; // List'i içe aktar
import java.awt.Color;

class Character {
    private int x, y, speed, yVelocity;
    private boolean up, down, left, right;
    private final int width = 60, height = 60;
    private final Color color;
    private int score; // Karakterin mevcut puanı
    private int velocityY = 0; // Yer çekimi için
    private final int gravity = 1; // Yer çekimi kuvveti
    private final int jumpStrength = -20; // Zıplama kuvveti
    private boolean onGround = false; // Karakter zeminde mi?

    public Character(int startX, int startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.speed = 5;
        this.color = color;
        this.yVelocity = 0;
        this.onGround = false;
        this.score = 0;
    }
    
    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void addScore(int points) {
        this.score += points; // Puan ekle
    }

    public int getScore() {
        return score;
    }    

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int screenWidth, int screenHeight, List<Block> blocks) {
    // Zemin yüksekliği
    int groundHeight = 700; 
    int groundOffset = 5; // Karakterin zeminden ne kadar yukarıda duracağını belirler

    // Sağ-sol hareket
    if (left) {
        x -= speed;
        if (x < 0) x = 0; // Ekran dışına çıkmayı engelle
    }
    if (right) {
        x += speed;
        if (x + width > screenWidth) x = screenWidth - width; // Ekran dışına çıkmayı engelle
    }

    // Yer çekimini uygula
    if (!onGround) {
        velocityY += gravity;
    }

    // Yukarı-aşağı hareket
    y += velocityY;

    // Zeminle çarpışma kontrolü
    onGround = false; // Varsayılan olarak havada
    if (y + height >= groundHeight - groundOffset) { // Zemin yüksekliği
        y = groundHeight - height - groundOffset; // Zeminden biraz yukarıda durmasını sağla
        velocityY = 0;
        onGround = true;
    }

    // Bloklarla çarpışma kontrolü
       for (Block block : blocks) {
        if (y + height >= block.getY() && y + height <= block.getY() + 10 // Karakterin alt kısmı bloğa değiyor mu?
                && x + width > block.getX() && x < block.getX() + block.getWidth()) {
            y = block.getY() - height; // Karakteri bloğun üstüne yerleştir
            velocityY = 0;
            onGround = true;
            
            // Puanı topla ve blok "toplandı" olarak işaretle
            if (!block.isCollected()) {
                score += block.getPoints(); // Puan ekle
                block.setCollected(true);   // Bloğu "toplandı" olarak işaretle
            }
        }
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
        
        if (characterName.equals("Character1") && e.getKeyCode() == KeyEvent.VK_W) {
            if (onGround) {
                velocityY = jumpStrength; // Zıpla
            }
        } else if (characterName.equals("Character2") && e.getKeyCode() == KeyEvent.VK_UP) {
            if (onGround) {
                velocityY = jumpStrength; // Zıpla
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


