package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "pending")
public class Pending extends OrderStatus{
    private String status;


    public Pending (Order order){
        super(order);
        this.setStatus("Pending");
    }

    @Override
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void cancelarOrder(){
       this.order.setStatus(new Cancelled(this.order));
    }
    public void enviarOrder(){
        this.order.setStatus(new Sent(this.order));
    }
    public void entregarOrder(){}
}
