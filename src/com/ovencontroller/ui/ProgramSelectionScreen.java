/**
 * 
 */
package com.ovencontroller.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.ovencontroller.model.ProgramSettings;
import com.ovencontroller.utils.InputReader;

/**
 * @author darshanbidkar
 *
 */
public class ProgramSelectionScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<ProgramSettings> settings;
	private JLabel lblProgramSelection;
	private JScrollPane scrollPane;
	private ProgramSettings currentSetting;

	public ProgramSelectionScreen() {
		setSize(500, 345);
		setTitle("Oven Controller");
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblOvenController = new JLabel("Oven Controller");
		lblOvenController.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lblOvenController.setHorizontalAlignment(SwingConstants.CENTER);
		lblOvenController.setBounds(104, 24, 284, 34);
		getContentPane().add(lblOvenController);

		lblProgramSelection = new JLabel("Select a setting:");
		lblProgramSelection.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblProgramSelection.setBounds(6, 84, 164, 34);
		getContentPane().add(lblProgramSelection);

		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new NewSetting();
			}
		});
		btnAddNew.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnAddNew.setBounds(338, 275, 143, 34);
		getContentPane().add(btnAddNew);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OvenControllerScreen(currentSetting);
			}
		});
		btnStart.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnStart.setBounds(187, 275, 139, 34);
		getContentPane().add(btnStart);

		settings = getPrograms();
		setRadioButtons();

		setVisible(true);
	}

	private ArrayList<ProgramSettings> getPrograms() {
		ArrayList<ProgramSettings> settings = InputReader.getSavedSettings();
		return settings;
	}

	private void setRadioButtons() {
		ButtonGroup radioGroup = new ButtonGroup();
		JRadioButton radioButton;
		JPanel jp = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jp, BoxLayout.Y_AXIS);
		jp.setLayout(boxLayout);
		// TODO change code.
		for (ProgramSettings setting : settings) {
			radioButton = new JRadioButton();
			radioButton.setText(setting.getName() + ", "
					+ setting.getTotalTime());
			radioButton.setLayout(new FlowLayout(FlowLayout.LEFT));
			radioGroup.add(radioButton);
			radioButton.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					currentSetting = setting;
				}
			});
			jp.add(radioButton);
		}

		scrollPane = new JScrollPane(jp);
		scrollPane.setBounds(16, 130, 465, 122);
		getContentPane().add(scrollPane);
	}
}
