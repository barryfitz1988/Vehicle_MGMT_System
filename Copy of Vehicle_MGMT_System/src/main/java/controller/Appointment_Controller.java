package controller;

import gui.AddAppointment_GUI;
import gui.AppointmentTable;
import gui.Appointment_GUI;
import gui.CustomerTable;
import gui.Header_Render;
import gui.Main_Menu_GUI;

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
import javax.swing.table.TableColumn;

import org.apache.activemq.filter.function.regexMatchFunction;
import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;

import service.Appointment_Service;
import service.Customer_Service;
import service.Employee_Service;
import service.Service_Service;
import service.Vehicle_Service;
import model.Appointment_Model;
import model.Customer_Model;
import model.Customers_Vehicle_Model;
import model.Employee_Model;
import model.Service_Model;

public class Appointment_Controller {

	private Main_Menu_GUI maingui = new Main_Menu_GUI();
	private Appointment_GUI appointmentgui = new Appointment_GUI();
	private Appointment_Model appointmentmodel = new Appointment_Model();
	private AddAppointment_GUI addappointmentgui = new AddAppointment_GUI();
	
	private Customer_Model customermodel = new Customer_Model();
	private Customer_Service customerservice = new Customer_Service();
	private ArrayList<Customer_Model> customers = new ArrayList<Customer_Model>();
	
	private Vehicle_Service vehicleservice = new Vehicle_Service();
	private Customers_Vehicle_Model ownersCar =  new Customers_Vehicle_Model();
	private ArrayList<Customers_Vehicle_Model> ownersCarList = new ArrayList<Customers_Vehicle_Model>();
	
	private Service_Model servivemodel = new Service_Model();
	private Service_Service serviceservice = new Service_Service();
	private ArrayList<Service_Model> services = new ArrayList<Service_Model>();
	
	
	
	private Employee_Service emservice = new Employee_Service();
	private Employee_Model employeemode = new Employee_Model();
	private ArrayList<Employee_Model> employees = new ArrayList<Employee_Model>();
	private AppointmentTable appointmenttable;
	private Appointment_Service service = new Appointment_Service();
	
	
	
	private String date;
	private String stylist;
	private String hour;
	private String[] servicesname;
	int counter = 0;
	int employeeId;
	int clientId;
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
		
		this.maingui.addappointmentListener(new Listener());
	

	}
	
	
	
	
	
	
	public void refreshTable() {
		appointmenttable.fireTableDataChanged();
	}
	
	class registrationSearcher implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String reg = addappointmentgui.getSearchTextfield().getText();
			
			
			for (Customers_Vehicle_Model c : ownersCarList) {
				String customerName =  c.getCustomer().getFirst_name()+ " " + c.getCustomer().getSurname();
				String makeModel =  c.getVehicle().getVehicle_make()+ " " + c.getVehicle().getVehicle_model();
				boolean found = false;
				
				System.out.println(c.getVehicle().getVehicle_reg());
				System.out.println("IS THERE ANYTHING HERE??   " + ownersCarList.size());
				
				if (c.getVehicle().getVehicle_reg().equals(reg) /*==  vehicleservice.findbyRegistration(reg).getVehicle().getVehicle_reg()*/) {
					System.out.println("Success");
					
					addappointmentgui.getLblCustomersName().setText(customerName);
					addappointmentgui.getLblMakeModel().setText(makeModel);


						
					
			
				}
				

				else {
					System.out.println("Fail");
					//addappointmentgui.getLblCustomersName().setText("UNKNOWN");
					//addappointmentgui.getLblMakeModel().setText("UNKNOWN");
					found = false;

				
			}
				

			}
			
			
	
			
		}
		
	}
	
	
	
	class escapeListener implements ActionListener{

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
				System.out.println("Date " +date );
				System.out.println("Date " + c );
				addappointmentgui.getDateLbl().setText(date);

			}
			
			else {
				
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
				List<Customers_Vehicle_Model> cus = vehicleservice.findAllOwners();	
			
				for (int x = 0; x < cus.size(); x++) {

				ownersCar = (Customers_Vehicle_Model) cus.get(x);
				ownersCarList.add(ownersCar);
			}	

			
			for(int x = 0; x <services.size();x++){
			
			servicesname[counter] = services.get(x).getJob_name();	
			counter++;

			}
			

	    	
	    	int row = appointmentgui.getAppointmentTable().rowAtPoint(evt.getPoint());
	        int col = appointmentgui.getAppointmentTable().columnAtPoint(evt.getPoint());
	        
	        String time = (String)appointmentgui.getRowtable().getValueAt(row, 0);
	        
	    System.out.println(appointmentgui.getAppointmentTable().getValueAt(row, col));
	        	
	        	

	        
	     if(appointmentgui.getAppointmentTable().getValueAt(row, col)== null) {
	    	 System.out.println("ROW  "+ row +"  "+ "COL  "+ col);
	    	
	    	 String employeeName = (String)appointmentgui.getAppointmentTable().getTableHeader().getColumnModel()
						.getColumn(col).getHeaderValue();
	    	 
	    	 addappointmentgui.getTechLbl().setText(employeeName);
	    	 addappointmentgui.getTimeLbl().setText(time);
	    	 addappointmentgui.getServiceComboBox().setModel(new DefaultComboBoxModel(servicesname));
	    	 addappointmentgui.setVisible(true);
	     
	     }else{
	    	 
	    	 System.out.println("What An Awful thing to happen");
	    	 
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
				

				
				appointmenttable = new AppointmentTable(employees);
				appointmentgui.getAppointmentTable().setModel(appointmenttable);
				
				//appointmenttable = new AppointmentTable(employees);
				
				 for(int i=0;i<appointmenttable.getColumnCount();i++)
				 {
				 TableColumn column1 = appointmentgui.getAppointmentTable().getTableHeader().getColumnModel().getColumn(i);
				 
				 	for(Employee_Model em : employees){
				 		System.out.println(em.getEmp_firstname() + em.getEmp_surname());
				 		//column1.setHeaderValue(employees.get(i).getEmp_firstname() + " " + employees.get(i).getEmp_surname()); 
				 	}
			 column1.setHeaderValue(employees.get(i).getEmp_firstname() + " " + employees.get(i).getEmp_surname());
			 //column1.setHeaderValue("Hello");
				 
				 }
				
				 
				 for (int j = 0; j < (appointmentgui.getColumnModel().getColumnCount()); j++) {
					 appointmentgui.getColumnModel().getColumn(j).setPreferredWidth(150);
			        }
				
				
				appointmentgui.getAppointmentTable().getTableHeader().setDefaultRenderer(new Header_Render());
						
			
				appointmentgui.setVisible(true);

			} catch (NumberFormatException nfe) {

				System.out.println("Not A Number: " + nfe.getMessage());

			}

		}

	}
	
	
	
}
