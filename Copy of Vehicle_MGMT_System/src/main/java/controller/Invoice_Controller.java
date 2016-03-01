package controller;

import gui.Invoice_GUI;
import gui.Main_Menu_GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Invoice_Model;

public class Invoice_Controller {
	
	

	private Main_Menu_GUI maingui = new Main_Menu_GUI();
	private Invoice_GUI invoicegui = new Invoice_GUI();
	private Invoice_Model invoicemodel = new Invoice_Model();
	
	
    public Invoice_Controller(Main_Menu_GUI maingui, Invoice_GUI invoicegui, Invoice_Model invoicemodel) {
    	        this.maingui = maingui;
    	        this.invoicegui= invoicegui;
    	        this.invoicemodel = invoicemodel;
    	         

    	       this.invoicegui.exitListener(new exitListener());    
    		   this.maingui.addInvoiceListener(new Listener());
    		    
    
    
    }
    
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {

				invoicegui.dispose();
				maingui.setVisible(true);

			} catch (NumberFormatException nfe) {

				System.out.println("Not A Number: " + nfe.getMessage());

			}

		}

	}
    
    
    class Listener implements ActionListener{
    	
    	
    	public void actionPerformed(ActionEvent e) {
   
    		
    		
    		 try {

    		
    			
    			 maingui.dispose();
    			 
    			 
    			 invoicegui.setVisible(true);
    			 
    		 
    		 } catch (NumberFormatException nfe) {
  			   
                 System.out.println("Not A Number: " + nfe.getMessage());
       
              }
    	
    	
    }
    	
}

}
