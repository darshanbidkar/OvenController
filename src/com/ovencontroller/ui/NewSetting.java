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
 * @author darshanbidkar
 *
 */
public class NewSetting extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JPanel jPanel;

	public NewSetting(ProgramSettings currentSetting) {
		getContentPane().setLayout(null);
		setSize(572, 366);

		JLabel lblSettingName = new JLabel("Setting Name:");
		lblSettingName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblSettingName.setBounds(18, 17, 136, 27);
		getContentPane().add(lblSettingName);

		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		textField.setBounds(147, 18, 134, 28);
		getContentPane().add(textField);
		textField.setColumns(10);

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
				RecordsHandler.addProgramSettings(setting);
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
		
		JLabel lblNewLabel_1 = new JLabel("Time");
		lblNewLabel_1.setBounds(324, 67, 61, 22);
		getContentPane().add(lblNewLabel_1);
		setVisible(true);
	}

	/**
	 * Extracts the contents from dynamically added text fields to get the user
	 * input. It is then passed to the handler to save it in the file.
	 * 
	 * @return ProgramSettings object
	 */
	private ProgramSettings createProgramSettings() {
		String settingName = textField.getText();
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

	private void addNewEntry() {
		SettingEntry entry = new SettingEntry();
		jPanel.add(entry.getJp());
		scrollPane.revalidate();
	}

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

		/**
		 * @return the jp
		 */
		public JPanel getJp() {
			return jp;
		}
	}
}
