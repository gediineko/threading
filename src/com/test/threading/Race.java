package com.test.threading;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.InputMismatchException;
public class Race {
	private Scanner sc = new Scanner(System.in);
	private List<Horse> horses = new ArrayList<Horse>();
	private int horseInGate = 0;
	public static final int BARN_TO_GATE_DISTANCE = 10;
	public static int gateToFinDistance;
	private List<Horse> finishedHorses = new LinkedList<>();
	public void start(){
		for (Horse horse : horses){
			horse.start();
		}
	}
	public List<Horse> getHorses(){
		return horses;
	}
	public int getHorseCount(){
		return horses.size();
	}
	public void addHorses(Horse horse){
		horses.add(horse);
	}
	public void resetHorses(){
		horses.clear();
	}
	public boolean isAllInGate(){
		return horses.size() == horseInGate;
	}
	public void addFinishedHorse(Horse horse){
		finishedHorses.add(horse);
	}
	public boolean isAllFinished(){
		return finishedHorses.size() == getHorseCount();
	}
	public Horse getWinner(){
		return finishedHorses.get(0);
	}
	public synchronized void waitInGate(){
		horseInGate++;
		if (isAllInGate()){
			System.out.println("All horses are in the Gate!");
			System.out.println("\n[Set distance from Gate to Finish Line]");
			do {
				try {
					System.out.println("Must be more than 10 meters.");
					gateToFinDistance = sc.nextInt();
				} catch (InputMismatchException ex){
					System.out.println("[Invalid distance]");
					sc.nextLine();
					gateToFinDistance = 0;
				}
			} while (gateToFinDistance <= 10);
			System.out.println("\nRACE START!\n\n");
			notifyAll();
		} else {
			try{
				System.out.println(Thread.currentThread().getName() + " waiting at the gate.");
				wait();
			} catch(Exception ex){ex.printStackTrace();}
			
		}
	}

}