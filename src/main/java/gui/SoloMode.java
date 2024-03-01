package gui;

import config.Game;
import entity.Knife;
import static config.States.*;



/**
 * La classe SoloMode représente le mode de jeu solo du jeu et gère l'initialisation et le démarrage d'une partie solo du jeu.
 */
public class SoloMode {

    private Game game;
    private HomeMenu homeMenu;
    private KnifeDisplay knifeDisplay;
    private Knife knife;



    /**
     * Constructeur de la classe SoloMode.
     * @param game L'objet Game associé au mode solo.
     * @param homeMenu Le menu principal de l'application.
     */
    public SoloMode(Game game, HomeMenu homeMenu) {
        this.game = game;
        this.homeMenu = homeMenu;
        this.knife = new Knife();
        this.knifeDisplay = new KnifeDisplay(knife,"src/main/ressources/background/solo.png");
    }

    public void initialize() {
        setStates(SOLOMODE);
        homeMenu.getContentPane().removeAll();
        homeMenu.getContentPane().add(knifeDisplay);
        homeMenu.revalidate();
        homeMenu.repaint();
    }

    public void startSoloGame() {
        initialize();
    }
}
