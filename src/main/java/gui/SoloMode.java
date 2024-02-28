package gui;

import config.Game;
import entity.Knife;
import javax.swing.*;
import java.awt.*;
import static config.States.*;

public class SoloMode {

    private Game game;
    private HomeMenu homeMenu;
    private BackgroundPanel soloFrame;
    private Knife knife;
    private KnifeDisplay knifeDisplay;
    private JLabel knifeLabel;


    public SoloMode(Game game, HomeMenu homeMenu) {
        this.game = game;
        this.homeMenu = homeMenu;
        this.knife = new Knife();
        this.knifeDisplay = new KnifeDisplay(knife);
        this.knifeLabel = new JLabel(new ImageIcon(knifeDisplay.getKnifeImage()));
    }

    public void initialize () {
        soloFrame = new BackgroundPanel("src/main/ressources/background/solo.png");
        soloFrame.setLayout(new BorderLayout());
        soloFrame.add(knifeLabel);

    }

    public void startSoloGame() {
        setStates(SOLOMODE);
        initialize();
        homeMenu.getContentPane().removeAll();
        homeMenu.getContentPane().add(soloFrame);
        homeMenu.revalidate();
        homeMenu.repaint();
        knifeDisplay.repaint();
        displayKnife();
    }

    public void displayKnife () {
        soloFrame.add(knifeLabel);
        knifeDisplay.repaint();
    }

}


