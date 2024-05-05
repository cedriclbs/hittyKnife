package User;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class User {
    private String nomUtilisateur;
    private String motDePasse;
    private String cheminSauvegarde;
    private int argent;
    private int level;
    private int xp;


    //Constructeur par défaut pour la sérialisation en JSON
    public User() {
        this.nomUtilisateur = null;
        this.motDePasse = null;
        this.cheminSauvegarde = null;
        this.argent = 0;
        this.level = 1;
        this.xp = 0;

    }

    public User(String nomUtilisateur, String motDePasse, String cheminSauvegarde, int argent, int level, int xp) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cheminSauvegarde = cheminSauvegarde;
        this.argent = argent;
        this.level = level;
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "User[" +
                "nomUtilisateur=" + nomUtilisateur + ", " +
                "motDePasse=" + motDePasse + ", " +
                "cheminSauvegarde=" + cheminSauvegarde + "]\n";
    }

    @JsonProperty("xp")
    public int getXp() {
        return xp;
    }

    @JsonProperty("xp")
    public void setXp(int xp) {
        this.xp = xp;
    }

    @JsonProperty("level")
    public int getLevel() {
        return level;
    }

    @JsonProperty("level")
    public void setLevel(int level) {
        this.level = level;
    }


    @JsonProperty("nomUtilisateur")
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    @JsonProperty("nomUtilisateur")
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @JsonProperty("motDePasse")
    public String getMotDePasse() {
        return motDePasse;
    }

    @JsonProperty("motDePasse")
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @JsonProperty("cheminSauvegarde")
    public String getCheminSauvegarde() {
        return cheminSauvegarde;
    }

    @JsonProperty("cheminSauvegarde")
    public void setCheminSauvegarde(String cheminSauvegarde) {
        this.cheminSauvegarde = cheminSauvegarde;
    }

    @JsonProperty("argent")
    public int getArgent() {
        return argent;
    }

    @JsonProperty("argent")
    public void setArgent(int argent) {
        this.argent = argent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.nomUtilisateur, that.nomUtilisateur) &&
                Objects.equals(this.motDePasse, that.motDePasse) &&
                Objects.equals(this.cheminSauvegarde, that.cheminSauvegarde) &&
                Objects.equals(this.argent, that.argent) ;
    }


}