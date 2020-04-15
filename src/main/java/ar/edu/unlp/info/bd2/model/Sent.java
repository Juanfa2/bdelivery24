package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "sent")
public class Sent extends OrderStatus{
    private String status;
 //   private OrderStatus orderStatus;

    public Sent(Order order){
        super(order,"Sent" );
    }

    @Override
    public void entregarOrder(){
        this.order.setStatus(new Delivered(this.order));
    }
    public void cancelarOrder(){}
    public void enviarOrder(){}
}
