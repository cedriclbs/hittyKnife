package App;

import config.Game;
import gui.HomeMenu;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Créez l'instance de votre jeu
        game.chargerEtat("save.ser");

        JFrame frame = new JFrame("Hitty Knife");
        frame.setIconImage(new ImageIcon("src/main/ressources/icon/icon.png").getImage());

        HomeMenu homeMenu = new HomeMenu("Hitty Knife", "src/main/ressources/background/solo.png", "save.ser");
        homeMenu.setGame(game); // Passez l'instance de Game à HomeMenu
        Loop l = new Loop();
    }

}