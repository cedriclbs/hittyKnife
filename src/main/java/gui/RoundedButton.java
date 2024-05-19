package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Cette classe représente bouton arrondi personnalisé.
 * Ce bouton affiche un arrière-plan arrondi avec des coins arrondis.
 * Elle étend la classe JButton.
 */
public class RoundedButton extends JButton {
    private Shape shape;

    /**
     * Constructeur de la classe RoundedButton.
     *
     * @param text Le texte affiché sur le bouton.
     */
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 100, 100);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 100, 100);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 100, 100);
        }
        return shape.contains(x, y);
    }

}
