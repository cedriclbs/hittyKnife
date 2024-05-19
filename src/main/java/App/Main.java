package App;

import User.UserManager;
import gui.*;

import javax.swing.*;

public class Main {

    public static Loop loop;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManager gestionUtilisateurs = UserManager.getInstance();
            ConnectionMenu ecranConnexion = new ConnectionMenu(null, gestionUtilisateurs);
            ecranConnexion.setVisible(true);
        });
    }

}