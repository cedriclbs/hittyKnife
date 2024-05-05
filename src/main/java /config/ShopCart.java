package config;

import gui.ShopMenu;
import gui.ShopTab;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Représente le panier d'achats dans le magasin.
 */

public class ShopCart {

    private Game game;
    private List<ShopItem> cart;
    private int cartTotal;
    private JLabel userMoneyLabel;

    private JLabel cartTotalLabel;




    /**
     * Initialise un nouveau panier d'achats vide.
     *
     * @param game Le jeu associé au panier d'achats.
     */
    public ShopCart (Game game){
        this.game = game;
        this.cart = new ArrayList<>();
        this.cartTotal = 0;
        this.cartTotalLabel = new JLabel("Total du panier : " + String.valueOf(cartTotal));
    }


    /**
     * Ajoute un article au panier d'achats.
     *
     * @param article L'article à ajouter au panier.
     */
    public void addArticle (ShopItem article){
        if (this.cart.contains(article)){
            JOptionPane.showMessageDialog(null, "Cet article est déjà dans le panier.");
        } else if (this.game.library != null && this.game.library.getLibraryItems().contains(article)) {
            JOptionPane.showMessageDialog(null, "Article déjà approprié.");
        } else {
            cart.add(article);
            this.cartTotal+=article.getArticlePrice();
            updateCartTotal();
        }
    }

    /**
     * Supprime un article du panier d'achats.
     *
     * @param article L'article à supprimer du panier.
     */
    public void removeArticle (ShopItem article){
        if (cart.contains(article)) {
            cartTotal -= article.getArticlePrice(); // Soustraire le prix de l'article supprimé du total du panier
            cart.remove(article);
        } else {
            JOptionPane.showMessageDialog(null, "Cet article n'est pas dans le panier.");
        }
    }



    /**
     * Affiche le panier d'achats dans un JPanel.
     *
     * @param shopMenu       Le menu du magasin associé.
     * @param list           La liste des articles à afficher dans le panier.
     * @param shopTab        L'onglet du magasin associé.
     * @param mainMenuPanel  Le JPanel principal où afficher le panier.
     * @param labelName      Le nom du label du panier.
     * @return Le JPanel contenant le panier d'achats.
     */
    public JPanel displayCart(ShopMenu shopMenu, List<ShopItem> list, ShopTab shopTab, JPanel mainMenuPanel, String labelName) {
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
                JLabel nameLabel = new JLabel("  " + article.getArticleName());

                imageLabel.setMaximumSize(new Dimension(50, 50));
                nameLabel.setMaximumSize(new Dimension(150, 50));

                JLabel priceLabel = new JLabel("Prix: " + article.getArticlePrice());
                priceLabel.setMaximumSize(new Dimension(100, 50));



                JButton deleteButton = new JButton(new ImageIcon(RessourcesPaths.buttonPath + "binButton.png"));
                deleteButton.setMaximumSize(new Dimension(50, 50));
                deleteButton.addActionListener(e -> {
                    shopMenu.getCart().removeArticle(article);
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


        // userMoneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //cartPanel.add(userMoneyLabel);

        cartTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartPanel.add(cartTotalLabel);


        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> shopMenu.saveCart());
        cartPanel.add(saveButton);


        mainMenuPanel.add(scrollPane);
        return cartPanel;
    }


    /**
     * Met à jour le montant total du panier et met à jour l'affichage.
     */
    private void updateCartTotal() {
        this.cartTotalLabel.setText("Total du panier : " + getCartTotal());
    }

    public List<ShopItem> getCart() {
        return cart;
    }

    public int getCartTotal () {
        return this.cartTotal;
    }





}
