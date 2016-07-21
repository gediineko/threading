package com.test.threading;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
public class Horse extends Thread{
	private boolean healthy;
	private int distance;
	private Random rand = new Random();
	private Race race;
	private static final int SPEED = 10;
	private static final int BOOST_SPEED = 20;
	Horse(String name, Race race){
			super(name);
			this.healthy = rand.nextBoolean();
			this.distance = 0;
			this.race = race;
	}
	public void run(){
		gallop(Race.BARN_TO_GATE_DISTANCE);
		race.waitInGate();
		distance = 0;
		gallop(Race.gateToFinDistance);
		System.out.println("[" + getName() + " reached the Finish Line]");
		race.addFinishedHorse(this);
		if (race.isAllFinished()){
			System.out.println("\n" + race.getWinner().getName() + " WINS!\n");
		}
	}
	private void gallop(int limit){
		while(distance < limit){
			//Sort the horses order per turn
			List<Horse> horseOrder = race.getHorses()
				.stream()
				.sorted((h1, h2) -> Integer.compare(h1.getDistance(), h2.getDistance()))
				.collect(Collectors.toList());
			//Boost horse if they are trailing
			boolean boost = horseOrder
				.get(0)
				.getName()
				.equals(getName());
			distance += (rand.nextInt(boost && race.isAllInGate() ? BOOST_SPEED : SPEED)+1);
			//Make sure horse will not go over the barn or finish line
			if (!race.isAllInGate() && distance > Race.BARN_TO_GATE_DISTANCE) {
			 	distance = Race.BARN_TO_GATE_DISTANCE;
			} else if (race.isAllInGate() && distance > Race.gateToFinDistance){
				distance = Race.gateToFinDistance;
			}
			printStatus(limit, boost);	
		}
	}
	public boolean isHealthy(){
		return healthy;
	}
	public int getDistance(){
		return distance;
	}
	public void printStatus(int limit, boolean boost){
		System.out.println((boost && race.isAllInGate() ? "Boost! " : "") 
			+ getName() 
			+ " at " 
			+ distance 
			+ "m; " 
			+ (limit-distance) 
			+ "m remaining " /*+ System.currentTimeMillis()*/);
	}
	
}