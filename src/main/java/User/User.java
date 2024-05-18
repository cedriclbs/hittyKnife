package User;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Classe représentant un utilisateur.
 */
public final class User {
    private String nomUtilisateur;
    private String motDePasse;
    private String cheminSauvegarde;
    private int argent;
    private int level;
    private int xp;

    /**
     * Constructeur par défaut pour la sérialisation en JSON.
     */
    public User() {
        this.nomUtilisateur = null;
        this.motDePasse = null;
        this.cheminSauvegarde = null;
        this.argent = 0;
        this.level = 1;
        this.xp = 0;
    }

    /**
     * Constructeur de la classe User.
     *
     * @param nomUtilisateur   Le nom de l'utilisateur.
     * @param motDePasse       Le mot de passe de l'utilisateur.
     * @param cheminSauvegarde Le chemin de sauvegarde de l'utilisateur.
     * @param argent           Le montant d'argent de l'utilisateur.
     * @param level            Le niveau de l'utilisateur.
     * @param xp               L'expérience de l'utilisateur.
     */
    public User(String nomUtilisateur, String motDePasse, String cheminSauvegarde, int argent, int level, int xp) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cheminSauvegarde = cheminSauvegarde;
        this.argent = argent;
        this.level = level;
        this.xp = xp;
    }

    /**
     * Obtient le niveau de l'utilisateur.
     *
     * @return Le niveau de l'utilisateur.
     */
    @JsonProperty("level")
    public int getLevel() {
        return level;
    }

    /**
     * Définit le niveau de l'utilisateur.
     *
     * @param level Le niveau de l'utilisateur à définir.
     */
    @JsonProperty("level")
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Obtient le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    @JsonProperty("nomUtilisateur")
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param nomUtilisateur Le nom de l'utilisateur à définir.
     */
    @JsonProperty("nomUtilisateur")
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    /**
     * Obtient le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    @JsonProperty("motDePasse")
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param motDePasse Le mot de passe de l'utilisateur à définir.
     */
    @JsonProperty("motDePasse")
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Obtient le chemin de sauvegarde de l'utilisateur.
     *
     * @return Le chemin de sauvegarde de l'utilisateur.
     */
    @JsonProperty("cheminSauvegarde")
    public String getCheminSauvegarde() {
        return cheminSauvegarde;
    }

    /**
     * Définit le chemin de sauvegarde de l'utilisateur.
     *
     * @param cheminSauvegarde Le chemin de sauvegarde de l'utilisateur à définir.
     */
    @JsonProperty("cheminSauvegarde")
    public void setCheminSauvegarde(String cheminSauvegarde) {
        this.cheminSauvegarde = cheminSauvegarde;
    }

    /**
     * Obtient le montant d'argent de l'utilisateur.
     *
     * @return Le montant d'argent de l'utilisateur.
     */
    @JsonProperty("argent")
    public int getArgent() {
        return argent;
    }

    /**
     * Définit le montant d'argent de l'utilisateur.
     *
     * @param argent Le montant d'argent de l'utilisateur à définir.
     */
    @JsonProperty("argent")
    public void setArgent(int argent) {
        this.argent = argent;
    }

    /**
     * Obtient l'expérience de l'utilisateur.
     *
     * @return L'expérience de l'utilisateur.
     */
    @JsonProperty("xp")
    public int getXp() {
        return xp;
    }

    /**
     * Définit l'expérience de l'utilisateur.
     *
     * @param xp L'expérience de l'utilisateur à définir.
     */
    @JsonProperty("xp")
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Redéfinition de la méthode equals pour comparer deux objets User.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.nomUtilisateur, that.nomUtilisateur) &&
                Objects.equals(this.motDePasse, that.motDePasse) &&
                Objects.equals(this.cheminSauvegarde, that.cheminSauvegarde) &&
                this.argent == that.argent;
    }
}
