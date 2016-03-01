package Interface;

import java.util.List;

import model.Appointment_Model;



public interface Appointment_Interface {
	
	public void persist(Appointment_Model c);
	
	public void update(Appointment_Model c);
	
	public Appointment_Model findById(int id);
	
	public void delete(Appointment_Model c);
	
	public List<Appointment_Model> findAll();
	
	

}
