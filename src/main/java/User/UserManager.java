package User;

import java.util.HashMap;
import java.util.Map;
public class UserManager {
    private Map<String, User> utilisateurs = new HashMap<>();

    public UserManager() {
        // Ici, vous pourriez charger une liste d'utilisateurs depuis un fichier ou une base de données
        // Pour l'exemple, nous allons créer un utilisateur de test
        utilisateurs.put("userTest", new User("userTest", "password123", "sauvegarde_userTest.ser"));
    }

    public User validerConnexion(String nomUtilisateur, String motDePasse) {
        if (utilisateurs.containsKey(nomUtilisateur)) {
            User utilisateur = utilisateurs.get(nomUtilisateur);
            if (utilisateur.getMotDePasse().equals(motDePasse)) {
                return utilisateur;
            }
        }
        return null;
    }
}
