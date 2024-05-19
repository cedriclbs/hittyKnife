package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Représente un article dans le magasin.
 */
public class ShopItem {

    private String articleName;      // Le nom de l'article
    private int articlePrice;        // Le prix de l'article
    private String articlePath;      // Le chemin de l'image de l'article

    /**
     * Constructeur par défaut pour la désérialisation JSON.
     */
    @JsonCreator
    public ShopItem(){
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
        this.articlePath = articleImagePath;
        this.articleName = articleName;
        this.articlePrice = articlePrice;
    }

    /**
     * Récupère le nom de l'article.
     *
     * @return Le nom de l'article.
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * Récupère le prix de l'article.
     *
     * @return Le prix de l'article.
     */
    public int getArticlePrice() {
        return articlePrice;
    }

    /**
     * Récupère le chemin de l'image de l'article.
     *
     * @return Le chemin de l'image de l'article.
     */
    public String getArticlePath() {
        return articlePath;
    }

    /**
     * Compare cet article avec un autre objet pour l'égalité.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ShopItem other = (ShopItem) obj;
        return Objects.equals(articleName, other.articleName) && articlePrice == other.articlePrice && Objects.equals(articlePath, other.articlePath);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de cet article.
     *
     * @return Une chaîne représentant l'article.
     */
    @Override
    public String toString() {
        return this.articleName + "|" + this.articlePrice + "|" + this.articlePath;
    }
}
