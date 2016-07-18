package com.test.threading;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.InputMismatchException;
public class Race {
	private Scanner sc = new Scanner(System.in);
	public List<Horse> raceHorses = new ArrayList<Horse>();
	private int horseInGate = 0;
	public static final int BARN_TO_GATE_DISTANCE = 10;
	public static int gateToFinDistance;
	private List<Horse> finishedHorses = new LinkedList<>();
	public void start(){
		for (Horse horse : raceHorses){
			horse.start();
		}
	}
	public void resetHorses(){
		raceHorses.clear();
	}
	public List<Horse> getHorses(){
		return raceHorses;
	}
	public int getHorseCount(){
		return raceHorses.size();
	}
	public void addHorses(Horse horse){
		raceHorses.add(horse);
	}
	public boolean isAllInGate(){
		return raceHorses.size() == horseInGate;
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
			System.out.println("[All horses are in the Gate]");
			System.out.println("\n[Set distance from Gate to Finish Line]");
			do {
				try {
					System.out.println("Must be more than 10 meters");
					gateToFinDistance = sc.nextInt();
				} catch (InputMismatchException ex){
					System.out.println("[Invalid distance]");
					sc.nextLine();
					gateToFinDistance = 0;
				}
			} while (gateToFinDistance <= 10);
			System.out.println("\nRACE START!\n");
			notifyAll();
		} else {
			try{
				System.out.println("[" + Thread.currentThread().getName() + " waiting at the gate]");
				wait();
			} catch(Exception ex){ex.printStackTrace();}
			
		}
	}

}