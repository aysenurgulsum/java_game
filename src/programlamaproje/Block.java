/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */

import java.awt.Color;
import java.awt.Graphics;


public class Block {
    private int x, y, points;        // Blok pozisyonu
    private int width, height; // Blok boyutları
    private boolean containsPoint; // Puan öğesi içeriyor mu?
    private final Color color;
    private boolean collected = false;

    // Constructor
    public Block(int x, int y, int width, int height, Color color, int points) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.containsPoint = true; // Blok başta bir puan içerir.
        this.color = color;
        this.points = points;
    }

    // Blok çizimi
    public void render(Graphics g) {
        // Blok için gri bir dikdörtgen
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Eğer blok bir puan içeriyorsa, sarı bir daire çizelim
        if (!collected) {
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(points), x + width / 2 - 5, y - 10); // Puanı bloğun üstünde göster
        }
    }
    
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    // Puan toplandığında çağrılır
    public void collectPoint() {
        this.containsPoint = false; // Puan toplandıktan sonra devre dışı
    }

    // Blok bir puan içeriyor mu?
    public boolean isPointCollected() {
        return !containsPoint;
    }

    // Karakter blok üzerine zıpladı mı? Kontrol metodu
    public boolean isOnTopOf(Character character) {
        return character.getY() + character.getHeight() >= y // Karakterin altı blok seviyesinde mi?
                && character.getY() + character.getHeight() <= y + height
                && character.getX() + character.getWidth() > x
                && character.getX() < x + width;
    }
    
    // Getter metodları
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getPoints() { return points; }
    public boolean isCollected() { return collected; }
}

