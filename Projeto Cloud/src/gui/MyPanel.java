
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import control.Controller;

public class MyPanel extends JPanel {

    private String uuid;

    private JLabel titleLabel;
    private JLabel cpuLabel;
    private JLabel ramLabel;
    private JLabel uuidLabel;
    private JLabel stateLabel;

    private JLabel cpuSettingsLabel;
    private JComboBox cpuValueCB;
    private JButton cpuOKButton;

    private JLabel ramSettingsLabel;
    private JComboBox ramValueCB;
    private JButton ramOKButton;

    private JButton eraseButton;
    private JButton stopButton;
    private JButton restartButton;
    private JButton startButton;
    private JButton suspendButton;
    private JButton cloneButton;

    public MyPanel(String uuid) {
        this.uuid = uuid;

        // label pane
        titleLabel = new JLabel();
        titleLabel.setText("Title");
        cpuLabel = new JLabel();
        cpuLabel.setText("CPU");
        ramLabel = new JLabel();
        ramLabel.setText("RAM");
        uuidLabel = new JLabel();
        uuidLabel.setText("UUID");
        stateLabel = new JLabel();

        // settings pane
        cpuSettingsLabel = new JLabel();
        cpuOKButton = new JButton();
        ramSettingsLabel = new JLabel();
        ramOKButton = new JButton();

        String[] cpuValues = {
                "1", "2", "3", "4", "5", "6", "7", "8"
        };

        cpuValueCB = new JComboBox(cpuValues);

        String[] ramValues = {
                "128", "256", "512", "1024", "2048"
        };

        ramValueCB = new JComboBox(ramValues);

        // button pane
        cloneButton = new JButton();
        eraseButton = new JButton();
        stopButton = new JButton();
        restartButton = new JButton();
        startButton = new JButton();
        suspendButton = new JButton();

        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.PAGE_AXIS));
        labelPane.add(titleLabel);
        // labelPane.add(Box.createHorizontalStrut(10));
        labelPane.add(cpuLabel);
        // labelPane.add(Box.createHorizontalStrut(10));
        labelPane.add(ramLabel);
        // labelPane.add(Box.createHorizontalStrut(10));
        labelPane.add(uuidLabel);
        labelPane.add(stateLabel);

        // TODO
        // middle pane

        JPanel settingsPane = new JPanel();
        settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.PAGE_AXIS));

        JPanel cpuPane = new JPanel();
        cpuPane.setLayout(new BoxLayout(cpuPane, BoxLayout.LINE_AXIS));
        cpuPane.add(cpuSettingsLabel);
        cpuPane.add(cpuValueCB);
        cpuPane.add(cpuOKButton);

        JPanel ramPane = new JPanel();
        ramPane.setLayout(new BoxLayout(ramPane, BoxLayout.LINE_AXIS));
        ramPane.add(ramSettingsLabel);
        ramPane.add(ramValueCB);
        ramPane.add(ramOKButton);

        settingsPane.add(cpuPane);
        settingsPane.add(ramPane);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(cloneButton);
        buttonPane.add(eraseButton);
        buttonPane.add(startButton);
        buttonPane.add(suspendButton);
        buttonPane.add(stopButton);
        buttonPane.add(restartButton);

        JPanel upperHalfPane = new JPanel();
        upperHalfPane.setLayout(new BoxLayout(upperHalfPane, BoxLayout.LINE_AXIS));
        upperHalfPane.add(settingsPane);
        upperHalfPane.add(Box.createHorizontalStrut(10));
        upperHalfPane.add(new JSeparator(SwingConstants.VERTICAL));
        upperHalfPane.add(Box.createHorizontalStrut(180));

        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(upperHalfPane);
        rightPane.add(Box.createVerticalStrut(10));
        rightPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        rightPane.add(Box.createVerticalStrut(10));
        rightPane.add(buttonPane);

        // setLayout(new GridLayout(0,1));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(Box.createHorizontalStrut(10));
        add(labelPane);
        add(Box.createHorizontalStrut(10));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(10));
        add(rightPane);
        add(Box.createHorizontalStrut(10));

        setUpPanel();
    }

    public void setUpPanel() {

        titleLabel.setText(Controller.get().getVMName(uuid));
        cpuLabel.setText("CPU = " + Controller.get().getVMVCPUUtilisation(uuid) + "%");
        ramLabel.setText("RAM Livre = " + Controller.get().getVMMem(uuid) + " MB");
        uuidLabel.setText("UUID = " + uuid);
        stateLabel.setText(Controller.get().getState(uuid));

        cpuSettingsLabel.setText("Nº de vCPUs");
        ramSettingsLabel.setText("Quantidade de RAM");

        cloneButton.setText("Clonar VM");
        cloneButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().cloneVM(uuid);
            }

        });

        eraseButton.setText("Apagar");
        eraseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().eraseVM(uuid);
            }

        });

        stopButton.setText("Parar");
        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().stopVM(uuid);
            }

        });

        restartButton.setText("Reiniciar");
        restartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().restartVM(uuid);
            }

        });

        startButton.setText("Iniciar");
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().startVM(uuid);
            }

        });

        suspendButton.setText("Suspender");
        suspendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Controller.get().suspendVM(uuid);
            }

        });

        cpuOKButton.setText("OK");
        cpuOKButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // String value = cpuValueField.getText();
                int value = cpuValueCB.getSelectedIndex() + 1;

                Controller.get().setVMVCPUs(uuid, Long.valueOf(value));

            }

        });

        ramOKButton.setText("OK");
        ramOKButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int value = 128 * (int) Math.pow(2, ramValueCB.getSelectedIndex());

                Controller.get().setVMRam(uuid, Long.valueOf(value));

            }

        });
    }

}
