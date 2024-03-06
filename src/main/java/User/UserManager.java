package User;

import java.io.*;

import com.fasterxml.jackson.core.type.TypeReference;
import config.Game;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
public class UserManager {

    private static UserManager instance;
    private HashMap<String, User> userList;

    public UserManager() {
        userList = new HashMap<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            chargerInstance();
        }
        return instance;
    }

    private static void chargerInstance() {
        ObjectMapper mapper = new ObjectMapper();
        File fichier = new File("src/main/saves/usermanager.json");
        if (fichier.length() == 0) {
            instance = new UserManager();
        } else {
            try {
                // Utilisation de la désérialisation customisée
                TypeReference<HashMap<String, User>> typeRef = new TypeReference<HashMap<String, User>>() {};
                HashMap<String, User> users = mapper.readValue(fichier, typeRef);
                instance = new UserManager();
                instance.userList = users;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sauvegarderInstance() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Utilisation de la sérialisation customisée
            mapper.writeValue(new File("src/main/saves/usermanager.json"), this.userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            String cheminSauvegarde = "src/main/saves/sauvegarde_" + nomUtilisateur + ".json";
            User nouvelUtilisateur = new User(nomUtilisateur, motDePasse, cheminSauvegarde);
            userList.put(nomUtilisateur, nouvelUtilisateur);
            sauvegarderInstance(); // Sauvegarder l'instance UserManager pour inclure le nouvel utilisateur.
            return true;
        }
        return false;
    }

}
