package com.test.threading;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
public class App {
	private Scanner	scanner;
	private Race race;
	private List<Horse> horseInput = new ArrayList<>();
	App(){
		scanner = new Scanner(System.in);
		race = new Race();
	}
	public static void main (String[] args){
		new App().run();
	}
	public void run(){
		System.out.println("[Horse Racing App: Register horses]");
		do {
			int horseCount = 0;
			race.resetHorses();
			horseInput.clear();
			System.out.print("Number of horses: ");
			try {
				horseCount = scanner.nextInt();
				System.out.println("");
				for (int x = 0; x < horseCount; x++){
					Horse horse = new Horse("horse" + (x+1), race);
					horseInput.add(horse);
					System.out.println(horse.getName() 
						+ (horse.isHealthy() 
							? ": Healthy, registered to the Race" 
							: ": Not healthy"));
				}
				//Filter healthy horses from horseInput to raceHorses
				race.raceHorses = horseInput
				  .stream()
                  .filter(Horse::isHealthy) /*h -> h.isHealthy()*/
                  .map(h -> {
                    h.setName(h.getName().toUpperCase());
                    return h;
                    })
                  .collect(Collectors.toList());
                //Make sure there are 2 or more healthy horses
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