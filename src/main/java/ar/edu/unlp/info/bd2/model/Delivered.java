package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@Table(name = "delivered")
public class Delivered extends OrderStatus{
    private String status;

    public Delivered(Order order){
        super(order);
        this.setStatus("Delivered");
    }

    @Override
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}

}
