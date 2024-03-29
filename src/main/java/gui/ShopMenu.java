package gui;

import App.Loop;
import App.Main;
import config.Game;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private String musicPath = "src/main/ressources/button/";
    private JLabel argentLabel; // Label pour afficher l'argent du joueur


    /**
     * Constructeur de la fenêtre du magasin.
     *
     * @param title Le titre de la fenêtre du magasin.
     * @param backgroundPath Le chemin de l'image de fond du magasin.
     * @param //musicPath Le chemin de la musique de fond du magasin.
     */
    public ShopMenu(String title, String backgroundPath, HomeMenu hm) {
        //super(title, backgroundPath, musicPath);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.game = new Game();
        Main.loop = new Loop(game);
        Main.loop.startTickFunction();
        this.homeMenu = hm;

        initialize();
    }


    public void startShopMenu() {
        setStates(SHOPMENU);
    }

    public void setGame(Game game) {
        this.game = game;
    }




    private void initialize() {
        setLayout(new BorderLayout());

        // Création des onglets
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        JPanel couteauxPanel = createMenuPanel(knifePath, "couteaux");
        tabbedPane.addTab("Couteaux", couteauxPanel);

        JPanel backgroundPanel = createMenuPanel(backgroundPath, "background");
        tabbedPane.addTab("Background", backgroundPanel);

        JPanel musicPanel = createMenuPanel(musicPath, "music");
        tabbedPane.addTab("Music", musicPanel);

        JPanel retourAuMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton retourAuMenuButton = new JButton("Retour au menu");
        retourAuMenuButton.addActionListener(e -> showMenu());


        JLabel retourAuMenuLabel = new JLabel("<html>Ajoutez au panier des articles avec la monnaie disponible, et sauvegardez.<br>Vous pouvez également retourner au menu sans effectuer d'achat.</html>");
        retourAuMenuPanel.add(retourAuMenuButton);
        retourAuMenuPanel.add(retourAuMenuLabel);


        ImageIcon retourAuMenuIcon = new ImageIcon(musicPath + "retourMenu.png");
        tabbedPane.addTab("Retour au menu", retourAuMenuIcon, retourAuMenuPanel, "Retour au menu");

        add(tabbedPane, BorderLayout.CENTER);

        // Création et ajout du label d'argent
        argentLabel = new JLabel("Argent disponible: " + game.getArgent());
        add(argentLabel, BorderLayout.NORTH);
    }


    private void configureButton(JButton button, ActionListener actionListener) {
        button.setPreferredSize(new Dimension(50,50));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.addActionListener(actionListener);
    }

    private JButton createMenuButton(String imagePath) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        // Charger l'icône à partir du chemin de l'image
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);

        return button;
    }
    private JButton getMenuButton() {
        JButton menuButton = new JButton();
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setOpaque(false);
        menuButton.setPreferredSize(new Dimension(50, 50));

        // Chargement de l'image d'origine
        ImageIcon originalIcon = new ImageIcon("src/main/ressources/button/retourMenu.png");

        // Redimensionnement de l'image à la taille du bouton
        Image resizedImage = originalIcon.getImage().getScaledInstance(menuButton.getPreferredSize().width, menuButton.getPreferredSize().height, Image.SCALE_SMOOTH);

        // Création d'une nouvelle ImageIcon avec l'image redimensionnée
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Définir l'icône redimensionnée pour le bouton
        menuButton.setIcon(resizedIcon);

        menuButton.addActionListener(e -> showMenu());
        return menuButton;
    }


    /**
     * Crée et configure le panneau du menu du magasin.
     *
     * @param //backgroundPath Le chemin de l'image de fond du panneau du menu.
     * @return Le panneau du menu du magasin configuré.
     */
    JPanel createMenuPanel(String path, String category) {
        // Création du panel pour chaque onglet avec une image de fond personnalisée
        BackgroundPanel tabPanel = new BackgroundPanel(backgroundPath + "bgShopMenu.png");
        tabPanel.setLayout(new BorderLayout());

        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        mainMenuPanel.setOpaque(false);
        mainMenuPanel.setBorder(new EmptyBorder(100, 100, 100, 100));

        switch (category) {
            case "couteaux":
                addShopItem(mainMenuPanel, path + "knife.png", "Sword 1");
                addShopItem(mainMenuPanel, path + "knife#2.png", "Sword 2");
                addShopItem(mainMenuPanel, path + "knife#3.png", "Sword 3");
                addShopItem(mainMenuPanel, path + "knife.png", "Sword 4");
                break;
            case "background":
                addShopItem(mainMenuPanel, path + "Background_MainMenu.png", "Background 1");
                addShopItem(mainMenuPanel, path + "Background_Solo.png", "Background 2");
                addShopItem(mainMenuPanel, path + "bgForet.png", "Background 3");
                break;
            case "music":
                addShopItem(mainMenuPanel, path + "music.png", "Music 1");
                addShopItem(mainMenuPanel, path + "music.png", "Music 2");
                break;
        }

        tabPanel.add(mainMenuPanel, BorderLayout.CENTER);
        //tabPanel.setPreferredSize(new Dimension(100, 100)); // Taille du panel de menu
        return tabPanel;
    }

    private void addShopItem(JPanel panel, String imagePath, String itemName) {
        // Création du panneau pour l'élément de magasin
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(imagePath);
        JButton itemButton = new JButton();


        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);

        // Configuration de l'action du bouton (à remplir)
        configureButton(itemButton, e -> {
            // Action à effectuer lorsqu'un élément est sélectionné dans le menu principal
        });

        // Création de l'étiquette pour le nom de l'élément
        JLabel itemNameLabel = new JLabel(itemName);
        itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemNameLabel.setFont(itemNameLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f)); // Modification de la taille, du style et du gras

        // Ajout du bouton et de l'étiquette au panneau de l'élément
        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemNameLabel, BorderLayout.SOUTH);

        // Ajout du panneau de l'élément au panneau principal
        panel.add(itemPanel);
    }


    /**
     * Configure les propriétés visuelles et comportementales d'un bouton.
     *
     * @param button Le bouton à configurer.
     * @param actionListener L'action à exécuter lorsque le bouton est cliqué.
     */


    /**
     * Redirige vers le menu principal.
     * Cette méthode est appelée lorsque le bouton "Menu" est cliqué.
     */
    private void showMenu() {
        setStates(HOMEMENU);
        homeMenu.showHomeMenu();
    }

}
