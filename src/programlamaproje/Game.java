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
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;

public class Game extends JPanel implements KeyListener {

    private final Character character1, character2;
    private final int maxLinkDistance = 200;
    private final ScheduledExecutorService executorService;
    private List<Block> blocks;
    private DatabaseManager dbManager;
    private int score;

    public Game() {

        dbManager = new DatabaseManager();

        character1 = new Character(100, 480, Color.MAGENTA);
        character2 = new Character(200, 480, Color.PINK);

        blocks = new ArrayList<>();
        blocks.add(new Block(700, 500, 150, 20, Color.BLUE, 10));

        blocks.add(new Block(400, 425, 150, 20, Color.BLUE, 20));
        blocks.add(new Block(1000, 425, 150, 20, Color.BLUE, 20));

        blocks.add(new Block(100, 525, 150, 20, Color.BLUE, 10));
        blocks.add(new Block(1300, 525, 150, 20, Color.BLUE, 10));

        blocks.add(new Block(110, 325, 150, 20, Color.BLUE, 30));
        blocks.add(new Block(1310, 325, 150, 20, Color.BLUE, 30));

        blocks.add(new Block(400, 225, 150, 20, Color.BLUE, 40));
        blocks.add(new Block(1000, 225, 150, 20, Color.BLUE, 40));

        blocks.add(new Block(700, 125, 150, 20, Color.BLUE, 50));

        JFrame frame = new JFrame("Java Proje");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Kapatma işlemini özelleştir
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran yap
        frame.setUndecorated(false); // Kenarları kaldır
        frame.add(this);
        frame.setVisible(true);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop(); // Pencere kapatılmadan önce stop() fonksiyonunu çağır
            }
        });

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::gameLoop, 0, 16, TimeUnit.MILLISECONDS);
        

    }
    // Oyunun sonunda veritabanına kaydet
    private void endGame() {
        System.out.println("Oyun sona erdi. Skorlar kaydediliyor...");

        // Oyuncu skorlarını kaydet
        dbManager.saveScore(character1.getScore(), character2.getScore());
        System.out.println("Character 1 Score: " + character1.getScore());
        System.out.println("Character 2 Score: " + character2.getScore());
        // Başlangıç ekranına dön
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);
    }

    private void gameLoop() {
        character1.update(getWidth(), getHeight(), blocks);
        character2.update(getWidth(), getHeight(), blocks);
        checkLinkDistance();
        //System.out.println("Character 1 Score: " + character1.getScore());
        //System.out.println("Character 2 Score: " + character2.getScore());
        repaint();
    }

    public void stop() {
        endGame(); // Skorları kaydet ve oyunu sonlandır
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        dispose(); // Pencereyi kapat
        System.exit(0);
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

            character1.setPosition(midX - (int) (dx * scale / 2), midY - (int) (dy * scale / 2));
            character2.setPosition(midX + (int) (dx * scale / 2), midY + (int) (dy * scale / 2));
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

        // Blokları çiz
        for (Block block : blocks) {
            block.render(g);
        }

        // Toplam puanı çiz
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score Purple: " + character1.getScore(), 20, 40);
        g.drawString("Score Pink: " + character2.getScore(), 20, 70);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        CompletableFuture.runAsync(() -> character1.keyPressed(e, "Character1"));
        CompletableFuture.runAsync(() -> character2.keyPressed(e, "Character2"));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CompletableFuture.runAsync(() -> character1.keyReleased(e, "Character1"));
        CompletableFuture.runAsync(() -> character2.keyReleased(e, "Character2"));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void dispose() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
    }

    private Connection connectToDatabase() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:game_scores.db");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveScoreToDatabase(int score) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = connectToDatabase()) {
                if (connection != null) {
                    String sql = "INSERT INTO scores (score) VALUES (?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setInt(1, score);
                        pstmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
