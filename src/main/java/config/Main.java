package config;

import gui.GameOverMenu;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // Créez l'instance de votre jeu
        GameOverMenu gameOverMenu = new GameOverMenu("Game Over","","");
        gameOverMenu.setGame(game);
        Loop l = new Loop();
    }

}
