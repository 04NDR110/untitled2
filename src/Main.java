import java.util.*;

class Player {
    private String name;
    private int survivedRounds;

    public Player(String name) {
        this.name = name;
        this.survivedRounds = 0;
    }

    public String getName() {
        return name;
    }

    public int getSurvivedRounds() {
        return survivedRounds;
    }

    public void incrementRounds() {
        survivedRounds++;
    }

    @Override
    public String toString() {
        return "Gracz: " + name + ", Przeżyte rundy: " + survivedRounds;
    }
}

public class Main {
    static Random random = new Random();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
       System.out.println("Copyright Skliarevskyi Andrii 124803");
        List<String> names = Arrays.asList("Jan", "Anna", "Piotr", "Kasia", "Marek", "Zosia"); // Список імен
        Set<Player> players = new HashSet<>(); // Набір гравців (уникнення дублікатів)
        Map<String, Integer> results = new HashMap<>(); // Мапа для зберігання результатів

        System.out.println("Witaj w grze Rosyjska Ruletka!");

        while (true) {
            String playerName = getRandomName(names);
            Player player = new Player(playerName);
            players.add(player);

            boolean gameWon = playGame(player); // Основна логіка гри
            results.put(player.getName(), Math.max(results.getOrDefault(player.getName(), 0), player.getSurvivedRounds()));

            System.out.println("Czy chcesz zagrać jeszcze raz? 1 - tak / 0 - nie");
            int choice = sc.nextInt();
            if (choice == 0) break;
        }

        showResults(results, players); // Вивід результатів
        showBestResult(results);       // Вивід найкращого результату
    }

    // Функція для отримання випадкового імені
    private static String getRandomName(List<String> names) {
        return names.get(random.nextInt(names.size()));
    }

    // Основна гра
    private static boolean playGame(Player player) {
        int loadedChamber = random.nextInt(6) + 1; // Випадковий патрон
        System.out.println("Gracz " + player.getName() + ", rewolwer jest naładowany. Gotowy?");
        boolean alive = true;

        for (int i = 1; i <= 6; i++) {
            System.out.println("Naciśnij spust. Runda " + i + ". 1 - strzał / 0 - ucieczka");
            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("Uciekłeś! Jesteś tchórzem, ale żyjesz!");
                alive = false;
                break;
            }

            if (i == loadedChamber) {
                System.out.println("Bum! Koniec gry. Przeżyłeś " + (i - 1) + " rund.");
                alive = false;
                break;
            } else {
                System.out.println("Żyjesz! Idziemy dalej.");
                player.incrementRounds();
            }
        }

        if (alive) {
            System.out.println("Szczęście! Rewolwer nie wypalił. Gracz " + player.getName() + " przeżył całą grę!");
        }

        return alive;
    }

    // Функція для відображення результатів
    private static void showResults(Map<String, Integer> results, Set<Player> players) {
        System.out.println("\n=== Wyniki Gry ===");
        for (Player player : players) {
            System.out.println(player);
        }

        System.out.println("\nWyniki najlepszych prób:");
        results.forEach((name, rounds) -> System.out.println(name + " - " + rounds + " rund(y)"));
    }

    // Функція для відображення найкращої спроби
    private static void showBestResult(Map<String, Integer> results) {
        int bestScore = Collections.max(results.values()); // Знаходимо максимальне значення
        System.out.println("\n=== Najlepsza próba gry ===");
        results.forEach((name, rounds) -> {
            if (rounds == bestScore) {
                System.out.println("Najlepszy wynik: " + name + " - " + rounds + " rund(y)");
            }
        });
    }
}
