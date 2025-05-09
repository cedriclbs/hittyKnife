package gui;

import User.UserManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Game;
import User.User;


/**
 * Fenêtre de connexion pour l'application.
 */
public class ConnectionMenu extends JDialog {
    private JTextField champNomUtilisateur;
    private JPasswordField champMotDePasse;
    private UserManager gestionUtilisateurs;
    private Game game;

    /**
     * Constructeur de la fenêtre de connexion.
     *
     * @param parent Le parent de la fenêtre.
     * @param gestion Le gestionnaire d'utilisateurs.
     */
    public ConnectionMenu(Frame parent, UserManager gestion) {
        super(parent, "Connexion", true);
        this.gestionUtilisateurs = gestion;
        setupUI();
    }

    /**
     * Initialise l'interface utilisateur de la fenêtre de connexion.
     */
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

    /**
     * Méthode appelée lorsqu'un utilisateur tente de se connecter.
     */
    private void seConnecter() {
        String nomUtilisateur = champNomUtilisateur.getText();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (gestionUtilisateurs.validerConnexion(nomUtilisateur, motDePasse) != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            this.dispose();

            // Crée un nouveau jeu avec le nom d'utilisateur et le chemin de sauvegarde
            String cheminSauvegarde = "src/main/saves/sauvegarde_" + nomUtilisateur + ".json";
            game = new Game(true, cheminSauvegarde);

            // Charge l'état du jeu pour l'utilisateur connecté
            try {
                game.chargerEtat(cheminSauvegarde);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la sauvegarde de jeu.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            MainFrame mainFrame = new MainFrame(game);
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la connexion. Vérifiez votre nom d'utilisateur et mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite créer un compte.
     */
    private void creerCompte() {
        String nomUtilisateur = champNomUtilisateur.getText();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur et le mot de passe ne peuvent pas être vides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean utilisateurAjoute = gestionUtilisateurs.ajouterUtilisateur(nomUtilisateur, motDePasse);
        if (utilisateurAjoute) {
            // Créer le fichier de sauvegarde pour le nouvel utilisateur
            String cheminSauvegarde = "src/main/saves/sauvegarde_" + nomUtilisateur + ".json";
            creerFichierSauvegardeUtilisateur(cheminSauvegarde, nomUtilisateur);
            JOptionPane.showMessageDialog(this, "Compte créé avec succès !");

            // Crée un nouveau jeu avec le nom d'utilisateur et le chemin de sauvegarde
            game = new Game(true, cheminSauvegarde);

            // Charge l'état du jeu pour l'utilisateur connecté
            try {
                game.chargerEtat(cheminSauvegarde);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la sauvegarde de jeu.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
            MainFrame mainFrame = new MainFrame(game);
        } else {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur est déjà pris. Veuillez essayer un nom différent.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Crée un fichier de sauvegarde pour un nouvel utilisateur.
     *
     * @param cheminSauvegarde Le chemin du fichier de sauvegarde.
     * @param username         Le nom d'utilisateur.
     */
    private void creerFichierSauvegardeUtilisateur(String cheminSauvegarde, String username) {
        try {
            // Créer un nouvel utilisateur avec le nom d'utilisateur et un mot de passe par défaut
            String motDePasse = "password"; // Mot de passe par défaut pour un nouvel utilisateur
            User nouvelUtilisateur = new User(username, motDePasse, cheminSauvegarde, 100, 1, 0); // 100 est l'argent initial

            // Sérialiser l'objet User dans le fichier JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(cheminSauvegarde), nouvelUtilisateur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}