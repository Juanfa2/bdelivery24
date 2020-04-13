package ar.edu.unlp.info.bd2.model;

public class Pending implements Status{

    private String status;
    private OrderStatus orderStatus;

    public Pending (OrderStatus orderStatus){
        this.setStatus("Pending");
        this.setOrderStatus(orderStatus);
    }

    @Override
    public String getStatus() {
     return this.status;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void cancelarOrder(){
       this.orderStatus.setStatu(new Cancelled(this.orderStatus));
    }

    public void setStatus(String status){
        this.status = status;
    }
    public void enviarOrder(){
        this.orderStatus.setStatu(new Sent(this.orderStatus));
    }
    public void entregarOrder(){}
}
