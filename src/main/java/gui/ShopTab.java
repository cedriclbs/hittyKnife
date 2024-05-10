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

        BackgroundPanel tabPanel = new BackgroundPanel(RessourcesPaths.backgroundPath + "bgShop.gif");
        tabPanel.setLayout(new BorderLayout());

        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        if (category.equals("background")){
            mainMenuPanel.setBorder(new EmptyBorder(150, 150, 150, 150));
        } else if (category.equals("cart")) {
            mainMenuPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
        } else {
            mainMenuPanel.setBorder(new EmptyBorder(200, 200, 200, 200));
        }
        mainMenuPanel.setOpaque(false);


        switch (category) {


            case "welcome":
                JPanel temp = new JPanel();
                temp.setOpaque(false);

                JPanel welcomePanel = new RoundedPanel(100, 100, false);

                JLabel welcomeLabel = new JLabel("<html>Bienvenue dans le Shop du jeu.<br>Ajoutez au panier des articles avec la monnaie disponible, et sauvegardez.<br>Vous pouvez également retourner au menu sans effectuer d'achat.</html>");
                welcomePanel.add(welcomeLabel);


                mainMenuPanel.add(temp);
                mainMenuPanel.add(welcomePanel);

                break;


            case "couteaux":
                addItemToPanel(mainMenuPanel, path + "knife.png", 15, "Sword 1");
                addItemToPanel(mainMenuPanel, path + "knife#2.png", 20,"Sword 2");
                addItemToPanel(mainMenuPanel, path + "knife#3.png", 25,"Sword 3");
                addItemToPanel(mainMenuPanel, path + "knife#4.png", 50,"Sword 4");
                addItemToPanel(mainMenuPanel, path + "knife#5.png", 75,"Sword 5");
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
        // Création d'un panel arrondi pour l'article
        RoundedPanel itemRoundedPanel = new RoundedPanel(20, 20, true); // Ajustez les dimensions des coins arrondis selon vos préférences
        itemRoundedPanel.setLayout(new BorderLayout());
        itemRoundedPanel.setOpaque(false);
        itemRoundedPanel.setBorder(null);
        itemRoundedPanel.setFocusable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        //Image
        ImageIcon icon = new ImageIcon(imagePath);
        if (itemName.contains("Sword") || itemName.contains("Music")){
            Image resizedImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        } else if (itemName.contains("Background")){
            Image resizedImage = icon.getImage().getScaledInstance(screenWidth/10, screenHeight/10, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        }



        // Création du bouton pour l'article
        RoundedButton itemButton = new RoundedButton("");
        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);

        RoundedPanel itemCaption = makeItemCaption(articlePrice, itemName);

        itemRoundedPanel.add(itemButton);
        itemRoundedPanel.add(itemCaption, BorderLayout.SOUTH);


        panel.add(itemRoundedPanel);

        shopMenu.configureButton(itemButton, e -> {
            shopMenu.cart.addArticle(new ShopItem(itemName, articlePrice, imagePath));
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

    private static RoundedPanel makeItemCaption(int articlePrice, String itemName) {
        RoundedPanel itemCaption = new RoundedPanel(20,20, false);
        itemCaption.setLayout(new BorderLayout());

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
        itemCaption.setOpaque(false);
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
