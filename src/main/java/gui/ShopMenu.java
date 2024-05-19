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
    private ShopTab cartTab;

    /**
     * Constructeur de la fenêtre du magasin.
     *
     * @param game Le jeu auquel le magasin est lié.
     */
    public ShopMenu(Game game) {
        setStates(SHOPMENU);
        this.game = game;
        this.cart = new ShopCart(game);
        this.tabbedPane = new JTabbedPane();
        initialize();
    }

    /**
     * Obtient le panier d'achats du magasin.
     *
     * @return Le panier d'achats du magasin.
     */
    public ShopCart getCart() {
        return this.cart;
    }

    /**
     * Initialise le ShopMenu avec sa disposition et les onglets du magasin.
     */
    private void initialize() {
        setLayout(new BorderLayout());
        tabbedPane.setOpaque(false);

        ShopTab couteauxTab = new ShopTab(tabbedPane, "Couteaux", RessourcesPaths.knifePath, "knifeTab.png", "couteaux", this);
        ShopTab backgroundTab = new ShopTab(tabbedPane, "Background", RessourcesPaths.backgroundPath, "bgTab.png", "background", this);
        cartTab = new ShopTab(tabbedPane, "Panier", null, "cartTab.png", "cart", this);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Sauvegarde le panier d'achats actuel.
     *
     * @param game Le jeu auquel le magasin est lié.
     */
    public void saveCart(Game game) {
        if (cart != null) {
            int argentUser = game.getArgent();
            int totalPanier = cart.getCartTotal();

            if (argentUser - totalPanier >= 0){
                game.addArgent(-totalPanier);
                game.updateLibrary(cart.getCart());
                JOptionPane.showMessageDialog(null, "Le panier a été sauvegardé avec succès.");

                cart.setCartTotal(0);
                cart.getCart().clear();
                refreshCartTab();
            } else {
                JOptionPane.showMessageDialog(null, "Pas assez de ressources.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le panier est vide.");
        }
    }

    /**
     * Retourne l'instance du jeu
     */
    public Game getGame () {
        return this.game;
    }

    /**
     * Actualise l'onglet du panier du magasin.
     */
    void refreshCartTab() {
        int cartTabIndex = tabbedPane.indexOfTab("Panier");
        if (cartTabIndex != -1) {
            JPanel cartPanel = cartTab.createPanel(null, "cart");
            tabbedPane.setComponentAt(cartTabIndex, cartPanel);
        }
    }

    /**
     * Configure un bouton avec des paramètres standard.
     *
     * @param button         Le bouton à configurer.
     * @param actionListener L'action à exécuter lorsque le bouton est cliqué.
     */
    void configureButton(JButton button, ActionListener actionListener) {
        button.setPreferredSize(new Dimension(50, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(actionListener);
    }
}
