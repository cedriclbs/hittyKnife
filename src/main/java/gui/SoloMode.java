package gui;

import config.Game;
import entity.Knife;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

        // Add a MouseListener to the knifeDisplay
        knifeDisplay.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                knifeDisplay.handleMouseClick();
            }

            /**
             * Invoked when a mouse button has been pressed on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {

            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
