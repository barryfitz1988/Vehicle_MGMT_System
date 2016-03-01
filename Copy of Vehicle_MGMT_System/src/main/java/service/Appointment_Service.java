package service;

import java.util.List;

import model.Appointment_Model;
import dao.Appointment_dao;

public class Appointment_Service {
	
	private static Appointment_dao dao;

	
	//Constructor
	public Appointment_Service() {

		dao = new Appointment_dao();
	}

	//Open Transaction
	public void open() {
		dao.openCurrentSessionwithTransaction();
	}
	//Close Transaction
	public void close() {
		dao.closeCurrentSessionwithTransaction();
	}
	//Commit Transaction
	public void commit() {

		dao.commit();
	}

	//Add to DB
	public void persist(Appointment_Model entity) {

		dao.persist(entity);

	}
	//Update DB
	public void update(Appointment_Model entity) {

		dao.update(entity);

	}

	// find object by ID
	public Appointment_Model findById(int id) {
		dao.openCurrentSession();
		Appointment_Model o = dao.findById(id);
		dao.closeCurrentSession();
		return o;
	}
	//Delete from DB
	public void delete(int id) {

		Appointment_Model artist = dao.findById(id);
		dao.delete(artist);

	}

	// FindAll in DB
	public List<Appointment_Model> findAll() {
		dao.openCurrentSession();
		List<Appointment_Model> object = dao.findAll();
		dao.closeCurrentSession();
		return object;
	}
	
	
	
	

}
