package ar.edu.unlp.info.bd2.model;



import java.util.Date;

import org.bson.types.ObjectId;


public class Cancelled extends OrderStatus{
    private String status;
    private Date startDate;


    public Cancelled(Order order, Date date){
        super(order);
        this.setStatus("Cancelled");
        this.setStartDate(date);
    }

    public Cancelled(Order order){
        super(order);
        this.setStatus("Cancelled");
    }
    public Cancelled(){

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
    public void entregarOrder(Date date){}
    public void cancelarOrder(Date date){}
    public void enviarOrder(Date date){}
    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}

	@Override
	public void setObjectId(ObjectId objectId) {
		// TODO Auto-generated method stub
		
	}

}
