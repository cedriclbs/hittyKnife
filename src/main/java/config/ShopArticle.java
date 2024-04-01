package config;

public class ShopArticle {

    private String articleName;
    private int articlePrice;
    private String articleImagePath;



    public ShopArticle (String articleName, int articlePrice, String articleImagePath){
        this.articleName = articleName;
        this.articlePrice = articlePrice;
        this.articleImagePath = articleImagePath;
    }


    public String getArticleName() {
        return articleName;
    }

    public int getArticlePrice() {
        return articlePrice;
    }

    public String getArticleImagePath() {
        return articleImagePath;
    }

    @Override
    public String toString() {
        return this.articleName + "|" + this.articlePrice + "|" + this.articleImagePath;
    }
}
