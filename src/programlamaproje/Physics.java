/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programlamaproje;

/**
 *
 * @author Aysenur
 */
public class Physics {
    public static void applyGravity(Character character) {
        // Yerçekimi etkisi (örnek olarak her güncellemede y değerini artırıyoruz)
        character.setPosition(character.getX(), character.getY() + 1);
    }

    public static void handleCollision(Character character) {
        // Yere temas eden karakterin daha fazla düşmesini önlüyor
        if (character.getY() >= 500) { // Zeminin Y eksenindeki sınırı
            character.setPosition(character.getX(), 500); // Zeminde sabit kal
        }
    }
}

