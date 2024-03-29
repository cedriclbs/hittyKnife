package config;

import java.util.ArrayList;
import java.util.List;

public class Cart {


    private List<Article> cart;

    public Cart (){
        this.cart = new ArrayList<>();
    }


    public void addArticle (Article article){
        cart.add(article);
    }


    public void removeArticle (Article article){
        cart.remove(article);
    }


    public List<Article> getCart() {
        return cart;
    }


    public int getTotal () {
        int res = 0;
        for (Article article : cart){
            res+=article.getArticlePrice();
        }
        return res;
    }


    public void afficherPanier() {
        System.out.println("Contenu du panier :");
        for (Article article : cart) {
            System.out.println(article);
        }
    }



}
