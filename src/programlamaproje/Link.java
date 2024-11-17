/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Link {
    private Character character1, character2;
    private final int maxDistance = 100;

    public Link(Character c1, Character c2) {
        this.character1 = c1;
        this.character2 = c2;
    }

    public void update() {
        int dx = character1.getX() - character2.getX();
        int dy = character1.getY() - character2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > maxDistance) {
            // İki karakter arasındaki mesafe sınırı aşarsa, birbirini çeken bir kuvvet uygulayın.
            int pullX = (int)(dx * 0.1); // Çekme gücünü ölçeklendirin
            int pullY = (int)(dy * 0.1);

            character1.setPosition(character1.getX() - pullX, character1.getY() - pullY);
            character2.setPosition(character2.getX() + pullX, character2.getY() + pullY);
        }
    }
}

