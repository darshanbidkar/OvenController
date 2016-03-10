/**
 * 
 */
package com.ovencontroller.model;

/**
 * @author darshanbidkar
 *
 */
public class ProgramModel {
	private int startTemp;
	private int endTemp;
	private int hours;
	private int minutes;
	private long duration;

	public ProgramModel(int startTemp, int endTemp, int duration) {
		this.startTemp = startTemp;
		this.endTemp = endTemp;
		this.duration = duration;
		this.hours = duration / 60;
		this.minutes = duration % 60;
	}

	/**
	 * @return the startTemp
	 */
	public int getStartTemp() {
		return startTemp;
	}

	/**
	 * @param startTemp
	 *            the startTemp to set
	 */
	public void setStartTemp(int startTemp) {
		this.startTemp = startTemp;
	}

	/**
	 * @return the endTemp
	 */
	public int getEndTemp() {
		return endTemp;
	}

	/**
	 * @param endTemp
	 *            the endTemp to set
	 */
	public void setEndTemp(int endTemp) {
		this.endTemp = endTemp;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @param hours
	 *            the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes
	 *            the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
