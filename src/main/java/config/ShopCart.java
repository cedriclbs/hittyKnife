package config;

import java.util.ArrayList;
import java.util.List;

public class ShopCart {


    private List<ShopArticle> cart;

    public ShopCart (){
        this.cart = new ArrayList<>();
    }


    public void addArticle (ShopArticle article){
        cart.add(article);
    }


    public void removeArticle (ShopArticle article){
        cart.remove(article);
    }


    public List<ShopArticle> getCart() {
        return cart;
    }


    public int getTotal () {
        int res = 0;
        for (ShopArticle article : cart){
            res+=article.getArticlePrice();
        }
        return res;
    }


//    public void afficherPanier() {
//        System.out.println("Contenu du panier :");
//        for (ShopArticle article : cart) {
//            System.out.println(article);
//        }
//    }




}
