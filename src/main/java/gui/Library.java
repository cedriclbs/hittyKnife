package gui;

import com.fasterxml.jackson.databind.JsonNode;
import config.Game;
import config.RessourcesPaths;
import config.ShopItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;




/**
 * Cette classe représente une bibliothèque dans l'interface graphique du jeu.
 * Elle utilise un panneau avec une image de fond pour personnaliser son apparence.
 */
public class Library extends JPanel {

    private String username;
    private List<ShopItem> libraryItems;



    /**
     * Constructeur de la classe Library.
     *
     * @param username Le nom d'utilisateur pour lequel la bibliothèque est créée.
     */
    public Library(String username) {
        initialize(username);
        charger();
        afficher();
    }


    /**
     * Initialise la bibliothèque avec le nom d'utilisateur spécifié.
     *
     * @param username Le nom d'utilisateur pour lequel la bibliothèque est initialisée.
     */
    public void initialize(String username) {
        this.username = username;
        this.libraryItems = new ArrayList<>();
    }



    /**
     * Charge les éléments de la bibliothèque à partir du fichier JSON correspondant à l'utilisateur.
     */
    private void charger () {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode readFile = mapper.readTree(new File("src/main/saves/sauvegarde_"+ this.username + ".json"));

            JsonNode libraryNode = readFile.get("library");
            if (libraryNode != null && libraryNode.isArray()) {
                for (JsonNode itemNode : libraryNode) {
                    libraryItems.add(new ShopItem(itemNode.get("articleName").asText(), itemNode.get("articlePrice").asInt(), itemNode.get("articleImagePath").asText()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


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

        for (ShopItem item : libraryItems) {

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



