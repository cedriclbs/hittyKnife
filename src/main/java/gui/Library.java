package gui;

import User.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Game;
import config.ShopItem;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Library extends JPanel {

    private Game game;
    private JPanel libraryPanel;
    List<ShopItem> libraryItems;

    public Library (Game game) {
        initialize(game);
        charger();
        afficher();
        add(libraryPanel, BorderLayout.SOUTH);
        //test
    }


    public void initialize(Game game) {
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/ressources/background/bgLibrary.png");
        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        this.game = game;

        libraryPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        libraryPanel.setOpaque(false);

        libraryItems = new ArrayList<>();

    }

    private void afficher() {
        for (ShopItem item : libraryItems) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setOpaque(false);

            ImageIcon icon = new ImageIcon(item.getArticleImagePath());
            JButton itemButton = new JButton();
            itemButton.setIcon(icon);
            itemButton.setBorderPainted(false);
            itemButton.setContentAreaFilled(false);
            itemButton.setFocusPainted(false);


            JLabel itemNameLabel = new JLabel(item.getArticleName());
            itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemNameLabel.setFont(itemNameLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));


            itemPanel.add(itemButton, BorderLayout.CENTER);
            itemPanel.add(itemNameLabel, BorderLayout.SOUTH);


            libraryPanel.add(itemPanel);

            itemButton.addActionListener(e -> {

            });

        }

    }





    private void charger () {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode readFile = mapper.readTree(new File("src/main/saves/sauvegarde_"+ game.getNomUtilisateur() + ".json"));

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








}
