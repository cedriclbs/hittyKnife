package App;

import User.User;
import User.UserManager;
import config.Game;
import gui.ConnectionMenu;
import gui.HomeMenu;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        SwingUtilities.invokeLater(() -> {
            UserManager gestionUtilisateurs = new UserManager();
            ConnectionMenu ecranConnexion = new ConnectionMenu(null, gestionUtilisateurs);
            ecranConnexion.setVisible(true);
        });

        Loop l = new Loop();
    }

}