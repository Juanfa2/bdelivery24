package ar.edu.unlp.info.bd2.model;



import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;


public class Cancelled extends OrderStatus{
    private String status;
    private Date startDate;

    public Cancelled(Date date){
        
        this.setStatus("Cancelled");
        this.setStartDate(date);
    }
    public Cancelled(){
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	this.setStatus("Cancelled");
    	this.setStartDate(date);
    }
  

    @Override
    public void setStatus(String status){
        this.status=status;
    }
    @Override
    public String getStatus(){
        return this.status;
    }
    @Override
    public void setStartDate(Date date){
        this.startDate = date;
    }
    @Override
    public Date getStartDate(){
        return this.startDate;
    }
    @Override
    public void entregarOrder(Date date){}
    @Override
    public void cancelarOrder(Date date){}
    @Override
    public void enviarOrder(Date date){}
    @Override
    public void entregarOrder(){}
    @Override
    public void cancelarOrder(){}
    @Override
    public void enviarOrder(){}



}
