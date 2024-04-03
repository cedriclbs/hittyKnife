package gui;

import App.Loop;
import App.Main;
import config.ShopCart;
import config.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import static config.States.*;



/**
 * Fenêtre du magasin où les joueurs peuvent acheter des objets ou des améliorations.
 */


public class ShopMenu extends JFrame {
    private Game game;
    private HomeMenu homeMenu;
    JLabel argentLabel;

    ShopCart cart;
    JTabbedPane tabbedPane;

    boolean saveB;

    String knifePath = "src/main/ressources/knifes/";
    String backgroundPath = "src/main/ressources/background/";
    String buttonPath = "src/main/ressources/button/";
    String tabPath = "src/main/ressources/onglets/";




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
        this.cart = new ShopCart();
        this.tabbedPane = new JTabbedPane();
        this.saveB = true;
        initialize();
    }

    public void startShopMenu() {
        setStates(SHOPMENU);
    }


    /**
     * Initialise le ShopMenu avec sa disposition et les onglets du magasin.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        tabbedPane.setOpaque(false);

        ShopTab accueil = new ShopTab(tabbedPane, "Accueil", null, "shopWelcomeTab.png", "welcome", this);
        ShopTab couteaux = new ShopTab(tabbedPane, "Couteaux", knifePath, "knifeTab.png", "couteaux", this);
        ShopTab background = new ShopTab(tabbedPane, "Background", backgroundPath, "bgTab.png", "background", this);
        ShopTab music = new ShopTab(tabbedPane, "Music", buttonPath, "musicTab.png", "music", this);
        ShopTab cart = new ShopTab(tabbedPane, "Panier", null, "cartTab.png", "cart", this);
        //ShopTab bibliotheque = new ShopTab(tabbedPane, "Bibliothèque", null, "biblioTab.png", "bibliotheque", this);

        add(tabbedPane, BorderLayout.CENTER);

        // Création et ajout du label d'argent
        argentLabel = new JLabel("Argent disponible: " + game.getArgent());
        add(argentLabel, BorderLayout.NORTH);

    }


    void updateTotal() {
        argentLabel.setText("Total: " + (game.getArgent() - cart.getCartTotal()));
    }



    public void saveCart() {
        if (cart != null) {
            saveB = true;
            //Ajouter this.cart à la bibliothèque
            JOptionPane.showMessageDialog(null, "Le panier a été sauvegardé avec succès.");
        } else {
            JOptionPane.showMessageDialog(null, "Le panier est vide.");
        }
    }



    /**
     * Configure un bouton avec des paramètres standard.
     *
     * @param button          Le bouton à configurer.
     * @param actionListener L'action à exécuter lorsque le bouton est cliqué.
     */
    void configureButton(JButton button, ActionListener actionListener) {
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
    void showMenu() {
        if (!saveB) {
            JPanel saveToQuit = new JPanel(new GridLayout(1, 2));
            JButton sauvButton = new JButton("Sauvegarder");
            JButton quitterButton = new JButton("Quitter");

            configureButton(sauvButton, e -> {
                saveCart();
                SwingUtilities.getWindowAncestor(saveToQuit).dispose();
                showMenuOnceVerif();
            });
            configureButton(quitterButton, e -> {
                this.cart.getCart().clear();
                updateTotal();
                revalidate();
                repaint();
                SwingUtilities.getWindowAncestor(saveToQuit).dispose();
                showMenuOnceVerif();
            });

            saveToQuit.add(sauvButton);
            saveToQuit.add(quitterButton);
            JOptionPane.showMessageDialog(null, saveToQuit, "Panier non sauvegardé", JOptionPane.PLAIN_MESSAGE);
        } else {
            showMenuOnceVerif();
        }

    }


    private void showMenuOnceVerif () {
        setStates(HOMEMENU);
        homeMenu.showHomeMenu();
    }


}
