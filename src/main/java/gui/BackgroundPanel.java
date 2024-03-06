package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe représente un panneau avec une image de fond dans l'interface graphique du jeu.
 * Elle permet d'afficher une image de fond pour personnaliser l'apparence de l'interface.
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Constructeur pour initialiser l'image de fond.
     *
     * @param fileName Le nom du fichier image à utiliser comme arrière-plan.
     */
    public BackgroundPanel(String fileName) {
        try {
            backgroundImage = new ImageIcon(fileName).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
