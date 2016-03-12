/**
 * 
 */
package com.ovencontroller.ui;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.ovencontroller.Process;
import com.ovencontroller.model.ProgramModel;
import com.ovencontroller.model.ProgramSettings;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
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
		setSize(576, 300);
		setResizable(false);
		getContentPane().setLayout(null);

		lblTimeLabel = new JLabel("");
		lblTimeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTimeLabel.setBounds(414, 19, 126, 32);
		getContentPane().add(lblTimeLabel);

		lblStatusLabel = new JLabel("");
		lblStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblStatusLabel.setBounds(55, 100, 226, 45);
		getContentPane().add(lblStatusLabel);

		lblCurrentTempLabel = new JLabel("");
		lblCurrentTempLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblCurrentTempLabel.setBounds(319, 100, 221, 45);
		getContentPane().add(lblCurrentTempLabel);
		
		lblTimeRemainingFor = new JLabel("Time Remaining for current cycle:");
		lblTimeRemainingFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimeRemainingFor.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTimeRemainingFor.setBounds(24, 19, 335, 32);
		getContentPane().add(lblTimeRemainingFor);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				counter = false;
				dispose();
				new ProgramSelectionScreen();
			}
		});
		btnNewButton.setIcon(new ImageIcon(OvenControllerScreen.class.getResource("/com/ovencontroller/185-poweroff.png")));
		btnNewButton.setBounds(221, 188, 88, 84);
		getContentPane().add(btnNewButton);

		setVisible(true);
		isRunning = false;
		new Thread() {
			public void run() {
				for (ProgramModel model : settings.getModels()) {
					if (!counter) return;
					isRunning = true;
					startTimer(model);
					while (isRunning);
				}
				lblStatusLabel.setText("<html>Status: <font color='green'>Finished</font></html>");
			}
		}.start();
	}

	private void setStatus(boolean isCooling) {
		String coolingStatus = "<html>Status: <font color='blue'>Cooling</font></html>";
		String heatingStatus = "<html>Status: <font color='red'>Heating</font></html>";
		lblStatusLabel.setText(isCooling ? coolingStatus : heatingStatus);
		lblStatusLabel.revalidate();
	}

	/**
	 * Reference: stackoverflow.com
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

	private void startTimer(ProgramModel model) {
		Process process = new Process(model.getStartTemp(), model.getEndTemp(),
				model.getDuration());
		process.startProcess();
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
				process.stopProcess();
			}
		};
		timer.schedule(task, 0);
	}
}
