package User;

import java.io.*;


import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Classe représentant le gestionnaire d'utilisateurs.
 */
public class UserManager {

    private static UserManager instance;
    private HashMap<String, User> userList;
    // Algorithme de hachage pour les mots de passe
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Constructeur par défaut du gestionnaire d'utilisateurs.
     * Cette classe utilise le modèle Singleton pour garantir une seule instance.
     * L'utilisation du Singleton est justifiée car il permet d'avoir une seule
     * instance partagée de UserManager dans toute l'application.
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
     * Malgré le Singleton, il est ici utile pour garantir que l'on charge bien le même UserManager à chaque lancement de partie
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
            if (verifierMotDePasse(motDePasse, user.getMotDePasse())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Méthode permettant de vérifier si un mot de passe est correct.
     * @param motDePasseSaisi Le mot de passe saisi par l'utilisateur.
     * @param motDePasseStocke Le mot de passe stocké dans le système (haché).
     * @return true si le mot de passe est correct, sinon false.
     */
    private boolean verifierMotDePasse(String motDePasseSaisi, String motDePasseStocke) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(motDePasseSaisi.getBytes());
            String motDePasseHache = Base64.getEncoder().encodeToString(hash);
            return motDePasseHache.equals(motDePasseStocke);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
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
            String motDePasseHache = hasherMotDePasse(motDePasse);
            User nouvelUtilisateur = new User(nomUtilisateur, motDePasseHache, cheminSauvegarde, 100, 1, 0);
            userList.put(nomUtilisateur, nouvelUtilisateur);
            sauvegarderInstance(); // Sauvegarder l'instance UserManager pour inclure le nouvel utilisateur.
            return true;
        }
        return false;
    }

    /**
     * Méthode permettant de hacher un mot de passe.
     * @param motDePasse Le mot de passe à hacher.
     * @return Le mot de passe haché.
     */
    private String hasherMotDePasse(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(motDePasse.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}