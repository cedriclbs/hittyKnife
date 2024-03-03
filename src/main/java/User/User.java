package User;

import java.util.Objects;

public record User(String nomUtilisateur, String motDePasse, String cheminSauvegarde) {

    @Override
    public String toString() {
        return "User[" +
                "nomUtilisateur=" + nomUtilisateur + ", " +
                "motDePasse=" + motDePasse + ", " +
                "cheminSauvegarde=" + cheminSauvegarde + ']';
    }
}