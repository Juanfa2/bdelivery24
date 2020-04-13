package ar.edu.unlp.info.bd2.model;

public class Cancelled implements Status{
    private String status;
    private OrderStatus orderStatus;

    public Cancelled(OrderStatus orderStatus){
        this.setStatus("Cancelled");
        this.setOrderStatus(orderStatus);
    }

    @Override
    public String getStatus() {
        return this.status;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void entregarOrder(){}
    public void cancelarOrder(){}
    public void enviarOrder(){}

}
