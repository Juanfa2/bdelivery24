package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;


    public void saveAssociation(PersistentObject source, PersistentObject destination, String associationName) {
        Association association = new Association(source.getObjectId(), destination.getObjectId());
        this.getDb()
                .getCollection(associationName, Association.class)
                .insertOne(association);
    }

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo" + this.getGroupNumber() );
    }

    private Integer getGroupNumber() { return 24; }

    public <T extends PersistentObject> List<T> getAssociatedObjects(
            PersistentObject source, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("source", source.getObjectId())),
                                        lookup(destCollection, "destination", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    
    public <T extends PersistentObject> List<T> getObjectsAssociatedWith(
            ObjectId objectId, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("destination", objectId)),
                                        lookup(destCollection, "source", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    
    public void createProduct(Product product) {
    	this.getDb().getCollection("Product", Product.class).insertOne(product);
    	
    }
    public void createProductWithDate(Product product) {
    	this.getDb().getCollection("Product", Product.class).insertOne(product);
    }
    
    public void createSupplier(Supplier supplier){
    	this.getDb().getCollection("Supplier", Supplier.class).insertOne(supplier);

    }
    public void createUser(User user) {
    	this.getDb().getCollection("User", User.class).insertOne(user);
    }
    
    public void updateProductPrice(Product product, ObjectId id) {
    	
    	 Bson filter = eq("_id", id);
    	this.getDb().getCollection("Product", Product.class).findOneAndReplace(filter, product);
    }
    
    public Product findProductById(ObjectId id) {
    	
    	Product product = this.getDb().getCollection("Product", Product.class).find(eq("_id", id)).first();
    	return product;
    }
    
    public Supplier findSupplierById(ObjectId id) {
    	
    	Supplier supplier = this.getDb().getCollection("Supplier", Supplier.class).find(eq("_id", id)).first();
    	return supplier;
    }
    
    public void createOrder(Order order) {
    	this.getDb().getCollection("Order", Order.class).insertOne(order);
    }
    
	public Optional<Order> getOrderById (ObjectId id){
    	Order order = this.getDb().getCollection("Order", Order.class).find(eq("_id", id)).first();
    	if(order == null) {
    		return Optional.empty();
    	}
    	return Optional.ofNullable(order);
    }
	
	public void updateOrder(Order order, ObjectId id) {
		Bson filter = eq("_id", id);
    	this.getDb().getCollection("Order", Order.class).findOneAndReplace(filter,order);
	}
	
	public Optional<User> getUserByUsername (String username){
    	User user = this.getDb().getCollection("User", User.class).find(eq("username", username)).first();
    	if(user == null) {
    		return Optional.empty();
    	}
    	return Optional.ofNullable(user);
    }
	
	public Optional<User> getUserByEmail(String email){
    	User user = this.getDb().getCollection("User", User.class).find(eq("email", email)).first();
    	if(user == null) {
    		return Optional.empty();
    	}
    	return Optional.ofNullable(user);
    }
	
	public Optional<User> getUserById (ObjectId id){
    	User user = this.getDb().getCollection("User", User.class).find(eq("_id", id)).first();
    	if(user == null) {
    		return Optional.empty();
    	}
    	return Optional.ofNullable(user);
    }
	
	public List<Product>  getProductsByName(String name){
		FindIterable<Product> products =  this.getDb().getCollection("Product", Product.class).find(regex("name", name));
		List<Product> productos = new ArrayList<>();
		MongoCursor<Product> prod = products.iterator();
		while(prod.hasNext()) {
			productos.add(prod.next());
		}
		return productos;
	}
	
	/*--------------------- PARTE 2 ------------------*/
	
	public List<Order> getAllOrdersMadeByUser(String username){
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(eq("client.username", username));
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = orders.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;
	}
	
	public Product getMaxWeigth() {
		Bson filter = eq("weight", -1);
		Product product = this.getDb().getCollection("Product", Product.class).find().sort(Sorts.descending("weight")).first();
		return product;
	}
	
	
	public List<Order> getPendingOrders(){
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(eq("actualStatus", "Pending"));
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = orders.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;	
	}
	
	public List<Order> getSentOrders() {
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(eq("actualStatus", "Sent"));
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = orders.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;	
	}
	
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
		Bson filter = Filters.and(
				Filters.eq("$gte",startDate),
				Filters.eq("$lte", endDate)
				);
		
		Bson query = Filters.and(
				Filters.eq("actualStatus", "Delivered"),
				Filters.eq("dateOfOrder",filter)
				);
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(query);
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = orders.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;	
	}
	
	public List<Order> getDeliveredOrdersForUser(String username){
		Bson query = Filters.and(
				Filters.eq("actualStatus", "Delivered"),
				Filters.eq("client.username",username)
				);
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(query);
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = orders.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;	
		
	}
	
	public List<Product> getProductsOnePrice(){
		Bson query = eq("prices", eq("$size",1));
		FindIterable<Product> products =  this.getDb().getCollection("Product", Product.class).find(query);
		List<Product> listProducts = new ArrayList<>();
		MongoCursor<Product> prod = products.iterator();
		while(prod.hasNext()) {
			listProducts.add(prod.next());
		}
		return listProducts;	
		
	}


	public List<Product> getSoldProductsOn(Date date) {
		Bson query = Filters.and(
				Filters.eq("dateOfOrder",date)
		);
		FindIterable<Order> orders =  this.getDb().getCollection("Order", Order.class).find(query);
		List<OrderProduct> listop = new ArrayList<>();
		MongoCursor<Order> o = orders.iterator();
		while(o.hasNext()) {
			List<OrderProduct> orderListop =o.next().getProducts();
			listop.addAll(orderListop);
		}
		List<Product> listProducts = listop.stream().map(property -> property.getProduct()).collect(Collectors.toList());
		return listProducts;
	}
	
	public Product getBestSellingProduct() {
		
		Document product = this.getDb()
        .getCollection("Order")
        .aggregate(
                Arrays.asList(
                        unwind("$products"),
                        project(eq("products", 1)),
                        group("$products.product._id", Accumulators.sum("cant", 1)),
                        sort(Sorts.descending("cant")))).first();
		
		ObjectId id = (ObjectId) product.get("_id") ;
		Product prod = this.findProductById(id);
	
		return prod;
	}
	
	public List<Supplier> getTopNSuppliersInSentOrders (int n) {
		
		AggregateIterable<Document> suplier = this.getDb()
		        .getCollection("Order")
		        .aggregate(
		                Arrays.asList(
		                        unwind("$products"),
		                        project(eq("products", 1)),
		                        group("$products.product.supplier._id", Accumulators.sum("cant", 1)),
		                        sort(Sorts.descending("cant")),
		                        limit(n)));
		
		List<Supplier> listSuppliers = new ArrayList<>();
		MongoCursor<Document> prod = suplier.iterator();
		while(prod.hasNext()) {
			ObjectId id = (ObjectId) prod.next().get("_id");
			Supplier sup = this.findSupplierById(id);
			listSuppliers.add(sup);
		}
		
		
		/*
		aggregate([{$unwind:"$products"},
		{$project:{supplier:1,products:1}}, 
		{$group: {_id:"$products.product.supplier._id" ,cant:{$sum:1} }}, 
		{$sort:{cant: -1}}])
		*/
		
		return listSuppliers;
	}
	
	public List<Order> getOrderNearPlazaMoreno() {
		Point newPoint = new Point(new Position(-34.921236,-57.954571));
		FindIterable<Order> result = this.getDb().getCollection("Order", Order.class).find(
				Filters.near("position", newPoint,400.0, 0.0));
		List<Order> listOrders = new ArrayList<>();
		MongoCursor<Order> ord = result.iterator();
		while(ord.hasNext()) {
			listOrders.add(ord.next());
		}
		return listOrders;
	}
}
