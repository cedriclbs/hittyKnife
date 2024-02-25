package User;

public class User {
    private String nomUtilisateur;
    private String motDePasse;
    private String cheminSauvegarde;

    public User(String nomUtilisateur, String motDePasse, String cheminSauvegarde) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cheminSauvegarde = cheminSauvegarde;
    }

    // Getters et Setters
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getCheminSauvegarde() {
        return cheminSauvegarde;
    }
}
