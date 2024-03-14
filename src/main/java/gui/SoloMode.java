package gui;

import config.Game;
import entity.Cible;
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
    private Cible cible;



    /**
     * Constructeur de la classe SoloMode.
     * @param game L'objet Game associé au mode solo.
     * @param homeMenu Le menu principal de l'application.
     */
    public SoloMode(Game game, HomeMenu homeMenu) {
        this.game = game;
        this.homeMenu = homeMenu;
        this.knife = new Knife();
        this.cible = new Cible("Cible", 100, KnifeDisplay.getBgImgWidth() / 2, KnifeDisplay.getBgImgHeight() / 2, 0);
        this.knifeDisplay = new KnifeDisplay(knife,"src/main/ressources/background/Background_Solo.png");
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

    public Knife getKnife() {
        return knife;
    }
}
