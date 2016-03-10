/**
 * 
 */
package com.ovencontroller.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.ovencontroller.model.ProgramModel;
import com.ovencontroller.model.ProgramSettings;

/**
 * @author darshanbidkar
 *
 */
public class ProgramSelectionScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private ProgramSettings settings;
	JLabel lblProgramSelection;
	JScrollPane scrollPane;

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
		btnStart.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnStart.setBounds(187, 275, 139, 34);
		getContentPane().add(btnStart);

		settings = getPrograms();

		setVisible(true);
	}

	private ProgramSettings getPrograms() {
		ArrayList<ProgramModel> models = new ArrayList<ProgramModel>();

		// File read code.

		ProgramSettings settings = new ProgramSettings(models);
		setRadioButtons(settings);
		return settings;
	}

	private void setRadioButtons(ProgramSettings settings) {
		ButtonGroup radioGroup = new ButtonGroup();
		JRadioButton radioButton;
		JPanel jp = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jp, BoxLayout.Y_AXIS);
		jp.setLayout(boxLayout);
		// TODO change code.
		for (int i = 0; i < 50; i++) {
			radioButton = new JRadioButton();
			radioButton.setText("Name, 10:00");
			radioButton.setLayout(new FlowLayout(FlowLayout.LEFT));
			radioGroup.add(radioButton);

			jp.add(radioButton);
		}
		scrollPane = new JScrollPane(jp);
		scrollPane.setBounds(16, 130, 465, 122);
		getContentPane().add(scrollPane);
	}
}
