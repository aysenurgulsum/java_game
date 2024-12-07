/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StartScreen extends JFrame {

    private JButton startButton; // Oyunu başlat butonu
    private JTextArea scoreArea; // Geçmiş puanların listeleneceği alan
    private DatabaseManager dbManager; // Veritabanı bağlantısı için

    public StartScreen() {
        // Başlangıç ekranı özellikleri
        setTitle("Oyun Başlangıç Ekranı");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ortaya yerleştir
        setLayout(new BorderLayout());

        // Veritabanı bağlantısını oluştur
        dbManager = new DatabaseManager();

        // Başlat butonu
        startButton = new JButton("Oyunu Başlat");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Oyun ekranını aç ve bu ekranı kapat
                Game gameFrame = new Game();
                gameFrame.setVisible(true);
                dispose();
                
            }
        });

        // Geçmiş skorlar alanı
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // JScrollPane ile sar (scroll özelliği eklemek için)
        JScrollPane scrollPane = new JScrollPane(scoreArea);
        // Veritabanından skorları getir
        List<String> scores = dbManager.getAllScores();
        scoreArea.setText("Geçmiş Skorlar:\n");
        for (String score : scores) {
            scoreArea.append(score + "\n");
        }

        // Bileşenleri yerleştir
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }
}
