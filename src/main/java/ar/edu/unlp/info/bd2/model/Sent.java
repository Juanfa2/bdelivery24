package ar.edu.unlp.info.bd2.model;

public class Sent implements Status{
    private String status;
    private OrderStatus orderStatus;

    public Sent(OrderStatus orderStatus){
        this.setStatus("Sent");
        this.setOrderStatus(orderStatus);
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public void entregarOrder(){
        this.orderStatus.setStatu(new Delivered(this.orderStatus));
    }
    public void cancelarOrder(){}
    public void enviarOrder(){}
}
