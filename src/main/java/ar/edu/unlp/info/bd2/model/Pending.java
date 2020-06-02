package ar.edu.unlp.info.bd2.model;


import java.util.Date;

import org.bson.types.ObjectId;


public class Pending extends OrderStatus{
    private String status;
    private Date startDate;

    public Pending(){

    }
    public Pending( Date date){
        this.setStatus("Pending");
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
    public void cancelarOrder(){
       //this.order.setStatus(new Cancelled(this.order));
    }
    @Override
    public void cancelarOrder(Date date){
       // this.order.setStatus(new Cancelled(this.order, date));
    }
    @Override
    public void enviarOrder(){
        //this.order.setStatus(new Sent(this.order));
    }
    @Override
    public void enviarOrder(Date date){
        //this.order.setStatus(new Sent(this.order, date));
    }
    @Override
    public void entregarOrder(){}
    @Override
    public void entregarOrder(Date date){}
    
	
	
	


}
