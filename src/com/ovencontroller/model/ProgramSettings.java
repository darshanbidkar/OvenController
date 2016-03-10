package com.ovencontroller.model;

import java.util.ArrayList;

public class ProgramSettings {

	private ArrayList<ProgramModel> models;
	private String name;
	private String totalTime;

	public ProgramSettings(ArrayList<ProgramModel> models) {

	}

	/**
	 * @return the models
	 */
	public ArrayList<ProgramModel> getModels() {
		return models;
	}

	/**
	 * @param models
	 *            the models to set
	 */
	public void setModels(ArrayList<ProgramModel> models) {
		this.models = models;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the totalTime
	 */
	public String getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

}
