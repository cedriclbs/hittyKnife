package gui;

import App.Loop;
import App.Main;
import config.ShopArticle;
import config.Cart;
import config.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static config.States.*;

/**
 * Fenêtre du magasin où les joueurs peuvent acheter des objets ou des améliorations.
 */
public class ShopMenu extends JFrame {
    private Game game; // Référence vers l'objet Game pour obtenir l'argent du joueur
    private HomeMenu homeMenu;

    private String knifePath = "src/main/ressources/knifes/";
    private String backgroundPath = "src/main/ressources/background/";
    private String buttonPath = "src/main/ressources/button/";
    private String ongletsPath = "src/main/ressources/onglets/";

    private JLabel argentLabel; // Label pour afficher l'argent du joueur

    private Cart cart;
    private JTabbedPane tabbedPane;


    /**
     * Constructeur de la fenêtre du magasin.
     *
     * @param title           Le titre de la fenêtre du magasin.
     * @param backgroundPath Le chemin de l'image de fond du magasin.
     * @param hm              Le menu principal.
     */
    public ShopMenu(String title, String backgroundPath, HomeMenu hm) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.game = new Game();
        Main.loop = new Loop(game);
        Main.loop.startTickFunction();
        this.homeMenu = hm;
        this.cart = new Cart();
        this.tabbedPane = new JTabbedPane();
        initialize();
    }

    public void startShopMenu() {
        setStates(SHOPMENU);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        // Création des onglets
        tabbedPane.setOpaque(false);

        // Ajouter les onglets
        addTab(tabbedPane, "Accueil", null, "shopWelcomeOnglet.png", "welcome");
        addTab(tabbedPane, "Couteaux", knifePath, "knifeOnglet.png", "couteaux");
        addTab(tabbedPane, "Background", backgroundPath, "bgOnglet.png", "background");
        addTab(tabbedPane, "Music", buttonPath, "musicOnglet.png", "music");
        addTab(tabbedPane, "Panier", null, "cartOnglet.png", "cart");

        add(tabbedPane, BorderLayout.CENTER);

        // Création et ajout du label d'argent
        argentLabel = new JLabel("Argent disponible: " + game.getArgent());
        add(argentLabel, BorderLayout.NORTH);


    }

    private void addTab(JTabbedPane tabbedPane, String title, String imagePath, String iconPath, String category) {
        JPanel panel = createPanel(imagePath, category);
        tabbedPane.addTab(title, panel);
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(ongletsPath + iconPath);
            tabbedPane.setIconAt(tabbedPane.indexOfComponent(panel), icon);
        }
    }


    private void updateTotal() {
        argentLabel.setText("Total: " + (game.getArgent() - cart.getTotal())); // Mettre à jour le texte avec le total du panier
    }





    /**
     * Crée et configure le panneau du menu du magasin.
     *
     * @param path     Le chemin de l'image de fond du panneau du menu.
     * @param category La catégorie de l'onglet.
     * @return Le panneau du menu du magasin configuré.
     */


    private JPanel createPanel(String path, String category) {
        // Création du panel pour chaque onglet avec une image de fond personnalisée

        BackgroundPanel tabPanel = new BackgroundPanel(backgroundPath + "bgShopMenu.png");
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
                retourAuMenuButton.addActionListener(e -> showMenu());
                ImageIcon retourAuMenuIcon = new ImageIcon(buttonPath + "retourMenu.png");
                retourAuMenuButton.setIcon(retourAuMenuIcon);
                welcomePanel.add(retourAuMenuButton);


                mainMenuPanel.add(temp);
                mainMenuPanel.add(welcomePanel); // Ajout de welcomePanel à mainMenuPanel


                break;


            case "couteaux":
                addShopItem(mainMenuPanel, path + "knife.png", "Sword 1");
                addShopItem(mainMenuPanel, path + "knife#2.png", "Sword 2");
                addShopItem(mainMenuPanel, path + "knife#3.png", "Sword 3");
                break;


            case "background":
                addShopItem(mainMenuPanel, path + "Background_MainMenu.png", "Background 1");
                addShopItem(mainMenuPanel, path + "Background_Solo.png", "Background 2");
                addShopItem(mainMenuPanel, path + "bgForet.png", "Background 3");
                break;


            case "music":
                addShopItem(mainMenuPanel, path + "music.png", "Music 1");
                addShopItem(mainMenuPanel, path + "music.png", "Music 2");
                addShopItem(mainMenuPanel, path + "music.png", "Music 2");
                break;


            case "cart":
                // Créez le panneau pour afficher les articles du panier
                JPanel temp2 = new JPanel();
                temp2.setOpaque(false);
                mainMenuPanel.add(temp2);

                JPanel cartPanel = new JPanel();
                cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS)); // Utilisation d'un BoxLayout pour l'alignement vertical

                JScrollPane scrollPane = new JScrollPane(cartPanel); // Ajout d'une barre de défilement au panneau du panier
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Défilement vertical si nécessaire
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Pas de défilement horizontal

                cartPanel.add(new JLabel("Panier"));

                for (ShopArticle article : cart.getCart()) {
                    JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alignement à gauche
                    itemPanel.setOpaque(false);

                    // Ajoutez l'image de l'article
                    ImageIcon iconTemp = new ImageIcon(article.getArticleImagePath());
                    ImageIcon icon = new ImageIcon(iconTemp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

                    JLabel imageLabel = new JLabel(icon);
                    itemPanel.add(imageLabel);

                    // Ajoutez le nom et le prix de l'article
                    JLabel nameLabel = new JLabel(article.getArticleName());
                    JLabel priceLabel = new JLabel("Prix: " + article.getArticlePrice());
                    itemPanel.add(nameLabel);
                    itemPanel.add(priceLabel);

                    // Ajoutez le panneau de l'article au panneau du panier
                    cartPanel.add(itemPanel);
                }

                mainMenuPanel.add(scrollPane); // Ajout du JScrollPane contenant le panneau du panier au panneau principal
                break;


        }

        tabPanel.add(mainMenuPanel, BorderLayout.CENTER);
        return tabPanel;
    }

    private void addShopItem(JPanel panel, String imagePath, String itemName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(imagePath);
        JButton itemButton = new JButton();
        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);

        // Création de l'étiquette pour le nom de l'élément
        JLabel itemNameLabel = new JLabel(itemName);
        itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemNameLabel.setFont(itemNameLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f)); // Modification de la taille, du style et du gras

        // Ajout du bouton et de l'étiquette au panneau de l'élément
        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemNameLabel, BorderLayout.SOUTH);

        // Ajout du panneau de l'élément au panneau principal
        panel.add(itemPanel);

        configureButton(itemButton, e -> {
            cart.addArticle(new ShopArticle(itemName, 10, imagePath));
            cart.afficherPanier();
            updateTotal();
            int cartTabIndex = tabbedPane.indexOfTab("Panier");
            if (cartTabIndex != -1) {
                JPanel cartPanel = createPanel(null, "cart");
                tabbedPane.setComponentAt(cartTabIndex, cartPanel);
            }
        });
    }


    private void configureButton(JButton button, ActionListener actionListener) {
        button.setPreferredSize(new Dimension(50, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.addActionListener(actionListener);
    }

    /**
     * Redirige vers le menu principal.
     * Cette méthode est appelée lorsque le bouton "Menu" est cliqué.
     */
    private void showMenu() {
        setStates(HOMEMENU);
        homeMenu.showHomeMenu();
    }
}
