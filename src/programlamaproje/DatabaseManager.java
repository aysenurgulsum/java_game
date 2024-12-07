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

    public static void main(String[] args) {
        // Veritabanı yöneticisini başlat
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTable();
        dbManager.testDatabaseConnection();
        // Örnek bir skor kaydı ekle
        System.out.println("Skor kaydediliyor...");
        dbManager.saveScore(100, 200);

        // Tüm skorları al ve yazdır
        System.out.println("Skorlar:");
        for (String score : dbManager.getAllScores()) {
            System.out.println(score);
        }
    }
public void testDatabaseConnection() {
    String sql = "SELECT 1";
    try (Connection conn = DriverManager.getConnection(DB_URL); 
         Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            System.out.println("Veritabanı bağlantısı başarılı!");
        }
    } catch (SQLException e) {
        System.out.println("Veritabanı bağlantısı sağlanamadı!");
        e.printStackTrace();
    }
}

    // Tabloyu silme ve yeniden oluşturma
    private void recreateTable() {
        String dropSql = "DROP TABLE IF EXISTS scores"; // Tabloyu sil
        String createSql = """
        CREATE TABLE IF NOT EXISTS scores (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            character1_score INTEGER,
            character2_score INTEGER,
            play_date TEXT
        )
    """; // Yeni tabloyu oluşturma

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            stmt.execute(dropSql);  // Tabloyu sil
            stmt.execute(createSql);  // Yeni tabloyu oluştur
            System.out.println("Tablo yeniden oluşturuldu.");
        } catch (SQLException e) {
            System.out.println("Tablo oluşturulamadı!");
            e.printStackTrace();
        }
    }

    public DatabaseManager() {
        createTable();
    }

    // Tablonun oluşturulması
    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS scores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                character1_score INTEGER,
                character2_score INTEGER,
                play_date TEXT
            )
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tablo oluşturuldu veya zaten mevcut.");
        } catch (SQLException e) {
            System.out.println("Tablo oluşturulamadı!");
            e.printStackTrace();
        }
    }

    // Skoru kaydetme
    public void saveScore(int character1Score, int character2Score) {
        String sql = "INSERT INTO scores (character1_score, character2_score, play_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, character1Score);
            pstmt.setInt(2, character2Score);
            pstmt.setString(3, new java.util.Date().toString()); // Tarihi ekler
            pstmt.executeUpdate();

            System.out.println("Skorlar veritabanına kaydedildi: Character1 = " + character1Score + ", Character2 = " + character2Score);
        } catch (SQLException e) {
            System.out.println("Skor kaydedilemedi!");
            e.printStackTrace();
        }
    }

    public List<String> getAllScores() {
        String sql = "SELECT * FROM scores";
        List<String> scores = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int character1Score = rs.getInt("character1_score");
                int character2Score = rs.getInt("character2_score");
                String playDate = rs.getString("play_date");

                // Skoru metin olarak listeye ekle
                scores.add("Character1: " + character1Score + ", Character2: " + character2Score + ", Date: " + playDate);
            }
        } catch (SQLException e) {
            System.out.println("Skorlar getirilemedi!");
            e.printStackTrace();
        }

        return scores;
    }
}
