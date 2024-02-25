package User;

import java.util.HashMap;
import java.util.Map;
public class UserManager {
    private Map<String, User> userList = new HashMap<>();

    public UserManager() {
        userList.put("userTest", new User("userTest", "password123", "sauvegarde_userTest.ser"));
    }

    public User validerConnexion(String nomUtilisateur, String motDePasse) {
        if (userList.containsKey(nomUtilisateur)) {
            User user = userList.get(nomUtilisateur);
            if (user.motDePasse().equals(motDePasse)) {
                return user;
            }
        }
        return null;
    }

    public boolean ajouterUtilisateur(String nomUtilisateur, String motDePasse) {
        if (!userList.containsKey(nomUtilisateur)) {
            String cheminSauvegarde = "sauvegarde_" + nomUtilisateur + ".ser";
            User nouvelUtilisateur = new User(nomUtilisateur, motDePasse, cheminSauvegarde);
            userList.put(nomUtilisateur, nouvelUtilisateur);
            return true;
        }
        return false;
    }
}
