package App;

import User.UserManager;
import gui.ConnectionMenu;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //Game game = new Game();
        //Debug.testSerialization();
        SwingUtilities.invokeLater(() -> {
            UserManager gestionUtilisateurs = UserManager.getInstance();
            ConnectionMenu ecranConnexion = new ConnectionMenu(null, gestionUtilisateurs);
            ecranConnexion.setVisible(true);
        });
        //Loop l = new Loop();
    }

}