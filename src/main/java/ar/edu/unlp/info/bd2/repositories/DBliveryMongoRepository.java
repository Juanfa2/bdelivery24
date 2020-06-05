package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
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
	
	public FindIterable<Product>  getProductsByName(String name){
		FindIterable<Product> product =  this.getDb().getCollection("Product", Product.class).find(regex("name", name));
		return product;
	}


}
