package gui;

import config.Game;
import config.GameObserver;
import config.RessourcesPaths;
import config.ShopItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Cette classe représente une bibliothèque dans l'interface graphique du jeu.
 * Elle utilise un panneau avec une image de fond pour personnaliser son apparence.
 */
public class Library extends JPanel  {
    private List<ShopItem> inventaire;
    private Game game;

    public Library(Game game) {
        this.game = game;
        this.inventaire = game.getInventaire();
        afficher();
    }


    public void updateInventaire() {
        this.inventaire = game.getInventaire(); // Mettre à jour l'inventaire
        afficher(); // Mettre à jour l'affichage de l'inventaire
    }



    /**
     * Affiche les éléments de la bibliothèque dans un panneau graphique avec une image de fond.
     */
    private void afficher() {

        BackgroundPanel backgroundPanel = new BackgroundPanel(RessourcesPaths.backgroundPath + "bgInventaire.gif");
        setLayout(new BorderLayout());

        JPanel itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        itemsPanel.setBorder(new EmptyBorder(300, 200, 300, 200));
        itemsPanel.setOpaque(false);

        for (ShopItem item : inventaire) {
            System.out.println(item);
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setOpaque(false);


            ImageIcon icon = new ImageIcon(item.getArticleImagePath());
            if (item.getArticleImagePath().contains("knife") || item.getArticleImagePath().contains("music")){
                Image resizedImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImage);
            } else if (item.getArticleImagePath().contains("background")){
                Image resizedImage = icon.getImage().getScaledInstance(1920/5, 1080/5, Image.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImage);
            }

            JButton itemButton = new JButton();
            itemButton.setIcon(icon);
            itemButton.setBorderPainted(true);
            itemButton.setContentAreaFilled(false);
            itemButton.setFocusPainted(false);

            itemPanel.add(itemButton, BorderLayout.CENTER);
            itemsPanel.add(itemPanel);


            itemButton.addActionListener(e -> {
                if (item.getArticleImagePath().contains("music")) {
                    // TODO : Changer de musique
                } else if (item.getArticleImagePath().contains("background")) {
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    mainFrame.getGameView().updateBackgroundImage(item.getArticleImagePath());
                }
                else {
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    mainFrame.getGameView().updateKnifeImage(item.getArticleImagePath());
                }
            });
        }

        backgroundPanel.add(itemsPanel, BorderLayout.CENTER);
        add(backgroundPanel);

    }


}



