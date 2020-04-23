package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
//@Table(name = "delivered")
public class Delivered extends OrderStatus{
    private String status;
    private Date startDate;


    public Delivered(Order order, Date date){
        super(order);
        this.setStatus("Delivered");
        this.setStartDate(date);
    }

    public Delivered(Order order){
        super(order);
        this.setStatus("Delivered");
    }

    public Delivered(){

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
    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}
    public void entregarOrder(Date date){}
    public void cancelarOrder(Date date){}
    public void enviarOrder(Date date){}

}
