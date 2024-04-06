package User;

import java.util.Objects;

public final class User {
    private final String nomUtilisateur;
    private final String motDePasse;
    private final String cheminSauvegarde;
    private int argent;

    public User(String nomUtilisateur, String motDePasse, String cheminSauvegarde, int argent) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cheminSauvegarde = cheminSauvegarde;
        this.argent = argent;
    }

    @Override
    public String toString() {
        return "User[" +
                "nomUtilisateur=" + nomUtilisateur + ", " +
                "motDePasse=" + motDePasse + ", " +
                "cheminSauvegarde=" + cheminSauvegarde + "]\n";
    }

    public String nomUtilisateur() {
        return nomUtilisateur;
    }

    public String motDePasse() {
        return motDePasse;
    }

    public String cheminSauvegarde() {
        return cheminSauvegarde;
    }

    public int getArgent() {
        return argent;
    }

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