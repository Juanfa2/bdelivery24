package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "delivered")
public class Delivered extends OrderStatus{


    public Delivered(Order order){
        super(order, "Delivered");
    }

    @Override
    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}

}
