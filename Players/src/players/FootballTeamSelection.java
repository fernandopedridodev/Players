package players;

/**
 *
 * @author pf.pedridomarino
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Player {
    String name;
    String position;

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return name + " (" + position + ")";
    }
}

public class FootballTeamSelection {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> allPlayers = new ArrayList<>();
        ArrayList<Player> selectedPlayers = new ArrayList<>();

        // Introducir los jugadores
        System.out.println("Introduce el nombre y la posición de los jugadores (ej: Juan, Goalkeeper).");
        System.out.println("Posiciones válidas: Goalkeeper, Central Defender, Left Back, Right Back, Midfielder, Winger, Forward.");
        System.out.println("Escribe 'done' para finalizar.");

        while (true) {
            System.out.print("Jugador: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            String[] parts = input.split(",");
            if (parts.length != 2) {
                System.out.println("Formato inválido. Usa: Nombre, Posición");
                continue;
            }

            String name = parts[0].trim();
            String position = parts[1].trim();
            if (!isValidPosition(position)) {
                System.out.println("Posición no válida. Usa una de las siguientes: Goalkeeper, Central Defender, Left Back, Right Back, Midfielder, Winger, Forward.");
                continue;
            }

            allPlayers.add(new Player(name, position));
        }

        // Validar que hay suficientes jugadores para cumplir los requisitos
        if (!hasSufficientPlayers(allPlayers)) {
            System.out.println("No hay suficientes jugadores en todas las posiciones obligatorias para generar una convocatoria.");
            return;
        }

        // Realizar la convocatoria
        selectedPlayers.add(selectPlayerByPosition(allPlayers, "Goalkeeper"));
        selectedPlayers.addAll(selectPlayersByPosition(allPlayers, "Central Defender", 2));
        selectedPlayers.add(selectPlayerByPosition(allPlayers, "Left Back"));
        selectedPlayers.add(selectPlayerByPosition(allPlayers, "Right Back"));
        selectedPlayers.addAll(selectPlayersByPosition(allPlayers, "Midfielder", 3));
        selectedPlayers.addAll(selectPlayersByPosition(allPlayers, "Winger", 2));
        selectedPlayers.add(selectPlayerByPosition(allPlayers, "Forward"));

        // Rellenar el resto de la convocatoria hasta 16 jugadores
        while (selectedPlayers.size() < 16 && !allPlayers.isEmpty()) {
            selectedPlayers.add(allPlayers.remove(0));
        }

        // Mostrar la convocatoria
        System.out.println("\nConvocatoria de 16 jugadores:");
        for (Player player : selectedPlayers) {
            System.out.println(player);
        }
    }

    // Métodos auxiliares
    private static boolean isValidPosition(String position) {
        return position.equalsIgnoreCase("Goalkeeper") ||
               position.equalsIgnoreCase("Central Defender") ||
               position.equalsIgnoreCase("Left Back") ||
               position.equalsIgnoreCase("Right Back") ||
               position.equalsIgnoreCase("Midfielder") ||
               position.equalsIgnoreCase("Winger") ||
               position.equalsIgnoreCase("Forward");
    }

    private static boolean hasSufficientPlayers(ArrayList<Player> players) {
        return players.stream().anyMatch(p -> p.position.equalsIgnoreCase("Goalkeeper")) &&
               players.stream().filter(p -> p.position.equalsIgnoreCase("Central Defender")).count() >= 2 &&
               players.stream().anyMatch(p -> p.position.equalsIgnoreCase("Left Back")) &&
               players.stream().anyMatch(p -> p.position.equalsIgnoreCase("Right Back")) &&
               players.stream().filter(p -> p.position.equalsIgnoreCase("Midfielder")).count() >= 3 &&
               players.stream().filter(p -> p.position.equalsIgnoreCase("Winger")).count() >= 2 &&
               players.stream().anyMatch(p -> p.position.equalsIgnoreCase("Forward"));
    }

    private static Player selectPlayerByPosition(ArrayList<Player> players, String position) {
        for (Player player : players) {
            if (player.position.equalsIgnoreCase(position)) {
                players.remove(player);
                return player;
            }
        }
        return null; // Esto no debería ocurrir si se valida correctamente
    }

    private static ArrayList<Player> selectPlayersByPosition(ArrayList<Player> players, String position, int count) {
        ArrayList<Player> selected = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Player player = selectPlayerByPosition(players, position);
            if (player != null) {
                selected.add(player);
            }
        }
        return selected;
    }
}
