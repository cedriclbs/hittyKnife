package gui;

import config.RessourcesPaths;
import config.ShopItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;



/**
 * Cette classe représente un menu du magasin dans l'interface utilisateur graphique.
 * Chaque onglet affiche des articles de différentes catégories tels que des couteaux, des fonds d'écran, de la musique, etc.
 * Les utilisateurs peuvent ajouter des articles à leur panier à partir de cet onglet.
 */

public class ShopTab {

    private ShopMenu shopMenu;


    /**
     * Constructeur de la classe ShopTab.
     *
     * @param tabbedPane Le JTabbedPane où l'onglet sera ajouté.
     * @param title Le titre de l'onglet.
     * @param imagePath Le chemin de l'image associée à l'onglet.
     * @param iconPath Le chemin de l'icône associée à l'onglet.
     * @param category La catégorie de l'onglet.
     * @param shopMenu L'instance de ShopMenu associée à cet onglet.
     */
    public ShopTab(JTabbedPane tabbedPane, String title, String imagePath, String iconPath, String category, ShopMenu shopMenu) {
        this.shopMenu = shopMenu;
        JPanel panel = createPanel(imagePath, category);
        tabbedPane.addTab(title, panel);
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(RessourcesPaths.tabPath + iconPath);
            tabbedPane.setIconAt(tabbedPane.indexOfComponent(panel), icon);
        }
    }



    /**
     * Crée le contenu du panneau principal de l'onglet en fonction de la catégorie spécifiée.
     *
     * @param path Le chemin de l'image ou du répertoire associé à la catégorie.
     * @param category La catégorie de l'onglet.
     * @return Le JPanel contenant le contenu principal de l'onglet.
     */
    private JPanel createPanel(String path, String category) {
        // Création du panel pour chaque onglet avec une image de fond personnalisée

        BackgroundPanel tabPanel = new BackgroundPanel(RessourcesPaths.backgroundPath + "bgShop.png");
        tabPanel.setLayout(new BorderLayout());

        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        if (category.equals("background")){
            mainMenuPanel.setBorder(new EmptyBorder(300, 200, 300, 200));
        } else if (category.equals("cart")) {
            mainMenuPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
        } else {
            mainMenuPanel.setBorder(new EmptyBorder(300, 300, 300, 300));
        }
        mainMenuPanel.setOpaque(false);


        switch (category) {


            case "welcome":
                JPanel temp = new JPanel();
                temp.setOpaque(false);

                JPanel welcomePanel = new JPanel();

                JLabel welcomeLabel = new JLabel("<html>Bienvenue dans le Shop du jeu.<br>Ajoutez au panier des articles avec la monnaie disponible, et sauvegardez.<br>Vous pouvez également retourner au menu sans effectuer d'achat.</html>");
                welcomePanel.add(welcomeLabel);

//                JButton retourAuMenuButton = new JButton("Retour au menu");
//                retourAuMenuButton.addActionListener(e -> shopMenu.showMenu());
//                ImageIcon retourAuMenuIcon = new ImageIcon(RessourcesPaths.buttonPath + "retourMenu.png");
//                retourAuMenuButton.setIcon(retourAuMenuIcon);
//                welcomePanel.add(retourAuMenuButton);


                mainMenuPanel.add(temp);
                mainMenuPanel.add(welcomePanel);

                break;


            case "couteaux":
                addItemToPanel(mainMenuPanel, path + "knife.png", 15, "Sword 1");
                addItemToPanel(mainMenuPanel, path + "knife#2.png", 20,"Sword 2");
                addItemToPanel(mainMenuPanel, path + "knife#3.png", 25,"Sword 3");
                break;


            case "background":
                addItemToPanel(mainMenuPanel, path + "Background_MainMenu.png", 20, "Background 1");
                addItemToPanel(mainMenuPanel, path + "Background_Solo.png", 20, "Background 2");
                addItemToPanel(mainMenuPanel, path + "bgForet.png", 25, "Background 3");
                break;


            case "music":
                addItemToPanel(mainMenuPanel, path + "music.png", 30,"Music 1");
                addItemToPanel(mainMenuPanel, path + "music.png", 30, "Music 2");
                addItemToPanel(mainMenuPanel, path + "music.png", 30,"Music 2");
                break;


            case "cart":
                JPanel cartPanel = shopMenu.cart.displayCart(this.shopMenu, shopMenu.cart.getCart(), this, mainMenuPanel, "Panier");
                break;

        }
        tabPanel.add(mainMenuPanel, BorderLayout.CENTER);
        return tabPanel;
    }






    /**
     * Ajoute un article à un JPanel en tant qu'élément de liste avec un bouton associé pour le supprimer.
     *
     * @param panel Le JPanel où l'article sera ajouté.
     * @param imagePath Le chemin de l'image de l'article.
     * @param itemName Le nom de l'article.
     */
    private void addItemToPanel(JPanel panel, String imagePath, int articlePrice, String itemName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);

        //Image
        ImageIcon icon = new ImageIcon(imagePath);
        if (itemName.contains("Sword") || itemName.contains("Music")){
            Image resizedImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        } else if (itemName.contains("Background")){
            Image resizedImage = icon.getImage().getScaledInstance(1920/5, 1080/5, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        }

        // Image to Bouton
        JButton itemButton = new JButton();
        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);

        //Caption de l'article
        JPanel itemCaption = makeItemCaption(articlePrice, itemName);


        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemCaption, BorderLayout.SOUTH);
        panel.add(itemPanel);

        shopMenu.configureButton(itemButton, e -> {
            shopMenu.cart.addArticle(new ShopItem(itemName, articlePrice, imagePath));
            shopMenu.saveB = false;
            refreshCartTab();
        });

    }


    /**
     * Crée le composant de légende pour un article avec son nom, son prix, et l'image de la pièce.
     *
     * @param articlePrice Le prix de l'article.
     * @param itemName Le nom de l'article.
     * @return Le JPanel contenant le composant de légende.
     */

    private static JPanel makeItemCaption(int articlePrice, String itemName) {
        JPanel itemCaption = new JPanel(new BorderLayout());

        JLabel itemNameLabel = new JLabel("  " + itemName);
        itemNameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel itemPriceLabel = new JLabel(String.valueOf(articlePrice));
        itemPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon coinIcon = new ImageIcon(RessourcesPaths.iconPath + "coin.png");
        JLabel coinLabel = new JLabel(coinIcon);
        coinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        coinLabel.setOpaque(false);

        //JPanel pour priceLabel et coinLabel
        JPanel pricePanel = new JPanel(new BorderLayout());
        pricePanel.add(itemPriceLabel, BorderLayout.CENTER);
        pricePanel.add(coinLabel, BorderLayout.EAST);
        pricePanel.setOpaque(false);


        itemCaption.setFont(itemCaption.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));
        itemCaption.setBackground(new Color(255, 255, 255, 200));
        itemCaption.setOpaque(true);
        itemCaption.add(itemNameLabel, BorderLayout.WEST);
        itemCaption.add(pricePanel, BorderLayout.EAST);

        return itemCaption;
    }


    /**
     * Actualise l'onglet du panier du magasin.
     */
    void refreshCartTab() {
        int cartTabIndex = shopMenu.tabbedPane.indexOfTab("Panier");
        if (cartTabIndex != -1) {
            JPanel cartPanel = createPanel(null, "cart");
            shopMenu.tabbedPane.setComponentAt(cartTabIndex, cartPanel);
        }
    }



}
