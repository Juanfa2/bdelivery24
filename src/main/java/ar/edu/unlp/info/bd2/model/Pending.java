package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "pending")
public class Pending extends OrderStatus{



    public Pending (Order order){
        super(order,"Pending" );
    }

    @Override
    public void cancelarOrder(){
       this.order.setStatus(new Cancelled(this.order));
    }
    public void enviarOrder(){
        this.order.setStatus(new Sent(this.order));
    }
    public void entregarOrder(){}
}
