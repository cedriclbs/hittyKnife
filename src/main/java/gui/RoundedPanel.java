package gui;

import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int arcWidth;
    private int arcHeight;
    private Color backgroundColor; // Ajout d'une couleur de fond pour la transparence

    public RoundedPanel(int arcWidth, int arcHeight, boolean forButton) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        if (forButton){
            this.backgroundColor = new Color(255, 255, 255, 0);
        } else {
            this.backgroundColor = new Color(255, 255, 255, 255);
        }
        setOpaque(false); // Indique que le panel n'est pas opaque
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(arcWidth, arcHeight);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g.create(); // Crée une nouvelle instance de Graphics2D pour éviter les problèmes de déformation de la forme
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(backgroundColor); // Utilise la couleur de fond avec transparence
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.dispose(); // Libère les ressources de Graphics2D
    }

}
