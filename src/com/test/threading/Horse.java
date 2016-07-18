package com.test.threading;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
public class Horse extends Thread{
	private boolean healthy;
	private int distance;
	private Random rand = new Random();
	private Race race;
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
			System.out.println(race.getWinner().getName() + " WINS!");
		}
	}
	private void gallop(int limit){
		while(distance < limit){
			step();
			if (limit == Race.BARN_TO_GATE_DISTANCE && distance > Race.BARN_TO_GATE_DISTANCE) {
			 	distance = 10;
			} else if (limit == Race.gateToFinDistance && distance > Race.gateToFinDistance){
				distance = Race.gateToFinDistance;
			}
			System.out.println(getName() + " at " + distance + "m; " + (limit-distance) + "m remaining" );
		}
	}
	public boolean isHealthy(){
		return healthy;
	}
	public void step(){
		List<Horse> horses = race.getHorses()
			.stream()
			.sorted((h1, h2) -> Integer.compare(h1.getDistance(), h2.getDistance()))
			.collect(Collectors.toList());
		boolean boost = horses.get(0).getName().equals(getName());
		distance += (rand.nextInt(boost && race.isAllInGate() ? 20 : 10)+1);
	}
	public int getDistance(){
		return distance;
	}
	
}