package User;

import java.io.*;
import java.util.HashMap;
public class UserManager implements Serializable {

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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/saves/usermanager.ser"))) {
            instance = (UserManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            instance = new UserManager();
        }
    }

    public void sauvegarderInstance() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/saves/usermanager.ser"))) {
            oos.writeObject(instance);
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
