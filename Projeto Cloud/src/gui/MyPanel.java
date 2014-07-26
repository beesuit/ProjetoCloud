package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Controller;

public class MyPanel extends JPanel {
	
	private String uuid;
	
	private JLabel titleLabel;
	private JLabel cpuLabel;
	private JLabel ramLabel;
	private JLabel uuidLabel;
	
	private JButton eraseButton;
	private JButton stopButton;
	private JButton restartButton;
	private JButton startButton;
	private JButton suspendButton;
	private JButton cloneButton;
	
	public MyPanel(String uuid) {
		this.uuid = uuid;
		
		titleLabel = new JLabel();
		titleLabel.setText("Title");
		cpuLabel = new JLabel();
		cpuLabel.setText("CPU");
		ramLabel = new JLabel();
		ramLabel.setText("RAM");
		uuidLabel = new JLabel();
		uuidLabel.setText("UUID");
		
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
		
		JPanel buttonPane = new JPanel();
		buttonPane.add(cloneButton);
		buttonPane.add(eraseButton);
		buttonPane.add(startButton);
		buttonPane.add(suspendButton);
		buttonPane.add(stopButton);
		buttonPane.add(restartButton);
		
		setLayout(new GridLayout(0,1));
		add(labelPane);
		add(buttonPane);
		setUpPanel();
	}
	
	public void setUpPanel(){
		
		titleLabel.setText(Controller.get().getVMName(uuid));
		cpuLabel.setText("CPU");
		ramLabel.setText(Controller.get().getVMMem(uuid));
		uuidLabel.setText(uuid);
		
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
		
	}

}
