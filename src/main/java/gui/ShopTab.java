package gui;

import config.ShopItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;



/**
 * Cette classe représente un menu du magasin dans l'interface utilisateur graphique.
 * Chaque onglet affiche des articles de différentes catégories tels que des couteaux, des fonds d'écran, de la musique, etc.
 * Les utilisateurs peuvent ajouter des articles à leur panier à partir de cet onglet.
 */

public class ShopTab {

    private ShopMenu shopMenu;
    private JLabel cartTotalLabel;


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
            ImageIcon icon = new ImageIcon(shopMenu.tabPath + iconPath);
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

        BackgroundPanel tabPanel = new BackgroundPanel(shopMenu.backgroundPath + "bgShopMenu.png");
        tabPanel.setLayout(new BorderLayout());

        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        mainMenuPanel.setOpaque(false);
        mainMenuPanel.setBorder(new EmptyBorder(100, 100, 100, 100));


        switch (category) {


            case "welcome":
                JPanel temp = new JPanel();
                temp.setOpaque(false);

                JPanel welcomePanel = new JPanel();

                JLabel welcomeLabel = new JLabel("<html>Bienvenue dans le Shop du jeu.<br>Ajoutez au panier des articles avec la monnaie disponible, et sauvegardez.<br>Vous pouvez également retourner au menu sans effectuer d'achat.</html>");
                welcomePanel.add(welcomeLabel);

                JButton retourAuMenuButton = new JButton("Retour au menu");
                retourAuMenuButton.addActionListener(e -> shopMenu.showMenu());
                ImageIcon retourAuMenuIcon = new ImageIcon(shopMenu.buttonPath + "retourMenu.png");
                retourAuMenuButton.setIcon(retourAuMenuIcon);
                welcomePanel.add(retourAuMenuButton);


                mainMenuPanel.add(temp);
                mainMenuPanel.add(welcomePanel);

                break;


            case "couteaux":
                addItemToPanel(mainMenuPanel, path + "knife.png", "Sword 1");
                addItemToPanel(mainMenuPanel, path + "knife#2.png", "Sword 2");
                addItemToPanel(mainMenuPanel, path + "knife#3.png", "Sword 3");
                break;


            case "background":
                addItemToPanel(mainMenuPanel, path + "Background_MainMenu.png", "Background 1");
                addItemToPanel(mainMenuPanel, path + "Background_Solo.png", "Background 2");
                addItemToPanel(mainMenuPanel, path + "bgForet.png", "Background 3");
                break;


            case "music":
                addItemToPanel(mainMenuPanel, path + "music.png", "Music 1");
                addItemToPanel(mainMenuPanel, path + "music.png", "Music 2");
                addItemToPanel(mainMenuPanel, path + "music.png", "Music 2");
                break;


            case "cart":
                JPanel cartPanel = displayList(shopMenu.cart.getCart(), mainMenuPanel, "Panier");
                if (shopMenu.argentLabel != null) {
                    //shopMenu.updateTotal();
                }
                break;

        }
        tabPanel.add(mainMenuPanel, BorderLayout.CENTER);
        return tabPanel;
    }


    /**
     * Affiche une liste d'articles dans un JPanel déroulant.
     *
     * @param list La liste d'articles à afficher.
     * @param mainMenuPanel Le JPanel principal où la liste sera affichée.
     * @param labelName Le nom du label associé à la liste.
     * @return Le JPanel contenant la liste d'articles.
     */

    //A utiliser pour afficher la biliothèque
    public JPanel displayList(List<ShopItem> list, JPanel mainMenuPanel, String labelName) {
        JPanel temp2 = new JPanel();
        temp2.setOpaque(false);
        mainMenuPanel.add(temp2);

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel cartLabel = new JLabel(labelName);
        cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartPanel.add(cartLabel);

        if (list != null) {
            for (ShopItem article : list) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS)); // Disposition horizontale

                ImageIcon iconTemp = new ImageIcon(article.getArticleImagePath());
                ImageIcon icon = new ImageIcon(iconTemp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

                JLabel imageLabel = new JLabel(icon);
                JLabel nameLabel = new JLabel(article.getArticleName());

                imageLabel.setMaximumSize(new Dimension(50, 50));
                nameLabel.setMaximumSize(new Dimension(150, 50));

                JLabel priceLabel = new JLabel("Prix: " + article.getArticlePrice());
                priceLabel.setMaximumSize(new Dimension(100, 50));




                JButton deleteButton = new JButton(new ImageIcon(shopMenu.buttonPath + "binButton.png"));
                deleteButton.setMaximumSize(new Dimension(50, 50));
                deleteButton.addActionListener(e -> {
                    shopMenu.cart.removeArticle(article);
                    //shopMenu.updateTotal();
                    updateCartTotal();
                    cartPanel.remove(itemPanel);
                    cartPanel.revalidate();
                    cartPanel.repaint();
                });

                itemPanel.add(imageLabel);
                itemPanel.add(nameLabel);
                itemPanel.add(priceLabel);
                itemPanel.add(deleteButton);

                cartPanel.add(itemPanel);
            }
        }

        cartPanel.add(Box.createVerticalGlue()); //crée un composant pour "coller" les composants ensemble dans un layout vertical


        this.cartTotalLabel = new JLabel("Total du panier : " + String.valueOf(shopMenu.cart.getCartTotal()));
        cartTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartPanel.add(cartTotalLabel);



        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> shopMenu.saveCart());
        cartPanel.add(saveButton);


        mainMenuPanel.add(scrollPane);
        return cartPanel;
    }



    private void updateCartTotal() {
        this.cartTotalLabel.setText("Total du panier : " + shopMenu.cart.getCartTotal());
    }


    /**
     * Ajoute un article à un JPanel en tant qu'élément de liste avec un bouton associé pour le supprimer.
     *
     * @param panel Le JPanel où l'article sera ajouté.
     * @param imagePath Le chemin de l'image de l'article.
     * @param itemName Le nom de l'article.
     */
    private void addItemToPanel(JPanel panel, String imagePath, String itemName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(imagePath);
        JButton itemButton = new JButton();
        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);


        JLabel itemNameLabel = new JLabel(itemName);
        itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemNameLabel.setFont(itemNameLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));


        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemNameLabel, BorderLayout.SOUTH);


        panel.add(itemPanel);

        shopMenu.configureButton(itemButton, e -> {
            shopMenu.cart.addArticle(new ShopItem(itemName, 10, imagePath));
            shopMenu.saveB = false;
            //shopMenu.updateTotal();
            int cartTabIndex = shopMenu.tabbedPane.indexOfTab("Panier");
            if (cartTabIndex != -1) {
                JPanel cartPanel = createPanel(null, "cart");
                shopMenu.tabbedPane.setComponentAt(cartTabIndex, cartPanel);
            }
        });

    }
}
