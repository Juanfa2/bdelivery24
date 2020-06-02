package ar.edu.unlp.info.bd2.model;


import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;


public class Delivered extends OrderStatus{
    private String status;
    private Date startDate;


    public Delivered( Date date){
        //super(order);
        this.setStatus("Delivered");
        this.setStartDate(date);
    }

 

    public Delivered(){
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	this.setStatus("Delivered");
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
    public void entregarOrder(){}
    @Override
    public void cancelarOrder(){}
    @Override
    public void enviarOrder(){}
    @Override
    public void entregarOrder(Date date){}
    @Override
    public void cancelarOrder(Date date){}
    @Override
    public void enviarOrder(Date date){}


}
