/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final String DB_URL = "jdbc:sqlite:game_scores.db";

    // JDBC sürücüsünü yüklemek için static blok
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DatabaseManager() {
        // Veritabanını oluştur ve tabloyu hazırla
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Scores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "score INTEGER NOT NULL, "
                + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // Tabloyu oluştur
            System.out.println("Tablo başarıyla oluşturuldu veya zaten mevcut.");
        } catch (SQLException e) {
            System.out.println("Tablo oluşturulurken hata oluştu.");
            e.printStackTrace();
        }
    }

    public void saveScore(int score) {
        String sql = "INSERT INTO scores (score) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.executeUpdate();
            System.out.println("Skor veritabanına başarıyla kaydedildi: " + score);
        } catch (SQLException e) {
            System.out.println("Skor kaydedilemedi!");
            e.printStackTrace();
        }
    }

    public List<String> getAllScores() {
        List<String> scores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Scores ORDER BY timestamp DESC")) {
            while (rs.next()) {
                int score = rs.getInt("score");
                String timestamp = rs.getString("timestamp");
                scores.add("Puan: " + score + " - Tarih: " + timestamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
