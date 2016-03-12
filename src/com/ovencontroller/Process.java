package com.ovencontroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Process class that is the heart of the program. Deals with various operations
 * such as: turn on turn off get current temp
 * 
 * @author darshanbidkar
 *
 */
public class Process {

	private double currentTemp;
	private double tempIdeal;
	private double endTemp;
	private long duration;
	private boolean isCooling;
	private double idealRate;
	private static final double HEATING_FACTOR = 1;
	private static final double COOLING_FACTOR = -0.5;

	public Process(double start, double end, long time) {
		currentTemp = start;
		tempIdeal = start;
		endTemp = end;
		duration = time;
		isCooling = endTemp < currentTemp;
		idealRate = (endTemp - currentTemp) / duration;
	}

	// turns on the oven.
	public void turnOnOven() {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				while (duration >= 0) {
					double delta = isCooling ? COOLING_FACTOR : HEATING_FACTOR;
					currentTemp += delta;
					tempIdeal += idealRate;
					if (currentTemp - tempIdeal > 0) {
						isCooling = true;
					} else if (currentTemp - tempIdeal < 0) {
						isCooling = false;
					}
					String status = isCooling ? "Cooling" : "Heating";
					System.out.println("Status : " + status + "\tIdeal : "
							+ tempIdeal + "\t\tActual : " + currentTemp);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// timer tick.
					duration -= 1;
				}
				timer.cancel();
			}
		};
		timer.schedule(task, 0);
	}

	// returns time.
	public long getTime() {
		return duration * 1000;
	}

	public boolean isCooling() {
		return isCooling;
	}

	// Returns current temperature.
	public int getCurrentTemp() {
		return (int) currentTemp;
	}

	// turns off the oven.
	public void turnOffOven() {
		duration = 0;
	}
}
