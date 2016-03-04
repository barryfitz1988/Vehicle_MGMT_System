package controller;

import gui.AddAppointment_GUI;
import gui.AppointmentTable;
import gui.Appointment_GUI;
import gui.BlankTable_Frame;
import gui.CustomerTable;
import gui.Header_Render;
import gui.Main_Menu_GUI;
import gui.PartsTable;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.activemq.filter.function.regexMatchFunction;
import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;

import service.Appointment_Service;
import service.Customer_Service;
import service.Employee_Service;
import service.Parts_Service;
import service.Service_Service;
import service.Vehicle_Service;
import model.Appointment_Model;
import model.Customer_Model;
import model.Customers_Vehicle_Model;
import model.Employee_Model;
import model.Parts_Model;
import model.Service_Model;

public class Appointment_Controller {

	private Main_Menu_GUI maingui = new Main_Menu_GUI();
	private Appointment_GUI appointmentgui = new Appointment_GUI();
	private Appointment_Model appointmentmodel = new Appointment_Model();
	private AddAppointment_GUI addappointmentgui = new AddAppointment_GUI();
	private Appointment_Service appointmentservice  = new Appointment_Service();
	private ArrayList<Appointment_Model> appointments = new ArrayList<Appointment_Model>();
	
	
	private BlankTable_Frame partsFrame = new BlankTable_Frame();
	private PartsTable partstable;
	

	
	private Vehicle_Service vehicleservice = new Vehicle_Service();
	private Customers_Vehicle_Model ownersCar =  new Customers_Vehicle_Model();
	private ArrayList<Customers_Vehicle_Model> ownersCarList = new ArrayList<Customers_Vehicle_Model>();
	
	private Service_Model servivemodel = new Service_Model();
	private Service_Service serviceservice = new Service_Service();
	private ArrayList<Service_Model> services = new ArrayList<Service_Model>();
	
	private Parts_Model partsmodel = new Parts_Model();
	private Parts_Service partsservice = new Parts_Service();
	private ArrayList<Parts_Model> parts = new ArrayList<Parts_Model>();
	
	
	
	private Employee_Service emservice = new Employee_Service();
	private Employee_Model employeemode = new Employee_Model();
	private ArrayList<Employee_Model> employees = new ArrayList<Employee_Model>();
	
	private DefaultTableModel tablemodel;
	private AppointmentTable appointmenttable;
	
	
	
	
	private String date;
	private String tech;
	private String hour;
	private String reg;
	private String selectedService;
	private String customer;
	private String[] servicesname;
	int counter = 0;
	boolean nextCellAvailable;
	
	
	public Appointment_Controller(Main_Menu_GUI maingui,AddAppointment_GUI addappointmentgui,
			Appointment_GUI appointmentgui, Appointment_Model appointmentmodel) {
		
		this.maingui = maingui;
		this.appointmentgui = appointmentgui;
		this.appointmentmodel = appointmentmodel;
		this.addappointmentgui = addappointmentgui;
		
		this.addappointmentgui.CancelandReturn(new escapeListener());
		this.appointmentgui.exitListener(new exitListener());
		this.appointmentgui.selectCellonTable(new cellListener());
		this.appointmentgui.MyDateListener(new theDateListener());
		this.addappointmentgui.searchforReg(new registrationSearcher());
		this.addappointmentgui.addParttoAppointment(new partAddedListener());
		this.addappointmentgui.saveAppointment(new saveListener());
		
		this.maingui.addappointmentListener(new Listener());
	

	}

	
	public void refreshTable() {
		appointmenttable.fireTableDataChanged();
	}
	
	
	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			date = addappointmentgui.getDateLbl().getText();
			tech = addappointmentgui.getTechLbl().getText();
			hour = addappointmentgui.getTimeLbl().getText();
			customer = addappointmentgui.getLblCustomersName().getText();
			reg = addappointmentgui.getLblReg().getText();
			selectedService =String.valueOf(addappointmentgui.getServiceComboBox().getSelectedItem());
			
			appointmentservice.open();
			appointmentmodel.getAppointment_id();
			appointmentmodel.setCustomer_name(customer);
			appointmentmodel.setDate(date);
			appointmentmodel.setVehicle_reg(reg);
			appointmentmodel.setEmployee_name(tech);
			appointmentmodel.setJob_name(selectedService);
			appointmentmodel.setTime(hour);
			appointmentservice.persist(appointmentmodel);
			appointmentservice.close();
			
