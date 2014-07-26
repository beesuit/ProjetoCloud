
package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import control.Controller;

public class Main extends JFrame {
    DefaultListModel<String> listModel;
    JList<String> list;
    final Button loadButton;
    JComboBox cpuCB;
    JComboBox ramCB;
    Button alertStartButton;
    Button alertStopButton;

    public Main() {
        JPanel container = new JPanel(new BorderLayout());
        final JPanel vmsPane = new JPanel();
        JPanel menuPane = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(vmsPane);

        vmsPane.setLayout(new BoxLayout(vmsPane, BoxLayout.PAGE_AXIS));
        loadButton = new Button("Carregar");
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Controller.get().connect();
                Controller.get().getAllVMs();
                vmsPane.removeAll();
                vmsPane.add(Box.createVerticalStrut(50));
                vmsPane.add(new HostPanel());
                vmsPane.add(Box.createVerticalStrut(50));
                vmsPane.add(new JSeparator(SwingConstants.HORIZONTAL));
                vmsPane.add(Box.createVerticalStrut(50));

                for (String i : Controller.get().getUuidList()) {
                    MyPanel pane = new MyPanel(i);
                    vmsPane.add(pane);
                    vmsPane.add(Box.createVerticalStrut(50));
                    vmsPane.add(new JSeparator(SwingConstants.HORIZONTAL));
                    vmsPane.add(Box.createVerticalStrut(50));
                }

                scrollPane.validate();
            }

        });

        String[] thresholds = {
                "50%", "55%", "60%", "65%", "70%", "75%", "80%", "85%", "90%", "95%"
        };

        cpuCB = new JComboBox(thresholds);
        ramCB = new JComboBox(thresholds);

        JLabel cpuAlert = new JLabel("Alerta CPU: ");
        JLabel ramAlert = new JLabel("Alerta RAM: ");

        alertStartButton = new Button("Start");
        alertStartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                // inicia thread de monitoramento e envio de email
                int cpu = 50 + cpuCB.getSelectedIndex() * 5;
                int ram = 50 + ramCB.getSelectedIndex() * 5;
                Controller.get().startAlertThread(cpu, ram);
            }

        });

        alertStopButton = new Button("Stop");
        alertStopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Controller.get().stopAlertThread();
            }

        });

        menuPane.add(loadButton);
        menuPane.add(cpuAlert);
        menuPane.add(cpuCB);
        menuPane.add(ramAlert);
        menuPane.add(ramCB);
        menuPane.add(alertStartButton);
        menuPane.add(alertStopButton);
        container.add(menuPane, BorderLayout.PAGE_START);
        container.add(scrollPane, BorderLayout.CENTER);

        // scrollPane.add(mainPane);

        // TODO
        // conectar e recuperar todas as VMs do host

        this.add(container);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(960, 680);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }

}
