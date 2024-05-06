package config;

import entity.TypeCible;
import entity.bosses.*;
import entity.Cible;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Gère les rounds dans le jeu, y compris les cibles pour chaque round.
 */
public class RoundManagement {
    private List<Round> rounds; // Liste des rounds contenant des cibles pour chaque round
    private int currentRoundIndex; // Indice du round actuel
    private Random random; // Pour générer des valeurs aléatoires
    private TypeCible lastBossType = null; 
    

    /**
     * Constructeur pour initialiser les rounds et leurs cibles.
     */
    public RoundManagement() {
        this.random = new Random();
        this.rounds = new ArrayList<>();
        this.currentRoundIndex = 0;
        initRounds();
    }

   
    /**
     * Prépare les rounds en créant une liste de cibles pour chacun.
     */
    private void initRounds() {
        this.rounds.clear(); // Assurez-vous de vider la liste avant de l'initialiser
        System.out.println(rounds.size());
        System.out.println("Initialisation des rounds...");
        for (int i = 0; i < 4; i++) {
            this.rounds.add(new Round());
            System.out.println("Round " + i + " ajouté.");
        }
        System.out.println("Population des rounds...");
        populateRounds();
    
    }
    

    /**
     * Remplit chaque round avec un ensemble déterminé de cibles.
     */
    private void populateRounds() {
        System.out.println("Début de la population des rounds...");
        int lastIndex = rounds.size() - 1; // Index du dernier round
        System.out.println(lastIndex);
    
        for (int i = 0; i < rounds.size(); i++) {
            Round round = rounds.get(i);
            int targetsCount = i < lastIndex ? getRndIntTargetRounds() : 1; // Plusieurs cibles pour les rounds normaux, une pour le boss
            System.out.println("Round " + i + ", nombre de cibles: " + targetsCount);
    
            for (int j = 0; j < targetsCount; j++) {
                // Détermine le type de cible
                TypeCible typeCible = i < lastIndex ? getRandomTypeCible() : getRandomTypeBoss(); //mettre le truc du boss
                System.out.println("Création de cible: " + typeCible + " pour le round " + i);

                double x,y;
                
                do {
                    x = getRandomPositionX();
                    y = getRandomPositionY();
                } while (EstTropProche(x, y, round.getListeCibles()) || (x > -7 && x < 7 && y >= 0 && y <= 15)); // Réessaye tant que la cible est trop proche des autres
            
    
                // Créer la cible en fonction du type
                Cible cible = createCibleWithType(typeCible, x, y);
    
                // Ajouter la cible au round
                round.addCible(cible); 
            }
        }
    }

    private Cible createCibleWithType(TypeCible typeCible, double x, double y) {
        switch (typeCible) {
            case CIBLE_NORMALE:
                // Créer une cible normale
                return new Cible(typeCible,x, y);
    
            case CIBLE_ARGENT:
                return new Cible(typeCible,x, y);
    
            case CIBLE_BOSS1:
                // Créer un BossType1
                return new BossType1(x, y);
    
            case CIBLE_BOSS2:
                // Créer un BossType2
                return new BossType2(x,y);

            case CIBLE_BOSS3:
                // Créer un BossType3
                return new BossType3(x, y);
    
            default:
                return new Cible(x, y);
        }
    }

    private boolean EstTropProche(double x, double y, List<Cible> cibles) {
        double minDistance = 8; 
        for (Cible cible : cibles) {
            double dx = x - cible.getX();
            double dy = y - cible.getY();
            if (Math.sqrt(dx * dx + dy * dy) < minDistance) {
                return true; 
            }
        }
        return false;
    }

    private double getRandomPositionX() {
        double x;
        do {
            x = (random.nextDouble() * 60) - 30; //à regler selon la taille du jeu, cible en position x de -30 à 30
        } while (x > -7 && x < 7); // Évite la zone autour de x = 0
        return x;
    }
    
    private double getRandomPositionY() {
        double y;
        do {
            y = random.nextDouble() * 30; // génère un y entre 0 et 30
        } while (y >= 0 && y <= 15 && Math.abs(getRandomPositionX()) <= 7); // Évite la combinaison de y entre 0 et 15 avec x entre -7 et 7
        return y;
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
     * Sélectionne un type de boss aléatoire pour le round final, en évitant de répéter le dernier boss utilisé.
     * @return Un type de boss aléatoire.
     */
    private TypeCible getRandomTypeBoss() {
        TypeCible newBossType;
        do {
            int randomNum = random.nextInt(3); // Il y a 3 types de boss à choisir
            newBossType = TypeCible.values()[TypeCible.values().length - 3 + randomNum]; // Sélectionne parmi les types de boss
        } while (newBossType == lastBossType); 
        lastBossType = newBossType; 
        return newBossType;
    }

    /**
     * Génère un nombre aléatoire de cibles pour les rounds.
     * @return Un nombre aléatoire de cibles, entre 4 et 10.
     */
    private int getRndIntTargetRounds() {
        return 4 + random.nextInt(4);
        //return 1;
    }
    // méthode pour vérifier si tous les rounds sont complétés
    public boolean isAllRoundsCompleted() {
        return currentRoundIndex == rounds.size();
    }

    //méthode pour réinitialiser les rounds
    public void resetRounds() {
        this.rounds.clear();
        currentRoundIndex = 0;
        initRounds();
    }
    
    // getter et setter
    public List<Round> getListeRounds() { 
        return rounds; 
    }

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }


    public void setCurrentRoundIndex(int a) {
        this.currentRoundIndex = a;
    }

}