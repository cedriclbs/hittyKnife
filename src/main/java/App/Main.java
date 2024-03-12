package App;

import User.UserManager;
import config.Game;
import gui.ConnectionMenu;
import gui.HomeMenu;

import javax.swing.*;


public class Main {

    public static Loop loop;
    public static Game game;
    public static void main(String[] args) {
        //game = new Game();
        //loop = new Loop(game);
        //Debug.testSerialization();
        SwingUtilities.invokeLater(() -> {
            UserManager gestionUtilisateurs = UserManager.getInstance();
            ConnectionMenu ecranConnexion = new ConnectionMenu(null, gestionUtilisateurs);
            ecranConnexion.setVisible(true);
        });
    }

}