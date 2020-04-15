package ar.edu.unlp.info.bd2.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "cancelled")
public class Cancelled extends OrderStatus{


    public Cancelled(Order order){
        super(order,"Cancelled");
    }

    @Override
    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}

}
