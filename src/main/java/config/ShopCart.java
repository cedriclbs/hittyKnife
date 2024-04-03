package config;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Représente le panier d'achats dans le magasin.
 */

public class ShopCart {


    private List<ShopItem> cart;
    private int cartTotal;


    /**
     * Initialise un nouveau panier d'achats vide.
     */
    public ShopCart (){
        this.cart = new ArrayList<>();
        this.cartTotal = 0;
    }


    public void addArticle (ShopItem article){
        if (!this.cart.contains(article)) {
            cart.add(article);
            this.cartTotal+=article.getArticlePrice();
        } else {
            JOptionPane.showMessageDialog(null, "Cet article est déjà dans le panier.");
        }
    }


    public void removeArticle (ShopItem article){
        if (cart.contains(article)) {
            cartTotal -= article.getArticlePrice(); // Soustraire le prix de l'article supprimé du total du panier
            cart.remove(article);
        } else {
            JOptionPane.showMessageDialog(null, "Cet article n'est pas dans le panier.");
        }
    }


    public List<ShopItem> getCart() {
        return cart;
    }


    public int getCartTotal () {
        return this.cartTotal;
    }

}
