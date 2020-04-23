package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
//@Table(name = "pending")
public class Pending extends OrderStatus{
    private String status;
    private Date startDate;

    public Pending(Order order, Date date){
        super(order);
        this.setStatus("Pending");
        this.setStartDate(date);
    }

    public Pending (Order order){
        super(order);
        this.setStatus("Pending");
    }

    public Pending(){

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
    public void cancelarOrder(){
       this.order.setStatus(new Cancelled(this.order));
    }
    public void cancelarOrder(Date date){
        this.order.setStatus(new Cancelled(this.order, date));
    }
    public void enviarOrder(){
        this.order.setStatus(new Sent(this.order));
    }
    public void enviarOrder(Date date){
        this.order.setStatus(new Sent(this.order));
    }
    public void entregarOrder(){}
    public void entregarOrder(Date date){}

}
