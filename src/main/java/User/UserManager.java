package User;

import java.io.*;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
public class UserManager {

    private static final long serialVersionUID = 1L;
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
        try {
            instance = mapper.readValue(new File("src/main/saves/usermanager.json"), UserManager.class);
        } catch (IOException e) {
            instance = new UserManager();
        }
    }

    public void sauvegarderInstance() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/saves/usermanager.json"), instance);
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
            String cheminSauvegarde = "src/main/saves/sauvegarde_" + nomUtilisateur + ".ser"; // Chemin ajusté
            User nouvelUtilisateur = new User(nomUtilisateur, motDePasse, cheminSauvegarde);
            userList.put(nomUtilisateur, nouvelUtilisateur);
            return true;
        }
        return false;
    }
}
