package control;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.xmlrpc.XmlRpcException;

import com.xensource.xenapi.APIVersion;
import com.xensource.xenapi.Connection;
import com.xensource.xenapi.Session;
import com.xensource.xenapi.Types;
import com.xensource.xenapi.Types.BadServerResponse;
import com.xensource.xenapi.Types.OperationNotAllowed;
import com.xensource.xenapi.Types.OtherOperationInProgress;
import com.xensource.xenapi.Types.SrFull;
import com.xensource.xenapi.Types.VmBadPowerState;
import com.xensource.xenapi.Types.VmIsTemplate;
import com.xensource.xenapi.Types.XenAPIException;
import com.xensource.xenapi.VM;
import com.xensource.xenapi.VM.Record;


public class Controller {
	private Set<VM> vmSet;
	private Session session;
	private Connection connection;
	private ArrayList<VM> vmList;
	private ArrayList<String> uuidList;
	private static Controller sController;
	private static final String URL = "http://192.168.254.7";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";

	public Controller() {
		// TODO Auto-generated constructor stub
	}

	public static Controller get(){
		if(sController == null){
			sController = new Controller();
		}
		return sController;
	}

	public void connect(){
		Connection connection = null;
		try {
			connection = new Connection(new URL(URL));
			Session session = Session.loginWithPassword(connection, USERNAME, PASSWORD, APIVersion.latest().toString());
		} catch (XenAPIException | XmlRpcException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//VM.getByNameLabel(c, label);
		this.connection = connection;
	}
	
	public void getAllVMs(){
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
		for (VM i : set){
			try {
				record = i.getRecord(connection);
				if(!record.isATemplate && !record.isControlDomain && (record.powerState == Types.VmPowerState.RUNNING || record.powerState == Types.VmPowerState.HALTED)){
					vmList.add(i);
					uuidList.add(i.getUuid(connection));
				}
			} catch (XenAPIException | XmlRpcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setMemory(Long value, String id){
		//VM.getByUuid(connection, id);
		try {
			getVM(id).setMemoryStaticMax(connection, value);
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setVCPUs(Long value, String id){
		try {
			getVM(id).setVCPUsNumberLive(connection, value);
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getUuidList(){
		return uuidList;
	}

	public VM getVM(String uuid){
		for(VM i : vmList){
			try {
				if(i.getUuid(connection).equals(uuid)){
					return i;
				}
			} catch (XenAPIException | XmlRpcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getVMName(String uuid){
		try {
			return getVM(uuid).getNameLabel(connection);
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getVMMem(String uuid){
		try {
			return String.valueOf(getVM(uuid).getMetrics(connection).getMemoryActual(connection));
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getFirst(){
		try {
			System.out.println(String.valueOf(vmList.get(0).getMetrics(connection).getMemoryActual(connection))); 
			//return String.valueOf(vmList.get(0).getMetrics(connection).getVCPUsUtilisation(connection).size());
			System.out.println(vmList.get(0).getMetrics(connection).getVCPUsUtilisation(connection).size());
			System.out.println(vmList.get(0).getRecord(connection).metrics.getVCPUsUtilisation(connection).size());
			System.out.println(vmList.get(0).getNameLabel(connection));
			//vmList.get(0).start(connection, false, false);
			System.out.println("Get all the  VMMetrics Records");

			Map<VM, VM.Record> allrecords = VM.getAllRecords(connection);
			System.out.println("got: " + allrecords.size() + " records");
			if (allrecords.size() > 0)
			{
				System.out.println(vmList.get(0).getRecord(connection));
				System.out.println("Print out a  VMMetrics record ");
				//System.out.println(allrecords.values().toArray()[1].toString());
				System.out.println(vmList.get(0).getUuid(connection));
				System.out.println(uuidList.size());
				//VM teste = VM.getByUuid(connection, "3d0419da-8d83-4c2d-8019-dbf0f6cd02bb");
				//System.out.println(teste.getMetrics(connection).getVCPUsUtilisation(connection).size());
			}


		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void startVM(String uuid){
		VM vm = getVM(uuid);
		try {
			if(vm.getPowerState(connection) == Types.VmPowerState.HALTED){
				vm.start(connection, false, false);
			}else{
				System.out.println("VM não está halted");
			}
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopVM(String uuid){
		VM vm = getVM(uuid);
		try {
			if(vm.getPowerState(connection) == Types.VmPowerState.RUNNING){
				vm.shutdown(connection);
			}else{
				System.out.println("VM não está rodando");
			}
			
		} catch (XenAPIException
				| XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void suspendVM(String uuid){
		VM vm = getVM(uuid);
		try {
			if(vm.getPowerState(connection) == Types.VmPowerState.RUNNING){
				vm.suspend(connection);
			}else{
				System.out.println("VM não está rodando");
			}
		} catch ( XenAPIException
				| XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void restartVM(String uuid){
		VM vm = getVM(uuid);
		try {
			if(vm.getPowerState(connection) == Types.VmPowerState.RUNNING){
				vm.cleanReboot(connection);
			}else{
				System.out.println("VM não está rodando");
			}
		} catch (XenAPIException
				| XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void eraseVM(String uuid){
		VM vm = getVM(uuid);
		try {
			if(vm.getPowerState(connection) == Types.VmPowerState.HALTED){
				vm.destroy(connection);
			}else{
				System.out.println("A VM não está parada");
			}
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cloneVM(String uuid){
		VM vm = getVM(uuid);
		VM cloneVM;
		String cloneName = "VM Clone";
		
		try {
			cloneVM = vm.createClone(connection, cloneName);
			cloneVM.setNameDescription(connection, "VM criada em " + new Date().toString());
		} catch (XenAPIException | XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
