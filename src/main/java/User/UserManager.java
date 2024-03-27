package User;

import java.io.*;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe représentant le gestionnaire d'utilisateurs.
 */
public class UserManager {

    private static UserManager instance;
    private HashMap<String, User> userList;

    /**
     * Constructeur par défaut du gestionnaire d'utilisateurs.
     */
    public UserManager() {
        userList = new HashMap<>();
    }

    /**
     * Méthode permettant d'obtenir l'instance unique du gestionnaire d'utilisateurs.
     * @return L'instance unique du gestionnaire d'utilisateurs.
     */
    public static UserManager getInstance() {
        if (instance == null) {
            chargerInstance();
        }
        return instance;
    }

    /**
     * Méthode privée permettant de charger l'instance du gestionnaire d'utilisateurs depuis un fichier JSON.
     */
    private static void chargerInstance() {
        ObjectMapper mapper = new ObjectMapper();
        File fichier = new File("src/main/saves/usermanager.json");
        if (fichier.length() == 0) {
            instance = new UserManager();
        } else {
            try {
                // Utilisation de la désérialisation customisée
                TypeReference<HashMap<String, User>> typeRef = new TypeReference<>() {};
                HashMap<String, User> users = mapper.readValue(fichier, typeRef);
                instance = new UserManager();
                instance.userList = users;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode permettant de sauvegarder l'instance du gestionnaire d'utilisateurs dans un fichier JSON.
     */
    public void sauvegarderInstance() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Utilisation de la sérialisation customisée
            mapper.writeValue(new File("src/main/saves/usermanager.json"), this.userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de valider la connexion d'un utilisateur.
     * @param nomUtilisateur Le nom de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @return L'utilisateur si la connexion est réussie, sinon null.
     */
    public User validerConnexion(String nomUtilisateur, String motDePasse) {
        if (userList.containsKey(nomUtilisateur)) {
            User user = userList.get(nomUtilisateur);
            if (user.motDePasse().equals(motDePasse)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Méthode permettant d'ajouter un nouvel utilisateur.
     * @param nomUtilisateur Le nom de l'utilisateur à ajouter.
     * @param motDePasse Le mot de passe de l'utilisateur à ajouter.
     * @return true si l'utilisateur a été ajouté avec succès, sinon false.
     */
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