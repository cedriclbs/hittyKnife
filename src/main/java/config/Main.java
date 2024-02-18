package config;

import gui.HomeMenu;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Créez l'instance de votre jeu
        game.chargerEtat("save.ser");

        ImageIcon icon = new ImageIcon("src/main/ressources/icon/icon.ico");
        JFrame frame = new JFrame("Hitty Knife");
        frame.setIconImage(gameIcon.getImage());

        HomeMenu homeMenu = new HomeMenu("Hitty Knife", "src/main/ressources/background/solo.png", "save.ser");
        homeMenu.setGame(game); // Passez l'instance de Game à HomeMenu
        Loop l = new Loop();
    }

}