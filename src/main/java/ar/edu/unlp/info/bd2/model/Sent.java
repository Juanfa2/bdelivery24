package ar.edu.unlp.info.bd2.model;


import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;


public class Sent extends OrderStatus{
    private String status;
    private Date startDate;


    public Sent( Date date){
        this.setStatus("Sent");
        this.setStartDate(date);
    }



    public Sent(){
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	this.setStatus("Sent");
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
    public void entregarOrder(){
       // this.order.setStatus(new Delivered(this.order));
    }
    @Override
    public void entregarOrder(Date date){
        //this.order.setStatus(new Delivered(this.order, date));
    }
    @Override
    public void cancelarOrder(){}
    @Override
    public void cancelarOrder(Date date){}
    @Override
    public void enviarOrder(){}
    @Override
    public void enviarOrder(Date date){}

}
