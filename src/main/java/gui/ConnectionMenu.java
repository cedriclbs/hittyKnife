package gui;

import User.UserManager;
import javax.swing.*;
import java.awt.*;
import config.Game;

public class ConnectionMenu extends JDialog {
    private JTextField champNomUtilisateur;
    private JPasswordField champMotDePasse;
    private UserManager gestionUtilisateurs;

    public ConnectionMenu(Frame parent, UserManager gestion) {
        super(parent, "Connexion", true);
        this.gestionUtilisateurs = gestion;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(3, 2));

        champNomUtilisateur = new JTextField();
        champMotDePasse = new JPasswordField();
        JButton boutonConnexion = new JButton("Connexion");
        JButton boutonCreerCompte = new JButton("Nouveau Compte");

        add(new JLabel("Nom d'utilisateur:"));
        add(champNomUtilisateur);
        add(new JLabel("Mot de passe:"));
        add(champMotDePasse);
        add(boutonConnexion);
        add(boutonCreerCompte);

        boutonConnexion.addActionListener(e -> seConnecter());
        boutonCreerCompte.addActionListener(e -> creerCompte());

        pack();
        setLocationRelativeTo(null); // Centre l'écran de connexion
    }

    private void seConnecter() {
        String nomUtilisateur = champNomUtilisateur.getText();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (gestionUtilisateurs.validerConnexion(nomUtilisateur, motDePasse) != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            this.dispose(); // Ferme l'écran de connexion

            // Lancement du HomeMenu
            SwingUtilities.invokeLater(() -> {
                Game game = new Game(); // Créez l'instance de votre jeu
                game.chargerEtat("save.ser");
                HomeMenu homeMenu = new HomeMenu("Hitty Knife", "cheminVersImageDeFond", "cheminVersMusique");
            });
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la connexion. Vérifiez votre nom d'utilisateur et mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void creerCompte() {
        String nomUtilisateur = champNomUtilisateur.getText();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur et le mot de passe ne peuvent pas être vides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean utilisateurAjoute = gestionUtilisateurs.ajouterUtilisateur(nomUtilisateur, motDePasse);
        if (utilisateurAjoute) {
            JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur est déjà pris. Veuillez essayer un nom différent.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //TODO: connexion automatique après une création de compte réussie


}
