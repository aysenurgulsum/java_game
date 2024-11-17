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
import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements KeyListener {
    private final Character character1, character2;
    private final int maxLinkDistance = 200;
    
    public Game() {
        character1 = new Character(100, 480, Color.MAGENTA);
        character2 = new Character(200, 480, Color.PINK);

        JFrame frame = new JFrame("2D Yarış Oyunu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran yap
        frame.setUndecorated(false); // Kenarları kaldır
        frame.add(this);
        frame.setVisible(true);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        
        
        Timer timer = new Timer(16, e -> {
            character1.update(getWidth(), getHeight());
            character2.update(getWidth(), getHeight());
            checkLinkDistance();
            repaint();
        });
        timer.start();
    }

    // Karakterler arasındaki mesafeyi kontrol et
    private void checkLinkDistance() {
        int dx = character2.getX() - character1.getX();
        int dy = character2.getY() - character1.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > maxLinkDistance) {
            double scale = maxLinkDistance / distance;
            int midX = (character1.getX() + character2.getX()) / 2;
            int midY = (character1.getY() + character2.getY()) / 2;

            character1.setPosition(midX - (int)(dx * scale / 2), midY - (int)(dy * scale / 2));
            character2.setPosition(midX + (int)(dx * scale / 2), midY + (int)(dy * scale / 2));
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(34, 139, 34));
        g.fillRect(0, 670, 1920, 250);
        
        character1.draw(g);
        character2.draw(g);
        g.setColor(Color.BLACK);
        g.drawLine(character1.getX() + 10, character1.getY() + 10,
                   character2.getX() + 10, character2.getY() + 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        character1.keyPressed(e, "Character1");
        character2.keyPressed(e, "Character2");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        character1.keyReleased(e, "Character1");
        character2.keyReleased(e, "Character2");
    }

    @Override
    public void keyTyped(KeyEvent e) { }
}


