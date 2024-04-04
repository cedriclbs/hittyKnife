package config;

import entity.TypeCible;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gère les rounds dans le jeu, y compris les cibles pour chaque round.
 */
public class Round {
    private List<List<TypeCible>> roundsTargets; // Liste des cibles pour chaque round
    private int currentRound; // Indice du round actuel
    private Random random; // Pour générer des valeurs aléatoires

    /**
     * Constructeur pour initialiser les rounds et leurs cibles.
     */
    public Round() {
        this.random = new Random();
        this.roundsTargets = new ArrayList<>();
        this.currentRound = 0; // Commence au premier round

        initRounds(); // Initialise les rounds avec des cibles
    }

    /**
     * Prépare les rounds en créant une liste de cibles pour chacun.
     */
    private void initRounds() {
        for (int i = 0; i < 4; i++) { // Pour 3 rounds et 1 round de boss
            roundsTargets.add(new ArrayList<>());
        }
        populateTargets(); // Remplit chaque round de cibles
    }

    /**
     * Remplit chaque round avec un ensemble déterminé de cibles.
     */
    private void populateTargets() {
        for (int i = 0; i < roundsTargets.size(); i++) {
            List<TypeCible> currentRoundTargets = roundsTargets.get(i);
            int targetsCount = (i < roundsTargets.size() - 1) ? getRndIntTargetRounds() : 1; // Un seul boss pour le dernier round

            for (int j = 0; j < targetsCount; j++) {
                currentRoundTargets.add((i < roundsTargets.size() - 1) ? getRandomTypeCible() : getRandomTypeBoss());
            }
        }
    }

    /**
     * Sélectionne un type de cible aléatoire pour les rounds normaux.
     * @return Un type de cible aléatoire, à l'exception des boss.
     */
    private TypeCible getRandomTypeCible() {
        int randomNum = random.nextInt(2);
        return TypeCible.values()[randomNum];
    }

    /**
     * Sélectionne un type de boss aléatoire pour le round final.
     * @return Un type de boss aléatoire.
     */
    private TypeCible getRandomTypeBoss() {
        int randomNum = random.nextInt(3);
        return TypeCible.values()[TypeCible.values().length - 3 + randomNum];
    }

    /**
     * Génère un nombre aléatoire de cibles pour les rounds.
     * @return Un nombre aléatoire de cibles, entre 4 et 10.
     */
    private int getRndIntTargetRounds() {
        return 4 + random.nextInt(7);
    }

    /**
     * Gère la progression des cibles dans le round actuel et passe au suivant si nécessaire.
     */
    public void nextTarget() {
        if (!roundsTargets.get(currentRound).isEmpty()) {
            roundsTargets.get(currentRound).remove(0); // Élimine la première cible TODO: retirer la cible qui est touché donc fonction pour savoir quelle cible à été touchée
        } else if (currentRound < roundsTargets.size() - 1) {
            currentRound++; // Passe au round suivant
        }
    }

    /**
     * Vérifie si tous les rounds, y compris le boss, ont été complétés.
     * @return vrai si tous les rounds sont terminés, sinon faux.
     */
    public boolean isAllRoundCompleted() {
        return currentRound == roundsTargets.size() - 1 && roundsTargets.get(currentRound).isEmpty();
    }

    /**
     * Réinitialise les rounds pour un nouveau jeu, en vidant les listes de cibles et en repopulant.
     */
    public void reset() {
        for (List<TypeCible> round : roundsTargets) {
            round.clear(); // Vide les cibles de chaque round
        }

        currentRound = 0; // Réinitialise au premier round
        populateTargets(); // Repeuple les cibles
    }
}
