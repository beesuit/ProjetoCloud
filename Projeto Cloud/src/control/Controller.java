
package control;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.xmlrpc.XmlRpcException;

import com.xensource.xenapi.APIVersion;
import com.xensource.xenapi.Connection;
import com.xensource.xenapi.Host;
import com.xensource.xenapi.Session;
import com.xensource.xenapi.Types;
import com.xensource.xenapi.Types.XenAPIException;
import com.xensource.xenapi.VBD;
import com.xensource.xenapi.VDI;
import com.xensource.xenapi.VM;
import com.xensource.xenapi.VM.Record;

public class Controller {

    private Connection connection;
    private ArrayList<VM> vmList;
    private ArrayList<String> uuidList;
    private Host host;
    private AlertThread alertThread;
    private static Controller sController;
    private static final String URL = "http://192.168.254.7";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public Controller() {
        connect();
        getHost();
        getAllVMs();
    }

    public static Controller get() {
        if (sController == null) {
            sController = new Controller();
        }
        return sController;
    }

    private void connect() {

        try {
            connection = new Connection(new URL(URL));
            Session.loginWithPassword(connection, USERNAME, PASSWORD, APIVersion.latest()
                    .toString());
        } catch (MalformedURLException | XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void getHost() {
        Set<Host> set = new HashSet<Host>();

        try {
            set = Host.getAll(connection);
            for (Host h : set) {
                host = h;
                break;
            }
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getHostName() {
        try {
            return host.getNameLabel(connection);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public String getHostVCPUUtilisation() {
        try {
            return new DecimalFormat("#.##").format(host.queryDataSource(connection, "cpu0") * 100);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public String getHostUUID() {
        try {
            return host.getUuid(connection).toString();
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public String getHostRAM() {
        try {
            return new DecimalFormat("#").format(((host.queryDataSource(connection,
                    "memory_total_kib")
                    - host.queryDataSource(connection, "memory_free_kib")) / 1024));
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public double getHostVCPUURatio() {
        try {
            return host.queryDataSource(connection, "cpu0") * 100;
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public double getHostRAMRatio() {
        double totalRAM;
        double usedRAM;
        try {
            totalRAM = host.queryDataSource(connection, "memory_total_kib");
            usedRAM = totalRAM - host.queryDataSource(connection, "memory_free_kib");
            return usedRAM / totalRAM * 100;

        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    public void getAllVMs() {
        Set<VM> set = new HashSet<VM>();
        vmList = new ArrayList<VM>();
        uuidList = new ArrayList<String>();
        Record record;
        try {
            set = VM.getAll(connection);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (VM i : set) {
            try {
                record = i.getRecord(connection);
                if (!record.isATemplate
                        && !record.isControlDomain
                        && (record.powerState == Types.VmPowerState.RUNNING
                                || record.powerState == Types.VmPowerState.HALTED
                                || record.powerState == Types.VmPowerState.SUSPENDED)) {
                    vmList.add(i);
                    uuidList.add(i.getUuid(connection));
                }
            } catch (XenAPIException | XmlRpcException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getUuidList() {
        return uuidList;
    }

    public VM getVM(String uuid) {
        for (VM i : vmList) {
            try {
                if (i.getUuid(connection).equals(uuid)) {
                    return i;
                }
            } catch (XenAPIException | XmlRpcException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getVMName(String uuid) {
        try {
            return getVM(uuid).getNameLabel(connection);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void prints() {
        try {
            System.out.println(String.valueOf(vmList.get(0).getMetrics(connection)
                    .getMemoryActual(connection)));
            // return
            // String.valueOf(vmList.get(0).getMetrics(connection).getVCPUsUtilisation(connection).size());
            System.out.println(vmList.get(0).getMetrics(connection).getVCPUsUtilisation(connection)
                    .size());
            System.out.println(vmList.get(0).getRecord(connection).metrics.getVCPUsUtilisation(
                    connection).size());
            System.out.println(vmList.get(0).getNameLabel(connection));
            // vmList.get(0).start(connection, false, false);
            System.out.println("Get all the  VMMetrics Records");

            Map<VM, VM.Record> allrecords = VM.getAllRecords(connection);
            System.out.println("got: " + allrecords.size() + " records");
            if (allrecords.size() > 0)
            {
                System.out.println(vmList.get(0).getRecord(connection));
                System.out.println("Print out a  VMMetrics record ");
                // System.out.println(allrecords.values().toArray()[1].toString());
                System.out.println(vmList.get(0).getUuid(connection));
                System.out.println(uuidList.size());
                // VM teste = VM.getByUuid(connection,
                // "3d0419da-8d83-4c2d-8019-dbf0f6cd02bb");
                // System.out.println(teste.getMetrics(connection).getVCPUsUtilisation(connection).size());
            }

        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void startVM(String uuid) {
        VM vm = getVM(uuid);
        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.HALTED) {
                // vm.start(connection, false, false);
                vm.startAsync(connection, false, false);

            } else if (vm.getPowerState(connection) == Types.VmPowerState.SUSPENDED) {
                vm.resumeAsync(connection, false, false);
            }
            else {
                System.out.println("VM não está parada");
            }
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stopVM(String uuid) {
        VM vm = getVM(uuid);
        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.RUNNING) {
                // vm.shutdown(connection);
                vm.shutdownAsync(connection);
            } else {
                System.out.println("VM não está rodando");
            }

        } catch (XenAPIException
                | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void suspendVM(String uuid) {
        VM vm = getVM(uuid);
        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.RUNNING) {
                // vm.suspend(connection);
                vm.suspendAsync(connection);
            } else {
                System.out.println("VM não está rodando");
            }
        } catch (XenAPIException
                | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void restartVM(String uuid) {
        VM vm = getVM(uuid);
        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.RUNNING) {
                // vm.cleanReboot(connection);
                vm.cleanRebootAsync(connection);
            } else {
                System.out.println("VM não está rodando");
            }
        } catch (XenAPIException
                | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void eraseVM(String uuid) {
        VM vm = getVM(uuid);
        VDI vdi = null;

        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.HALTED) {
                // vm.

                Set<VBD> vbds = vm.getVBDs(connection);

                for (VBD i : vbds) {

                    vdi = i.getVDI(connection);
                }
                //vm.destroy(connection);
                vm.destroyAsync(connection);
                //vdi.destroy(connection);
                vdi.destroyAsync(connection);

            } else {
                System.out.println("A VM não está parada");
            }
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void cloneVM(String uuid) {
        VM vm = getVM(uuid);
        VM cloneVM;
        

        try {
            // cloneVM = vm.createClone(connection, cloneName);
            // cloneVM.setNameDescription(connection, "VM criada em " + new
            // Date().toString());
            String cloneName = vm.getNameLabel(connection) + " " + new Date().toString();

            vm.createCloneAsync(connection, cloneName);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setVMRam(String uuid, Long value) {
        VM vm = getVM(uuid);
        value *= 1024 * 1024;
        try {
            //vm.setMemoryLimits(connection, value, value, value, value);
            vm.setMemoryLimitsAsync(connection, value, value, value, value);
            // vm.setMemoryStaticMax(connection, value);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setVMVCPUs(String uuid, Long value) {
        VM vm = getVM(uuid);
        try {
            if (vm.getPowerState(connection) == Types.VmPowerState.HALTED) {
                if (vm.getVCPUsMax(connection) < value) {                    
                    vm.setVCPUsMax(connection, value);
                    vm.setVCPUsAtStartup(connection, value);
                } else {
                    vm.setVCPUsAtStartup(connection, value);
                }
            } else if (vm.getPowerState(connection) == Types.VmPowerState.RUNNING) {
                if (vm.getVCPUsMax(connection) >= value) {
                    //vm.setVCPUsNumberLive(connection, value);
                    vm.setVCPUsNumberLiveAsync(connection, value);
                } else {
                    System.out
                            .println("O valor dado é maior que o valor de VCPUs máximas configurado");
                }
            } else {
                System.out.println("VM precisa estar parada ou rodando");
            }

        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getVMVCPUUtilisation(String uuid) {
        VM vm = getVM(uuid);
        try {
            // return String.valueOf(vm.queryDataSource(connection,
            // "cpu0")*100);
            return new DecimalFormat("#.##").format(vm.queryDataSource(connection, "cpu0") * 100);

            // return String.valueOf(((Integer) vm.queryDataSource(connection,
            // "cpu0")));

            // return
            // String.valueOf(vm.getMetrics(connection).getVCPUsUtilisation(connection).size());
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public String getVMMem(String uuid) {
        VM vm = getVM(uuid);
        try {
            //return String.valueOf((vm.queryDataSource(connection, "memory") - vm.queryDataSource(connection, "memory_internal_free")));
            return new DecimalFormat("#").format(vm.queryDataSource(connection,"memory_internal_free")/1024);
            
            //return String.valueOf(vm.getMetrics(connection).getMemoryActual(connection));
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getState(String uuid) {
        VM vm = getVM(uuid);
        try {
            return vm.getPowerState(connection).toString();
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public void startAlertThread(int cpu, int ram) {
        if (alertThread != null) {
            alertThread.interrupt();

        }
        alertThread = new AlertThread(cpu, ram);
        alertThread.start();
    }

    public void stopAlertThread() {
        if (alertThread != null) {
            alertThread.interrupt();
            alertThread = null;
        }
    }

}
