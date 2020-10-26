package lab;

import java.util.Scanner;

public class World {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Animal animal1 = new Animal();

        System.out.println(animal1.toString());
        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.BACKWARD);
        animal1.move(MoveDirection.BACKWARD);
        animal1.move(MoveDirection.RIGHT);
        System.out.println(animal1.toString());

        Animal animal2 = new Animal();

        System.out.println("Podaj liczbę ruchów które chcesz wprowadzić: ");
        int numberOfMoves = input.nextInt();
        input.nextLine();

        String[] rawMoves = new String[numberOfMoves];
        System.out.println("Podaj " + numberOfMoves + " kolejnych ruchów: ");
        for(int i=0; i<numberOfMoves; i++) {
            rawMoves[i] = input.nextLine();
        }

        OptionsParser converter = new OptionsParser();
        MoveDirection[] convertedMoves = converter.parse(rawMoves);

        for(int i=0; i<numberOfMoves; i++) {
            animal2.move(convertedMoves[i]);
        }

        System.out.println(animal2.toString());
    }
}
