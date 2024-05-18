package gui;

import java.awt.*;
import javax.swing.*;


/**
 * Un JPanel personnalisé avec des coins arrondis.
 */
public class RoundedPanel extends JPanel {
    private int arcWidth;
    private int arcHeight;
    private boolean forButton;
    private boolean forAccueil;
    private Color backgroundColor;



    /**
     * Constructeur de la classe RoundedPanel.
     *
     * @param arcWidth La largeur de l'arc pour les coins arrondis.
     * @param arcHeight La hauteur de l'arc pour les coins arrondis.
     * @param forButton Indique si le panel est destiné à être utilisé pour un bouton.
     * @param forAccueil Indique si le panel est destiné à être utilisé pour une page accueil.
     */
    public RoundedPanel(int arcWidth, int arcHeight, boolean forButton, boolean forAccueil) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.forButton = forButton;
        this.forAccueil = forAccueil;

        if (forButton){
            this.backgroundColor = new Color(255, 255, 255, 0);
        } else if (forAccueil){
            this.backgroundColor = new Color(255, 255, 255, 34);
        } else {
            this.backgroundColor = new Color(255, 255, 255, 255);
        }
        setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(arcWidth, arcHeight);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (forAccueil){
            int borderThickness = 5;
            graphics.setStroke(new BasicStroke(borderThickness));
            graphics.drawRoundRect(borderThickness / 2, borderThickness / 2, width - borderThickness, height - borderThickness, arcs.width, arcs.height);

            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(borderThickness / 2, borderThickness / 2, width - borderThickness, height - borderThickness, arcs.width, arcs.height);

        } else {
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }

        graphics.dispose();

    }

}