			System.out.println("DATE--->" + date);
			addappointmentgui.dispose();
			services.clear();
			ownersCarList.clear();
			servicesname = new String[servicesname.length];
			counter = 0;
			
			
		}
		
		
		
	}
	
	class partAddedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
				
			
				partsFrame.setVisible(true);
				partstable = new PartsTable(parts);
				partsFrame.getBlankTable().setModel(partstable);
				
				List<Parts_Model> c = partsservice.findAll();

				for (int x = 0; x < c.size(); x++) {
					partsmodel = (Parts_Model) c.get(x);
					parts.add(partsmodel);

				}
			
			
			
		}
		
		
		
		
	}
	
	class registrationSearcher implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String reg = addappointmentgui.getSearchTextfield().getText();

			for (Customers_Vehicle_Model c : ownersCarList) {
				String customerName = c.getCustomer().getFirst_name() + " "
						+ c.getCustomer().getSurname();
				String makeModel = c.getVehicle().getVehicle_make() + " "
						+ c.getVehicle().getVehicle_model();
				String registration = c.getVehicle().getVehicle_reg();
				
				boolean found = false;

				System.out.println(c.getVehicle().getVehicle_reg());
				System.out.println("IS THERE ANYTHING HERE??   "
						+ ownersCarList.size());

				if (c.getVehicle().getVehicle_reg().equals(reg)) {
					System.out.println("Success");

					addappointmentgui.getLblCustomersName().setText(
							customerName);
					addappointmentgui.getLblMakeModel().setText(makeModel);
					addappointmentgui.getLblReg().setText(registration);

				}

				else {
					System.out.println("Fail");
					// addappointmentgui.getLblCustomersName().setText("UNKNOWN");
					// addappointmentgui.getLblMakeModel().setText("UNKNOWN");
					found = false;

				}

			}

		}

	}
	
	
	
	class escapeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			addappointmentgui.dispose();
			services.clear();
			ownersCarList.clear();
			servicesname = new String[servicesname.length];
			counter = 0;

		}

	}
	
	class theDateListener implements DateListener{

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  
		@Override 
		public void dateChanged(DateEvent e){
			
			Calendar c = e.getSelectedDate();
			appointmentgui.getAppointmentTable().setVisible(true);
			
			if (c != null) {
				
				date = dateFormat.format(c.getTime()); 
				//hour = (String)appointmentgui.getRowtable().getValueAt(0, 0);
				//System.out.println("Date " +date );
				//System.out.println("Date " + c );
				
				addappointmentgui.getDateLbl().setText(date);
				
				//to clear table
				 for (int i = 0; i < appointmentgui.getAppointmentTable().getRowCount(); i++) {
				        for (int j = 0; j < appointmentgui.getAppointmentTable().getColumnCount(); j++) {
				        	appointmentgui.getAppointmentTable().setValueAt("", i, j);
				        }
				    }

			
			//to populate table
			for(Appointment_Model a : appointments){
				
				
				int serviceDuration = 0;
				String employeeName = a.getEmployee_name();
				
				for(Employee_Model em : employees){
					
					if(employeeName == em.getEmp_firstname()+" "+em.getEmp_surname()){
						
						employeeName = em.toString();
						
					}
				}
				
				String serviceName = a.getJob_name();
				
				for(Service_Model sv : services){
					
					if(serviceName == sv.getJob_name()){
						
						serviceDuration = sv.getJob_time();
						//System.out.print("What is  the service time inside loop???" +serviceDuration);
					}
				}
				
				
			if(date.equals(a.getDate()) /*&& hour.equals(a.getTime())*/){
				System.out.print("What is  the service time???" +serviceDuration);
				System.out.println("This is the Appointments for that day!!! -->" + a.toString());

				for(int x = 0; x<appointmentgui.getAppointmentTable().getColumnCount();x++) {

					if(appointmentgui.getAppointmentTable().getTableHeader().getColumnModel().getColumn(x).getHeaderValue()
							.equals(employeeName)){	
					
					for(int y = 0; y<appointmentgui.getRowtable().getRowCount(); y++) {
	
						if(appointmentgui.getRowtable().getValueAt(y,y).equals(a.getTime())){
							
							appointmentgui.getAppointmentTable().setValueAt(a.getCustomer_name(), y, x);
							
							/*if(serviceDuration > 1){
								System.out.print("What is  the service time???" +serviceDuration);
								appointmentgui.getAppointmentTable().setValueAt("^" + a.getCustomer_name(), y + 1, x);
							}*/
							
						}
						
		
	
					}
				}
				}
				
					
					

				
			}
			
			}
			
			}else {
				
				System.out.println("No time selected.");
			}
		}
		
	}

	class cellListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent evt ) {
			
			List<Service_Model> c = serviceservice.findAll();	
			
			for (int x = 0; x < c.size(); x++) {

				servivemodel = (Service_Model) c.get(x);
				services.add(servivemodel);
				servicesname = new String[services.size()];
			}
			
			
			for(int x = 0; x <services.size();x++){
				
			servicesname[counter] = services.get(x).getJob_name();	
			counter++;

			}
				
			
			List<Customers_Vehicle_Model> cus = vehicleservice.findAllOwners();	
			
				for (int x = 0; x < cus.size(); x++) {

				ownersCar = (Customers_Vehicle_Model) cus.get(x);
				ownersCarList.add(ownersCar);
			}	

			

			

	    	
	    	int row = appointmentgui.getAppointmentTable().rowAtPoint(evt.getPoint());
	        int col = appointmentgui.getAppointmentTable().columnAtPoint(evt.getPoint());
	        
	        String time = (String)appointmentgui.getRowtable().getValueAt(row, 0);
	      //  appointmentgui.getAppointmentTable().setValueAt("xxxxx", 0, 0);
	    System.out.println(appointmentgui.getAppointmentTable().getValueAt(row, col));
	        	
	        
	     if(appointmentgui.getAppointmentTable().getValueAt(row, col)== "") {
	    	 System.out.println("ROW  "+ row +"  "+ "COL  "+ col);
	    	 
	    	 
	    	
	    	 
			
			String employeeName = (String)appointmentgui.getAppointmentTable().getTableHeader().getColumnModel()
						.getColumn(col).getHeaderValue();
	    	 
	    	 addappointmentgui.getTechLbl().setText(employeeName);
	    	 addappointmentgui.getTimeLbl().setText(time);
	    	 addappointmentgui.getServiceComboBox().setModel(new DefaultComboBoxModel(servicesname));
	    	 addappointmentgui.setVisible(true);
	     
	     }else{
	    	 System.out.println("ROW  "+ row +"  "+ "COL  "+ col);
	    	 System.out.println(appointmentgui.getAppointmentTable().getValueAt(row, col));
	    	 System.out.println("What An Awful thing to happen");
	    	 counter = 0;
	     }
	     
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {

				employees.clear();
				appointmentgui.dispose();
				maingui.setVisible(true);

			} catch (NumberFormatException nfe) {

				System.out.println("Not A Number: " + nfe.getMessage());

			}

		}

	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				maingui.dispose();
				
				List<Employee_Model> c = emservice.findAll();

				for (int x = 0; x < c.size(); x++) {
					employeemode = (Employee_Model) c.get(x);
					employees.add(employeemode);

				}
				
				
				List<Appointment_Model> a = appointmentservice.findAll();

				for (int x = 0; x < a.size(); x++) {
					appointmentmodel = (Appointment_Model) a.get(x);
					appointments.add(appointmentmodel);

				}
				
				
				tablemodel = (DefaultTableModel)appointmentgui.getAppointmentTable().getModel();
				tablemodel.setRowCount(12);
				tablemodel.setColumnCount(employees.size());
				System.out.print("IS IT NULL??" + tablemodel);
				//appointmenttable = new AppointmentTable(employees);
				//appointmentgui.getAppointmentTable().setModel(tablemodel);
				//appointmentgui.getTablemodel().equals((DefaultTableModel)appointmentgui.getAppointmentTable().getModel());
				



				for (int i = 0; i < appointmentgui.getAppointmentTable().getColumnCount(); i++) {
					TableColumn column1 = appointmentgui.getAppointmentTable()
							.getTableHeader().getColumnModel().getColumn(i);

					for (Employee_Model em : employees) {
						System.out.println(em.getEmp_firstname()
								+ em.getEmp_surname());
						// column1.setHeaderValue(employees.get(i).getEmp_firstname()
						// + " " + employees.get(i).getEmp_surname());
					}
					column1.setHeaderValue(employees.get(i).getEmp_firstname()
							+ " " + employees.get(i).getEmp_surname());
					


				}

				for (int j = 0; j < (appointmentgui.getColumnModel()
						.getColumnCount()); j++) {
					appointmentgui.getColumnModel().getColumn(j)
							.setPreferredWidth(150);
				}

				appointmentgui.getAppointmentTable().getTableHeader()
						.setDefaultRenderer(new Header_Render());
				
				
				
				appointmentgui.setVisible(true);

			} catch (NumberFormatException nfe) {

				System.out.println("Not A Number: " + nfe.getMessage());

			}

		}

	}
	
	
	
}
