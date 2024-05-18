package config;

import entity.Bonus;
import entity.MovingTarget;
import entity.TypeCible;
import entity.bosses.*;
import entity.Cible;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static entity.Bonus.TypeBonus.*;


/**
 * Gère les rounds dans le jeu, y compris les cibles pour chaque round.
 */
public class RoundManagement {
    private List<Round> rounds; // Liste des rounds contenant des cibles pour chaque round
    private int currentRoundIndex; // Indice du round actuel
    private Random random; // Pour générer des valeurs aléatoires
    private TypeCible lastBossType = null; 
    private Queue<TypeCible> bossQueue;    // File principale pour les bosses
    private Queue<TypeCible> tempQueue;    // File temporaire pour stocker les bosses récemment utilisés
    

    /**
     * Constructeur pour initialiser les rounds et leurs cibles.
     */
    public RoundManagement() {
        this.random = new Random();
        this.rounds = new ArrayList<>();
        this.currentRoundIndex = 0;
        initBossQueues();
        initRounds();
    }

   
    /**
     * Prépare les rounds en créant une liste de cibles pour chacun.
     */
    private void initRounds() {
        this.rounds.clear(); // Assurez-vous de vider la liste avant de l'initialiser
        //System.out.println("Initialisation des rounds...");
        for (int i = 0; i < 4; i++) {
            this.rounds.add(new Round());
            //System.out.println("Round " + i + " ajouté.");
        }
        //System.out.println("Population des rounds...");
        populateRounds();
    
    }

    private void initBossQueues() {
        List<TypeCible> bosses = Arrays.asList(
            TypeCible.CIBLE_BOSS1, 
            TypeCible.CIBLE_BOSS2, 
            TypeCible.CIBLE_BOSS3, 
            TypeCible.CIBLE_BOSS4  
        );
        Collections.shuffle(bosses);
        bossQueue = new LinkedList<>(bosses);
        tempQueue = new LinkedList<>();
    }

    /**
     * Remplit chaque round avec un ensemble déterminé de cibles.
     */
    private synchronized void populateRounds() {
        int lastIndex = rounds.size() - 1; // Index du dernier round

        for (int i = 0; i < rounds.size(); i++) {
            Round round = rounds.get(i);
            int targetsCount = i < lastIndex ? getRndIntTargetRounds() : 1; // Plusieurs cibles pour les rounds normaux, une pour le boss

            for (int j = 0; j < targetsCount; j++) {
                TypeCible typeCible;
                double x, y;

                if (i == lastIndex) {  // S'assure que c'est le dernier round
                    typeCible = getRandomTypeBoss();
                    x = -60;
                    y = -60;
                    Cible cible = createCibleWithType(typeCible, x, y);
                    round.addCible(cible);
                    continue;  // S'assure que seul un boss est ajouté dans le dernier round
                }

                // Sélection du type de cible pour les rounds autres que le dernier
                typeCible = TypeCible.CIBLE_NORMALE ;//getRandomTypeCible();
                do {
                    x = getRandomPositionX();
                    y = getRandomPositionY();
                } while (EstTropProche(x, y, round.getListeCibles()) || (x > -7 && x < 7 && y >= 0 && y <= 15)); // Réessaye tant que la cible est trop proche des autres

                Cible cible;
                if (j == targetsCount - 2) {
                    int randomNum = random.nextInt(2);
                    if (randomNum == 0) {
                        cible = createCibleWithType(TypeCible.CIBLE_MOUVANTE, x, y);
                    } else {
                        cible = createCibleWithType(typeCible, x, y);
                    }
                } else if (j == targetsCount - 1) {
                    cible = createCibleWithType(TypeCible.CIBLE_BONUS, x, y);
                } else {
                    cible = createCibleWithType(typeCible, x, y);
                }

                round.addCible(cible);
            }
        }
    }

    

    private Cible createCibleWithType(TypeCible typeCible, double x, double y) {
        switch (typeCible) {
            case CIBLE_NORMALE:
                // Créer une cible normale
                return new Cible(typeCible,x, y);
            case CIBLE_MOUVANTE:
                // Créer une cible mouvante
                return new MovingTarget(x, y);
            case CIBLE_BONUS:
                int randomNum = random.nextInt(5);
                Bonus.TypeBonus typeBonus;
                switch (randomNum){
                    case 0: typeBonus = BONUS_XP;break;
                    case 1: typeBonus = BONUS_GOLD;break;
                    case 2 : typeBonus = BONUS_GEL;break;
                    case 3 : typeBonus = BONUS_POWER;break;
                    case 4 : typeBonus = BONUS_TNT;break;
                    default: typeBonus = BONUS_TNT;break;
                }
                return new Bonus(x, y,typeBonus);
    
            case CIBLE_BOSS1:
                // Créer un BossType1
                return new BossType1(x, y);
    
            case CIBLE_BOSS2:
                // Créer un BossType2
                return new BossType2(x,y);

            case CIBLE_BOSS3:
                // Créer un BossType3
                return new BossType3(x, y);

            case CIBLE_BOSS4:
            // Créer un BossType4
            return new BossType4(x, y);
    
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
        int randomNum = random.nextInt(3);
        return TypeCible.values()[randomNum];
    }

    /**
     * Sélectionne un type de boss aléatoire pour le round final, en évitant de répéter le dernier boss utilisé.
     * @return Un type de boss aléatoire.
     */
    private TypeCible getRandomTypeBoss() {
        // S'assure que la queue principale a des éléments à offrir
        if (bossQueue.isEmpty()) {
            refillBossQueue();
        }

        TypeCible boss = bossQueue.poll();  // Retire le prochain boss de la file principale
        tempQueue.offer(boss);  // Ajoute ce boss à la file temporaire

        // Si la file temporaire atteint 3 éléments, réintégre le premier élément retiré à la file principale
        if (tempQueue.size() > 2) {
            bossQueue.offer(tempQueue.poll());
        }

        return boss;
    }

    private void refillBossQueue() {
        if (bossQueue.isEmpty() && tempQueue.size() <= 2) {
            while (!tempQueue.isEmpty()) {
                bossQueue.offer(tempQueue.poll());
            }
            Collections.shuffle((LinkedList<TypeCible>) bossQueue);
        }
    }
    

    /**
     * Génère un nombre aléatoire de cibles pour les rounds.
     * @return Un nombre aléatoire de cibles, entre 4 et 10.
     */
    private int getRndIntTargetRounds() {
        //return 4 + random.nextInt(4);
        return 3;
    }
    // méthode pour vérifier si tous les rounds sont complétés
    public boolean isAllRoundsCompleted() {
        return currentRoundIndex == rounds.size();
    }

    //méthode pour réinitialiser les rounds
    public void resetRounds() {
        System.out.println("Réinitialisation des rounds. Index actuel avant réinitialisation: " + currentRoundIndex);
        rounds.clear();
        currentRoundIndex = 0;
        initRounds();
        System.out.println("Index après réinitialisation: " + currentRoundIndex);
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