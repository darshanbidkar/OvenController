package com.ovencontroller;

import java.util.Timer;
import java.util.TimerTask;

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

	public void startProcess() {

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
					duration -= 1;
				}
				timer.cancel();
			}
		};
		timer.schedule(task, 0);
	}

	public long getTime() {
		return duration * 1000;
	}

	public boolean isCooling() {
		return isCooling;
	}

	public int getCurrentTemp() {
		return (int) currentTemp;
	}
	
	public void stopProcess() {
		duration = 0;
	}

	public static void main(String[] args) {
		new Process(900, 890, 100).startProcess();
	}
}
