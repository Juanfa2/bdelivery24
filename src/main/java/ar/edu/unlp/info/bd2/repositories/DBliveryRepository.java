package ar.edu.unlp.info.bd2.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderProduct;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;


public class DBliveryRepository{
	
	@Autowired
	private SessionFactory sessionFactory;


    public Optional<User> getUserById(long id) {

        String queryStr = "FROM User WHERE id = :id";
        Query<User> query=this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<User> u= query.uniqueResultOptional();

    	return u;
    }

	public Optional<User> getUserByEmail(String email) {

        String queryStr = "from User where email = :email";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("email", email);
        Optional<User> u=query.uniqueResultOptional();
        return u;
    }

	public Optional<User> getUserByUsername(String username) {

        String queryStr = "from User where username = :username";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("username", username);
        Optional<User>  u = query.uniqueResultOptional();

        return u;
	}

	public Optional<Product> getProductById(Long id) {

        String queryStr = "FROM Product WHERE id Like :id";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<Product> p = query.uniqueResultOptional();

        return p;
    }

	public Optional<Order> getOrderById(Long id) {

        String queryStr = "FROM Order WHERE id Like :id";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<Order> o = query.uniqueResultOptional();

		return o;
	}

	public List<Product> getProductByName(String name) {

        String queryStr = "FROM Product WHERE name LIKE :name";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("name", "%" + name + "%");
        List<Product> products = query.getResultList();

        return products;
	}


    public void save(Object object) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.save(object);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Object object) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.update(object);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

  /*  public String getCurrentStatus(Long order) {
        //List<Product> products = null;
        String queryStr = "FROM orderStatus WHERE order_id LIKE :order"
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("order", order);
        status = query.getResultList();

        return status;

    }*/

    public OrderStatus getLastStatus(Long order) {
        String queryStr = "FROM OrderStatus WHERE order_id LIKE :order ORDER BY startDate DESC";
        Query<OrderStatus> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("order", order);
        query.setMaxResults(1);
        OrderStatus status = query.uniqueResult();

        return status;
    }

    //---------------------------------------TP2 -------------------------------//

    public List<Order> getOrderByUser(String username){
        String queryStr = "from Order o  where o.client.username = :username";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("username", username);
        List<Order> orders = query.getResultList();
        return orders;
    }

    public List<Product> topExpensiveProducts(){
        String queryStr = "from Product p  ORDER BY p.price DESC";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Product> products = query.setMaxResults(9).getResultList();
        return products;

    }

    public List<Order> pendingOrders(){
        String queryStr = "from Order o  where o.actualStatus.status= :status";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("status", "Pending");
        List<Order> orders = query.getResultList();
        return orders;
    }

    public List<Order> sentOrders(){
        String queryStr = "from Order o  where o.actualStatus.status = :status";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("status", "Sent");
        List<Order> orders = query.getResultList();
        return orders;
    }

    public List<Order> deliveredOrdersUser(String username){
        String queryStr = "from Order o  where o.actualStatus.status = :status and o.client.username = :username";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("status", "Delivered");
        query.setParameter("username", username);
        List<Order> orders = query.getResultList();
        return orders;
    }

    public List<User> topUsersMoreOrders(){
        String queryStr = "select o.client from Order o  group by o.client ORDER BY count(o.client) DESC";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<User> users = query.setMaxResults(6).getResultList();
        return users;
    }

    public List<User> lessDeliveryUser(){
        /*
        String queryStr = "select o.deliveryUser from Order o " +
                          "where o.actualStatus.status = :sent or " +
                                "o.actualStatus.status = :delivered " +
                          "group by o.deliveryUser " +
                          "ORDER BY count(o.deliveryUser) ASC";

         */
        String queryStr = "SELECT dusr " +
                          "FROM Order o INNER JOIN o.deliveryUser dusr " +
                          "      INNER JOIN o.orderStatus os " +
                          "WHERE os.status = :sent OR " +
                                "os.status = :delivered " +
                          "GROUP BY o.deliveryUser " +
                          "ORDER BY count(o.deliveryUser) ASC, dusr.name ASC";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("delivered", "Delivered");
        query.setParameter("sent", "Sent");
        List<User> users = query.setMaxResults(5).getResultList();
        return users;
    }

    public List<Supplier> suppliersInSentOrders(int n){
        String queryStr = "SELECT o.producto.supplier " +
                          "FROM OrderProduct o " +
                          //"WHERE o.orderP.actualStatus.status = :sent " +
                          "GROUP BY o.producto.supplier " +
                          "ORDER BY count(*) DESC";
        Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        //query.setParameter("sent", "Sent");
        List<Supplier> suppliers = query.setMaxResults(n).getResultList();
        return suppliers;
    }

    public List<Order> deliveredOrdersInPeriod(Date startDate, Date endDate){
        String queryStr = "from Order o  where o.actualStatus.status= :status and o.actualStatus.startDate between :start and :end ";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("status", "Delivered");
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
        List<Order> orders = query.getResultList();
        return orders;
    }
    public List<Order> cancelleddOrdersInPeriod(Date startDate, Date endDate){
        String queryStr = "from Order o  where o.actualStatus.status= :status and o.actualStatus.startDate between :start and :end ";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("status", "Cancelled");
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
        List<Order> orders = query.getResultList();
        return orders;
    }

    public Supplier supplierLessExpensiveProduct(){
        String queryStr = "select p.products.supplier FROM Price p  ORDER BY p.price ASC";
        Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        Supplier supp = query.setMaxResults(1).uniqueResult();
        return supp;

    }

    public List<Object[]> productsWithPriceAt(Date day){
        String queryStr = "SELECT pri.products, pri.price " +
                          "FROM Price pri " +
                          "WHERE (startDate,product_id,product_id) in " +
                                 "(SELECT max(pri.startDate), pri.products  " +
                                 "FROM Price pri " +
                                 "WHERE pri.startDate <= :day " +
                                 "GROUP BY pri.products)";
        Query<Object[]> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("day", day);
        List<Object[]> prices = query.getResultList();
        return prices;
    }

