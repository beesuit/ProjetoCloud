package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import control.Controller;

public class Main extends JFrame{
	DefaultListModel<String> listModel;
	JList<String> list;
	final Button loadButton;
	Button createButton;
	int a;

	public Main() {
		// TODO Auto-generated constructor stub
		a = 1;
		listModel = new DefaultListModel<String>();
		listModel.addElement("Jane Doe");
		listModel.addElement("John Smith");
		listModel.addElement("Kathy Green");

		list = new JList<String>(listModel);
		
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		loadButton = new Button();
		loadButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				/*Main main = new Main();
				main.setSize(200,200);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setVisible(true);*/
				
				//add(new MyPanel());
				
			}
			
		});
		
		createButton = new Button();
		

		Timer timer = new Timer(500, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//loadButton.setLabel(getText());
				
			}
			
		});
		
		timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
		
		JPanel panel = new JPanel();
		panel.add(loadButton);
		panel.add(listScroller);
		this.add(panel);

	}
	
	public Main(boolean value){
		JPanel container = new JPanel(new BorderLayout());
		final JPanel vmsPane = new JPanel();
		JPanel menuPane = new JPanel();
		final JScrollPane scrollPane = new JScrollPane(vmsPane);
		
		
		vmsPane.setLayout(new GridLayout(0,1));
		loadButton = new Button("Carregar");
		loadButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
				Controller.get().connect();
				Controller.get().getAllVMs();
				vmsPane.removeAll();
				for(String i : Controller.get().getUuidList()){
					MyPanel pane = new MyPanel(i);
					vmsPane.add(pane);
				}
				
				
				
				//vmsPane.add(new JLabel(Controller.get().getFirst()));
				Controller.get().getFirst();
				scrollPane.validate();
			}
			
		});
		
		createButton = new Button("Criar");
		createButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO
				System.out.println("Create");
				
			}
			
		});
		
		menuPane.add(createButton);
		menuPane.add(loadButton);
		container.add(menuPane, BorderLayout.PAGE_START);
		container.add(scrollPane, BorderLayout.CENTER);
		
		//scrollPane.add(mainPane);
		
		//TODO
		//conectar e recuperar todas as VMs do host
		
		this.add(container);		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main(true);
		main.setSize(800, 600);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}

}
