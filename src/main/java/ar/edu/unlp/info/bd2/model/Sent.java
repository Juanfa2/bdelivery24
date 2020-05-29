package ar.edu.unlp.info.bd2.model;


import java.util.Date;

import org.bson.types.ObjectId;


public class Sent extends OrderStatus{
    private String status;
    private Date startDate;


    public Sent(Order order, Date date){
        super(order);
        this.setStatus("Sent");
        this.setStartDate(date);
    }

    public Sent (Order order){
        super(order);
        this.setStatus("Sent");
    }

    public Sent(){

    }

    @Override
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStartDate(Date date){
        this.startDate = date;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public void entregarOrder(){
        this.order.setStatus(new Delivered(this.order));
    }
    public void entregarOrder(Date date){
        this.order.setStatus(new Delivered(this.order, date));
    }
    public void cancelarOrder(){}
    public void cancelarOrder(Date date){}
    public void enviarOrder(){}
    public void enviarOrder(Date date){}

	@Override
	public void setObjectId(ObjectId objectId) {
		// TODO Auto-generated method stub
		
	}
}
