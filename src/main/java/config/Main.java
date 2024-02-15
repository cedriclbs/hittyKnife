package config;

import gui.HomeMenu;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Créez l'instance de votre jeu
        game.chargerEtat("save.ser");
        HomeMenu homeMenu = new HomeMenu("Hitty Knife", "cheminVersImageDeFond", "save.ser");
        homeMenu.setGame(game); // Passez l'instance de Game à HomeMenu
        Loop l = new Loop();
    }

}