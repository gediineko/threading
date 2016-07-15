package com.test.threading;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
public class App {
	private Scanner	scanner;
	private Race race;
	App(){
		scanner = new Scanner(System.in);
		race = new Race();
	}
	public static void main (String[] args){
		new App().run();
	}
	public void run(){
		System.out.println("[Horse Racing App: Register horses, must be more than 1]");
		do {
			int horseCount = 0;
			race.resetHorses();
			System.out.print("Number of horses: ");
			try {
				horseCount = scanner.nextInt();
				System.out.println("");
				for (int x = 0; x < horseCount; x++){
					Horse horse = new Horse("HORSE " + (x+1), race);
					if (horse.isHealthy()){
						race.addHorses(horse);
						System.out.println(horse.getName() + ": Healthy, registered to the Race.");
					} else {
						System.out.println(horse.getName() + ": Not healthy.");
					}
				}
				if (race.getHorseCount() < 2){
					System.out.println("\nNot enough healthy horses.");
					System.out.println("We need more than 1 healthy horses to start the race.");
				}
			} catch (InputMismatchException ex){
				System.out.println("[Invalid input]");
				scanner.nextLine();
			}

		} while (race.getHorseCount() < 2);
		race.start();
	}
}