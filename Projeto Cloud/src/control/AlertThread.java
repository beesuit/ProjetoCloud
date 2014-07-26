
package control;

import java.text.DecimalFormat;

public class AlertThread extends Thread {

    private int cpuThreshold;
    private int ramThreshold;
    private int count;

    private final int SLEEP = 1000;
    private final int LIMIT = 15;

    public AlertThread(int cpu, int ram) {
        cpuThreshold = cpu;
        ramThreshold = ram;
        count = 0;

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (Controller.get().getHostVCPUURatio() > cpuThreshold
                        || Controller.get().getHostRAMRatio() > ramThreshold) {

                    count++;
                    if (count > LIMIT) {

                        Email email = new Email("xrodssx@hotmail.com",
                                "cloud.project666@gmail.com", "123abcdef");
                        String subject = "Alerta";
                        String text = "Uso de CPU: "
                                + Controller.get().getHostVCPUUtilisation()
                                + "%"
                                + "\nUso de RAM: "
                                + new DecimalFormat("#.##").format(Controller.get()
                                        .getHostRAMRatio()) + "%";

                        email.enviarEmail(subject, text);

                        System.out.println("email sent");
                        count = 0;
                    }
                }
                else {
                    if (count > 0) {
                        count--;
                    }
                }

                sleep(SLEEP);
                // System.out.println(cpuThreshold + " " + ramThreshold);
                System.out.println(count);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                interrupt();

            }

        }

    }
}
