package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


/**
 * Représente un article dans le magasin.
 */
public class ShopItem {

    private String articleName;
    private int articlePrice;
    private String articleImagePath;

    @JsonCreator
    public ShopItem(){
        // Constructeur sans arguments pour la désérialisation JSON

    }

    /**
     * Initialise un nouvel article avec un nom, un prix et un chemin d'image.
     *
     * @param articleName     Le nom de l'article.
     * @param articlePrice    Le prix de l'article.
     * @param articleImagePath Le chemin de l'image de l'article.
     */
    @JsonCreator
    public ShopItem(@JsonProperty("articleName") String articleName,
                    @JsonProperty("articlePrice") int articlePrice,
                    @JsonProperty("articleImagePath") String articleImagePath) {
        this.articleImagePath = articleImagePath;
        this.articleName = articleName;
        this.articlePrice = articlePrice;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ShopItem other = (ShopItem) obj;
        return Objects.equals(articleName, other.articleName) && articlePrice == other.articlePrice && Objects.equals(articleImagePath, other.articleImagePath);
    }


    @Override
    public String toString() {
        return this.articleName + "|" + this.articlePrice + "|" + this.articleImagePath;
    }
}
