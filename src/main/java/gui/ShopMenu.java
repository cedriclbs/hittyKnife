package gui;

import config.Game;
import config.RessourcesPaths;
import config.ShopCart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import static config.States.*;



/**
 * Fenêtre du magasin où les joueurs peuvent acheter des objets ou des améliorations.
 */


public class ShopMenu extends JPanel {
    private Game game;
    ShopCart cart;
    public JTabbedPane tabbedPane;
    boolean saveB;
    boolean assezArgent;


    /**
     * Constructeur de la fenêtre du magasin.
     *
     */
    public ShopMenu(Game game) {
        setStates(SHOPMENU);
        this.cart = new ShopCart(game);
        this.tabbedPane = new JTabbedPane();
        this.saveB = true;
        this.game = game;
        initialize();
    }




    /**
     * Initialise le ShopMenu avec sa disposition et les onglets du magasin.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        tabbedPane.setOpaque(false);

        ShopTab accueil = new ShopTab(tabbedPane, "Accueil", null, "shopWelcomeTab.png", "welcome", this);
        ShopTab couteaux = new ShopTab(tabbedPane, "Couteaux", RessourcesPaths.knifePath, "knifeTab.png", "couteaux", this);
        ShopTab background = new ShopTab(tabbedPane, "Background", RessourcesPaths.backgroundPath, "bgTab.png", "background", this);
        ShopTab music = new ShopTab(tabbedPane, "Music", RessourcesPaths.buttonPath, "musicTab.png", "music", this);
        ShopTab cart = new ShopTab(tabbedPane, "Panier", null, "cartTab.png", "cart", this);

        add(tabbedPane, BorderLayout.CENTER);


    }


    /**
     * Sauvegarde le panier d'achats actuel.
     */
    public void saveCart() {
        if (cart != null) {
            //TODO : Argent disponible pour acheter ? Faire la vérif
            //if (User.getArgent() - cart.getCartTotal() >= 0){
                saveB = true;
                //updateMoney();
                game.updateLibrary(cart.getCart());
                cart.getCart().clear();
                //refreshCartTab(); TODO : actualiser la liste une fois le panier sauvegarder
                JOptionPane.showMessageDialog(null, "Le panier a été sauvegardé avec succès.");
            //} else {
                //JOptionPane.showMessageDialog(null, "Pas assez de ressources.");
            //
        } else {
            JOptionPane.showMessageDialog(null, "Le panier est vide.");
        }
    }


    //private void updateMoney () {
    //    User.setArgent(User.getArgent() - cart.getCartTotal());
    //}



    /**
     * Configure un bouton avec des paramètres standard.
     *
     * @param button         Le bouton à configurer.
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
                //showMenuOnceVerif();
            });
            configureButton(quitterButton, e -> {
                this.cart.getCart().clear();
                //updateTotal();
                revalidate();
                repaint();
                SwingUtilities.getWindowAncestor(saveToQuit).dispose();
                //showMenuOnceVerif();
            });

            saveToQuit.add(sauvButton);
            saveToQuit.add(quitterButton);
            JOptionPane.showMessageDialog(null, saveToQuit, "Panier non sauvegardé", JOptionPane.PLAIN_MESSAGE);
        } else {
            //showMenuOnceVerif();
        }

    }

    public ShopCart getCart() {
        return this.cart;
    }

    /*
    private void showMenuOnceVerif () {
        setStates(HOMEMENU);
        //homeMenu.showHomeMenu();
    }

    */


}
