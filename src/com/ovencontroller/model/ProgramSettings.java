package com.ovencontroller.model;

import java.util.ArrayList;

public class ProgramSettings {

	private ArrayList<ProgramModel> models;
	private String name;
	private String totalTime;
	private long duration;

	public ProgramSettings(String name, ArrayList<ProgramModel> models) {
		this.name = name;
		this.models = models;
		calculateTotalTime();
	}

	/**
	 * Calculates total time based on setting models
	 */
    private void calculateTotalTime() {
        duration = 0;
        for (ProgramModel model : models) {
            duration += model.getDuration();
        }
        totalTime = String.format("%02d hrs %02d mins", (duration / 60), (duration % 60));
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

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append(name).append("\t");
	    for(ProgramModel model : models) {
	        builder.append(model.getStartTemp())
	               .append(",")
	               .append(model.getEndTemp())
	               .append(",")
	               .append(model.getDuration())
	               .append("\t");
	    }
	    return builder.toString();
	}
}
