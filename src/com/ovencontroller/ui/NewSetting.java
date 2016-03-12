/**
 * 
 */
package com.ovencontroller.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.ovencontroller.model.ProgramModel;
import com.ovencontroller.model.ProgramSettings;
import com.ovencontroller.utils.RecordsHandler;

/**
 * Handles UI for new setting cycle by the user.
 * 
 * @author darshanbidkar
 *
 */
public class NewSetting extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nameTextField;
	private JScrollPane scrollPane;
	private JPanel jPanel;

	public NewSetting(ProgramSettings currentSetting) {
		getContentPane().setLayout(null);
		setSize(572, 366);

		JLabel lblSettingName = new JLabel("Setting Name:");
		lblSettingName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblSettingName.setBounds(18, 17, 136, 27);
		getContentPane().add(lblSettingName);

		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		nameTextField.setBounds(147, 18, 134, 28);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(jPanel);
		scrollPane.setBounds(18, 90, 536, 178);
		getContentPane().add(scrollPane);

		JButton btnAddNew = new JButton("New Entry");
		btnAddNew.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewEntry();
			}
		});
		btnAddNew.setBounds(273, 280, 136, 45);
		getContentPane().add(btnAddNew);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgramSettings setting = createProgramSettings();
				if (currentSetting != null) {
					RecordsHandler.updateProgramSettings(setting);
				} else {
					RecordsHandler.addProgramSettings(setting);
				}
				dispose();
				new ProgramSelectionScreen();
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnNewButton.setBounds(421, 280, 133, 45);
		getContentPane().add(btnNewButton);

		JLabel lblStartTemperature = new JLabel("Start Temperature");
		lblStartTemperature.setBounds(28, 67, 136, 22);
		getContentPane().add(lblStartTemperature);

		JLabel lblNewLabel = new JLabel("End Temperature");
		lblNewLabel.setBounds(176, 68, 136, 20);
		getContentPane().add(lblNewLabel);

		JLabel lblTimeLabel = new JLabel("Time (in minutes)");
		lblTimeLabel.setBounds(324, 67, 110, 22);
		getContentPane().add(lblTimeLabel);

		if (currentSetting != null) {
			populate(currentSetting);
		}

		setVisible(true);
	}

	// Populates pre filled data to the controls in case of edit scenario.
	private void populate(ProgramSettings currentSettings) {
		nameTextField.setText(currentSettings.getName());
		nameTextField.setEditable(false);
		for (ProgramModel model : currentSettings.getModels()) {
			SettingEntry entry = new SettingEntry();
			entry.setDuration(model.getDuration());
			entry.setEndTemp(model.getEndTemp());
			entry.setStartTemp(model.getStartTemp());
			jPanel.add(entry.getJp());
		}
	}

	/**
	 * Extracts the contents from dynamically added text fields to get the user
	 * input. It is then passed to the handler to save it in the file.
	 * 
	 * @return ProgramSettings object
	 */
	private ProgramSettings createProgramSettings() {
		String settingName = nameTextField.getText();
		ArrayList<ProgramModel> models = new ArrayList<>();
		Component[] innerPanels = jPanel.getComponents();
		for (Component component : innerPanels) {
			JPanel panel = (JPanel) component;
			JTextField field0 = (JTextField) panel.getComponent(0);
			JTextField field1 = (JTextField) panel.getComponent(1);
			JTextField field2 = (JTextField) panel.getComponent(2);
			int startTemperature = Integer.parseInt(field0.getText());
			int endTemperature = Integer.parseInt(field1.getText());
			int durationTime = Integer.parseInt(field2.getText());
			models.add(new ProgramModel(startTemperature, endTemperature,
					durationTime));
		}
		return new ProgramSettings(settingName, models);
	}

	// Adds new entry to the scroll pane.
	private void addNewEntry() {
		SettingEntry entry = new SettingEntry();
		jPanel.add(entry.getJp());
		scrollPane.revalidate();
	}

	// Removes entry from scrollpane.
	private void removeEntry(JPanel jp) {
		if (jPanel.getComponents().length == 1) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
			scrollPane.setViewportView(jPanel);
			return;
		}
		jPanel.remove(jp);
		jPanel.revalidate();
	}

	// SettingEntry class is a dummy UI class that acts as a placeholder.
	private class SettingEntry {
		JPanel jp;
		JTextField startTemp, endTemp, duration;
		JButton remove;

		public SettingEntry() {
			jp = new JPanel();
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));

			startTemp = new JTextField(10);
			endTemp = new JTextField(10);
			duration = new JTextField(10);
			remove = new JButton("Remove");
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					removeEntry(jp);
				}
			});

			jp.add(startTemp);
			jp.add(endTemp);
			jp.add(duration);
			jp.add(remove);
		}

		public void setStartTemp(int temp) {
			startTemp.setText(temp + "");
		}

		public void setEndTemp(int temp) {
			endTemp.setText(temp + "");
		}

		public void setDuration(long time) {
			duration.setText(time + "");
		}

		/**
		 * @return the jp
		 */
		public JPanel getJp() {
			return jp;
		}
	}
}