/*
    public List<Order> ordersCompleteMorethanOneDay(){
        String queryStr = "from Order o  where o.actualStatus.status = :delivered and (day(o.actualStatus.startDate) - day(o.dateOfOrder)) >= 1 ";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("delivered", "Delivered");
        List<Order> orders = query.getResultList();
        return orders;
    }

 */

    public List<Order> sentMoreOneHour() {
        String queryStr = "SELECT os1.order " +
                "FROM OrderStatus os1 " +
                "WHERE " +
                "os1.status = :sent and os1.startDate > os1.order.dateOfOrder ";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("sent", "Sent");
        List<Order> order = query.getResultList();
        return order;
    }

    public Product getBestSellingProduct(){
        String queryStr = "select op.producto from OrderProduct op group by op.producto ORDER BY sum(op.cuantity) DESC";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        Product prod = query.setMaxResults(1).uniqueResult();
        return prod;
    }

    public List<Order> getDeliveredOrdersSameDay(){
        String queryStr = "select os1.order " +
                          "from OrderStatus os1, OrderStatus os2 " +
                          "where os1.order = os2.order " +
                          "and os1.startDate = os2.startDate " +
                          "and os1.status = Delivered " +
                          "and os2.status = Pending";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Order> orders = query.getResultList();
        return orders;
    }

   /* public List<Order> deliveredOrdersSameDay(){
        String queryStr = "from Order o  where o.actualStatus.status = :delivered and day(o.actualStatus.startDate) = day(o.dateOfOrder)  ";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("delivered", "Delivered");
        List<Order> orders = query.getResultList();
        return orders;
    }
    */

    public List<Product> productsOnePrice(){
        String queryStr = "select p.products from Price p  group by p.products having count (p.products) = 1  ";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Product> products = query.getResultList();
        return products;
    }


    public List<Product> productsNotSold(){
        String queryStr = "select p from Product p  where p not in(select distinct op.producto from OrderProduct op)  ";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Product> products = query.getResultList();
        return products;
    }


    public List<Product> productsSoldOn(Date day) {
        String queryStr = "SELECT op.producto " +
                          "FROM OrderStatus os, OrderProduct op " +
                          "WHERE os.order = op.orderP AND " +
                          "os.startDate = :day AND os.status = Pending ";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("day", day);
        List<Product> prod = query.getResultList();

        return prod;
    }

    public List<Order> ordersCompleteMorethanOneDay() {
        String queryStr = "SELECT os1.order " +
                "FROM OrderStatus os1, OrderStatus os2 " +
                "WHERE os1.order = os2.order AND " +
                "os1.status = Pending AND os2.status = Delivered AND " +
                "os1.startDate<os2.startDate";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Order> orders = query.getResultList();

        return orders;

    }

    public List<Product> getProductsIncreaseMoreThan100() {
        String queryStr = "SELECT pri.products " +
                "FROM Price pri " +
                "GROUP BY pri.products " +
                "HAVING min(pri.price) * 2 < max(pri.price)";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        List<Product> prod = query.getResultList();

        return prod;
    }

    public List<Order> orderWithMoreQuantityOfProducts(Date day) {
        String queryStr = "SELECT op.orderP " +
                         "FROM OrderProduct op INNER JOIN op.orderP AS o " +
                         "WHERE o.dateOfOrder = :day " +
                         "AND op.cuantity in " +
                                         "(SELECT MAX(cuantity) " +
                                             "FROM OrderProduct)";

        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("day", day);
        List<Order> orders = query.getResultList();

        return orders;
    }


    public List<Supplier> suppliersDoNotSellOn(Date day) {
        String queryStr = "FROM Supplier " +
                          "WHERE id NOT IN" +
                               "(SELECT sup.id " +
                                "FROM Order ord INNER JOIN ord.orderProduct op " +
                                              "INNER JOIN op.producto prod " +
                                              "INNER JOIN prod.supplier sup " +
                                "WHERE ord.dateOfOrder = :day)";
        Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("day", day);
        List<Supplier> suppliers = query.getResultList();
        return suppliers;
    }


    public List<User> usersSpendingMoreThan(Float amount){
        String queryStr = "select o.orderP.client from OrderProduct o group by o.orderP having  sum(o.producto.price  * o.cuantity) > :amount";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("amount", amount);
        List<User> users = query.getResultList();
        return users;
    }




}

