package App;

import User.User;
import User.UserManager;
import config.Game;
import gui.ConnectionMenu;
import gui.HomeMenu;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Créez l'instance de votre jeu
        SwingUtilities.invokeLater(() -> {
            UserManager gestionUtilisateurs = new UserManager(); // Assurez-vous d'initialiser cette classe correctement
            ConnectionMenu ecranConnexion = new ConnectionMenu(null, gestionUtilisateurs);
            ecranConnexion.setVisible(true);
        });
        Loop l = new Loop();
    }

}