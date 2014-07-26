package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.Controller;

public class MyPanel extends JPanel {
	
	private String uuid;
	
	private JLabel titleLabel;
	private JLabel cpuLabel;
	private JLabel ramLabel;
	private JLabel uuidLabel;
	
	private JLabel cpuSettingsLabel;
	private JTextField cpuValueField;
	private JButton cpuOKButton;
	private JLabel ramSettingsLabel;
	private JTextField ramValueField;
	private JButton ramOKButton;
	
	
	private JButton eraseButton;
	private JButton stopButton;
	private JButton restartButton;
	private JButton startButton;
	private JButton suspendButton;
	private JButton cloneButton;
	
	public MyPanel(String uuid) {
		this.uuid = uuid;
		
		//label pane
		titleLabel = new JLabel();
		titleLabel.setText("Title");
		cpuLabel = new JLabel();
		cpuLabel.setText("CPU");
		ramLabel = new JLabel();
		ramLabel.setText("RAM");
		uuidLabel = new JLabel();
		uuidLabel.setText("UUID");
		
		//settings pane
		cpuSettingsLabel = new JLabel();
		cpuValueField = new JTextField(10);
		cpuOKButton = new JButton();
		ramSettingsLabel = new JLabel();
		ramValueField = new JTextField(10);
		ramOKButton = new JButton();
		
		//button pane
		cloneButton = new JButton();
		eraseButton = new JButton();
		stopButton = new JButton();
		restartButton = new JButton();
		startButton = new JButton();
		suspendButton = new JButton();
		
		
		JPanel labelPane = new JPanel();
		labelPane.add(titleLabel);
		labelPane.add(cpuLabel);
		labelPane.add(ramLabel);
		labelPane.add(uuidLabel);
		
		//TODO
		//middle pane
		
		JPanel settingsPane = new JPanel();
		settingsPane.add(cpuSettingsLabel);
		settingsPane.add(cpuValueField);
		settingsPane.add(cpuOKButton);
		
		settingsPane.add(ramSettingsLabel);
		settingsPane.add(ramValueField);
		settingsPane.add(ramOKButton);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.add(cloneButton);
		buttonPane.add(eraseButton);
		buttonPane.add(startButton);
		buttonPane.add(suspendButton);
		buttonPane.add(stopButton);
		buttonPane.add(restartButton);
		
		setLayout(new GridLayout(0,1));
		add(labelPane);
		add(settingsPane);
		add(buttonPane);
		
		setUpPanel();
	}
	
	public void setUpPanel(){
		
		titleLabel.setText(Controller.get().getVMName(uuid));
		cpuLabel.setText("CPU");
		ramLabel.setText(Controller.get().getVMMem(uuid));
		uuidLabel.setText(uuid);
		
		cpuSettingsLabel.setText("Nº de vCPUs");
		ramSettingsLabel.setText("Quantidade de RAM");
		
		cloneButton.setText("Clonar VM");
		cloneButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().cloneVM(uuid);
			}
			
		});
		
		eraseButton.setText("Apagar");
		eraseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().eraseVM(uuid);
			}
			
		});
		
		stopButton.setText("Parar");
		stopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().stopVM(uuid);
			}
			
		});
		
		restartButton.setText("Reiniciar");
		restartButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().restartVM(uuid);
			}
			
		});
		
		startButton.setText("Iniciar");
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().startVM(uuid);
			}
			
		});
		
		suspendButton.setText("Suspender");
		suspendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.get().suspendVM(uuid);
			}
			
		});
		
		cpuOKButton.setText("OK");
		cpuOKButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String value = cpuValueField.getText();
				if(value != null){
					Controller.get().setVMVCPUs(uuid, Long.valueOf(value));
				}
				
			}
			
		});
		
		ramOKButton.setText("OK");
		ramOKButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = ramValueField.getText();
				if(value != null){
					Controller.get().setVMRam(uuid, Long.valueOf(value));
				}
			}
			
		});
	}

}
