/**
 * 
 */
package com.ovencontroller.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.ovencontroller.Process;
import com.ovencontroller.model.ProgramModel;
import com.ovencontroller.model.ProgramSettings;

/**
 * Controls oven. Builds and deals with UI.
 * 
 * @author darshanbidkar
 *
 */
public class OvenControllerScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblTimeLabel;
	private JLabel lblStatusLabel;
	private JLabel lblCurrentTempLabel;
	private Timer timer;
	private volatile boolean isRunning, counter = true;
	private JLabel lblTimeRemainingFor;

	public OvenControllerScreen(ProgramSettings settings) {
		setTitle("Oven Controller Status");
		setSize(693, 300);
		setResizable(false);
		getContentPane().setLayout(null);

		lblTimeLabel = new JLabel("");
		lblTimeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lblTimeLabel.setBounds(513, 19, 126, 32);
		getContentPane().add(lblTimeLabel);

		lblStatusLabel = new JLabel("");
		lblStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		lblStatusLabel.setBounds(55, 100, 303, 45);
		getContentPane().add(lblStatusLabel);

		lblCurrentTempLabel = new JLabel("");
		lblCurrentTempLabel.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		lblCurrentTempLabel.setBounds(513, 100, 221, 45);
		getContentPane().add(lblCurrentTempLabel);

		lblTimeRemainingFor = new JLabel("Time Remaining for current cycle:");
		lblTimeRemainingFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimeRemainingFor.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lblTimeRemainingFor.setBounds(24, 19, 436, 32);
		getContentPane().add(lblTimeRemainingFor);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				counter = false;
				dispose();
				new ProgramSelectionScreen();
			}
		});
		btnNewButton.setIcon(new ImageIcon(OvenControllerScreen.class
				.getResource("/com/ovencontroller/185-poweroff.png")));
		btnNewButton.setBounds(513, 188, 88, 84);
		getContentPane().add(btnNewButton);

		JLabel lblSystemTimeLabel = new JLabel("");
		lblSystemTimeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		lblSystemTimeLabel.setBounds(55, 212, 436, 39);
		getContentPane().add(lblSystemTimeLabel);

		setVisible(true);
		isRunning = false;
		// start the thread for all program models.
		new Thread() {
			public void run() {
				for (ProgramModel model : settings.getModels()) {
					if (!counter)
						return;
					isRunning = true;
					startTimer(model);
					while (isRunning)
						;
				}
				lblStatusLabel
						.setText("<html>Status: <font color='green'>Finished</font></html>");
			}
		}.start();

		// start thread for the system time.
		new Thread() {
			public void run() {
				long time = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				while (counter) {
					Date resultdate = new Date(time);
					lblSystemTimeLabel.setText("System Time: "
							+ sdf.format(resultdate));
					time += 1000;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	// Sets current status of oven. cooling or heating.
	private void setStatus(boolean isCooling) {
		String coolingStatus = "<html>Status: <font color='blue'>Cooling</font></html>";
		String heatingStatus = "<html>Status: <font color='red'>Heating</font></html>";
		lblStatusLabel.setText(isCooling ? coolingStatus : heatingStatus);
		lblStatusLabel.revalidate();
	}

	/**
	 * Reference: stackoverflow.com
	 * Converts time in milli seconds to hh:mm:ss format.
	 * 
	 * @param millis
	 * @return
	 */
	private String getTime(long millis) {
		// hh:mm:ss
		return String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)));
	}

	// Turns on the timer for current cycle.
	private void startTimer(ProgramModel model) {
		Process process = new Process(model.getStartTemp(), model.getEndTemp(),
				model.getDuration());
		process.turnOnOven();
		timer = new Timer();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				long time;
				while (counter && (time = process.getTime()) >= 0) {
					setStatus(process.isCooling());
					System.out.println("Time: " + getTime(time));
					lblTimeLabel.setText(getTime(time));
					lblCurrentTempLabel.setText(process.getCurrentTemp()
							+ "\u00B0" + "F");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				timer.cancel();
				isRunning = false;
				process.turnOffOven();
			}
		};
		timer.schedule(task, 0);
	}
}
