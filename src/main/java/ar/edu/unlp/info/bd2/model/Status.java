package ar.edu.unlp.info.bd2.model;

public interface Status {

    public String getStatus();
    public void setOrderStatus(OrderStatus orderStatus);
    public void setStatus(String status);
    public void entregarOrder();
    public void cancelarOrder();
    public void enviarOrder();

}
