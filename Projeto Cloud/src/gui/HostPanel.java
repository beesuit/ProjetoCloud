
package gui;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Controller;

public class HostPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel cpuLabel;
    private JLabel ramLabel;
    private JLabel uuidLabel;

    public HostPanel() {
        titleLabel = new JLabel();
        cpuLabel = new JLabel();
        ramLabel = new JLabel();
        uuidLabel = new JLabel();
        
        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.PAGE_AXIS));
        labelPane.add(titleLabel);
        labelPane.add(cpuLabel);
        labelPane.add(ramLabel);
        labelPane.add(uuidLabel);
        
        add(labelPane);
        setUpPane();
    }
    
    private void setUpPane(){
        titleLabel.setText(Controller.get().getHostName());
        cpuLabel.setText(Controller.get().getHostVCPUUtilisation()+"%");
        ramLabel.setText(Controller.get().getHostRAM()+"MB");
        uuidLabel.setText(Controller.get().getHostUUID());
    }

}
