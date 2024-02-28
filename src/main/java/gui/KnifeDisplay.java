package gui;

import config.Game;
import entity.Knife;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Math.round;

public class KnifeDisplay extends JPanel {

    private Knife knife;
    private Image knifeImage;
    //private BufferedImage knifeImage;


    public KnifeDisplay(Knife knife) {
        this.knife = knife;
        this.knifeImage = new ImageIcon("src/main/ressources/knifes/knife.png").getImage();

//        try {
//            this.knifeImage = ImageIO.read(getClass().getResourceAsStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public Image getKnifeImage() {
        return this.knifeImage;
    }


    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        if (knifeImage != null) {
            g.drawImage(knifeImage, 0, 0, this);
        }

        /*
        Graphics2D g2d = (Graphics2D) g.create();

        double rotationAngle = Math.toRadians(knife.getAngle());
        AffineTransform rotation = AffineTransform.getRotateInstance(rotationAngle, knife.getX(), 40 - knife.getY());
        g2d.setTransform(rotation);

        g2d.drawImage(
                knifeImage,
                (int) knife.getX() - knifeImage.getWidth(null) / 2,
                (int) (40 - knife.getY()) - knifeImage.getHeight(null) / 2, null
        );
        g2d.dispose();
         */
    }
}
