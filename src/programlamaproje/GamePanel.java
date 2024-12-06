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
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
    private Character character1, character2;
    private Link link;
    private List<Block> blocks;
   
    private void checkBlockCollisions() {
        for (Block block : blocks) {
            if (block.isOnTopOf(character1) && !block.isPointCollected()) {
                block.collectPoint(); // Puan öğesini topla
                character1.addScore(10); // Karaktere 10 puan ekle
            }
            if (block.isOnTopOf(character2) && !block.isPointCollected()) {
                block.collectPoint();
                character2.addScore(10); // Diğer karaktere de puan ekle
            }
        }
    }

    
    private void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Character 1 Score: " + character1.getScore(), 10, 20);
        g.drawString("Character 2 Score: " + character2.getScore(), 10, 40);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScores(g); // Puanları ekrana yazdır
        g.drawRect(character1.getX(), character1.getY(), 20, 20); // Karakter 1
        g.drawRect(character2.getX(), character2.getY(), 20, 20); // Karakter 2
        
        // Karakterler arasındaki bağlantıyı çiz
        g.drawLine(character1.getX() + 10, character1.getY() + 10,
                   character2.getX() + 10, character2.getY() + 10);
        // Blokları çiz
        for (Block block : blocks) {
            block.render(g);
        }
    }
    @Override
    public void run() {
        while (true) {
            // Oyun döngüsü
            checkBlockCollisions(); // Çarpışmaları kontrol et
            repaint(); // Grafik güncellemesi

            try {
                Thread.sleep(16); // 60 FPS için yaklaşık 16ms bekle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

