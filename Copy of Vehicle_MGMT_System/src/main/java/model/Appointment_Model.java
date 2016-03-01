package model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Appointment_Model {
	
	@Id
	private int appointment_id;
	private Date date;
	private Time time;
	private int customer_id;
	private int employee_id;
	private int job_id;
	private int vehicle_id;
	private int customer_vehicle_id;
	
	
	
	
	
	public Appointment_Model(int appointment_id, Date date, Time time,
			int customer_id, int employee_id, int job_id, int vehicle_id,
			int customer_vehicle_id) {

		this.appointment_id = appointment_id;
		this.date = date;
		this.time = time;
		this.customer_id = customer_id;
		this.employee_id = employee_id;
		this.job_id = job_id;
		this.vehicle_id = vehicle_id;
		this.customer_vehicle_id = customer_vehicle_id;
	}
	
	
	public Appointment_Model(){
		
	}
	
	
	public int getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	public int getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public int getCustomer_vehicle_id() {
		return customer_vehicle_id;
	}
	public void setCustomer_vehicle_id(int customer_vehicle_id) {
		this.customer_vehicle_id = customer_vehicle_id;
	}



	@Override
	public String toString() {
		return "Appointment_Model [appointment_id=" + appointment_id
				+ ", date=" + date + ", time=" + time + ", customer_id="
				+ customer_id + ", employee_id=" + employee_id + ", job_id="
				+ job_id + ", vehicle_id=" + vehicle_id
				+ ", customer_vehicle_id=" + customer_vehicle_id + "]";
	}
	


}
