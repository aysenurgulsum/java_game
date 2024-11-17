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


public class GamePanel extends JPanel {
    private Character character1, character2;
    private Link link;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(character1.getX(), character1.getY(), 20, 20); // Karakter 1
        g.drawRect(character2.getX(), character2.getY(), 20, 20); // Karakter 2
        
        // Karakterler arasındaki bağlantıyı çiz
        g.drawLine(character1.getX() + 10, character1.getY() + 10,
                   character2.getX() + 10, character2.getY() + 10);
    }
}

