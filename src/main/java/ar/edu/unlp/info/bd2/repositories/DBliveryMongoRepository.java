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
    
    public Product createProduct(Product product) {
 
    	Document document = new Document();
    	document.append("_id", product.getObjectId());
    	document.append("name", product.getName());
    	document.append("price", product.getPrice());
    	document.append("weight", product.getWeight());
    	document.append("Supplier", product.getSupplier());
    	document.append("prices", product.getPrices());
    	document.append("orderProduct", product.getOrderProduct());
    	this.getDb().getCollection("Product").insertOne(document);
    	
    	return product;
    }
    public ObjectId createProductWithDate(Product product) {
    	Document document = new Document();
    	document.append("name", product.getName());
    	document.append("price", product.getPrice());
    	document.append("weight", product.getWeight());
    	document.append("Supplier", product.getSupplier());
    	document.append("date", product.getDate());
    	this.getDb().getCollection("Product").insertOne(document);
    	ObjectId id = (ObjectId)document.get("_id");
    	return id;
    }
    
    public Supplier createSupplier(Supplier supplier){
    	Document document = new Document();
    	document.append("name", supplier.getName());
    	document.append("cuil", supplier.getCuil());
    	document.append("address", supplier.getAddress());
    	document.append("coordX", supplier.getCoordX());
    	document.append("coordY", supplier.getCoordY());
    	this.getDb().getCollection("Supplier").insertOne(document);
    	
    	return supplier;

    }
    public ObjectId createUser(User user) {
    	Document document = new Document();
    	document.append("email", user.getEmail());
    	document.append("password", user.getPassword());
    	document.append("username", user.getUsername());
    	document.append("name", user.getName());
    	document.append("dateOfBirth", user.getDateOfBirth());
    	this.getDb().getCollection("User").insertOne(document);
    	ObjectId id = (ObjectId)document.get("_id");
    	return id;
    }
    
    public void updateProductPrice(Object id, Price price) {
    	Bson filter = eq("_id", id);
    	Document updateDocument =  new Document("price", price.getPrice());
    	Document update = new Document("$set", updateDocument);
    	this.getDb().getCollection("Product").updateOne(filter, update);
    	
    	Document updateDoc=  new Document("prices", price);
    	Document updatePrices = new Document("$push", updateDoc);
    	this.getDb().getCollection("Product").updateOne(filter, updatePrices);
    }
    
    public Product findProductById(ObjectId id) {
    	
    	Document document = new Document("_id", id);
    	Document productoDoc = this.getDb().getCollection("Product").find(document).first();
    	Product product = this.getDb().getCollection("Product").find(document, Product.class).first();
    	List<Document> list = productoDoc.getList("prices", Document.class);
		for(int i = 0; i < list.size(); i++) {
			
			Double pr = (Double) list.get(i).get("price");
			Float priceN = pr.floatValue();
			Date startDate = (Date) list.get(i).get("startDate");
			Price price = new Price(priceN, startDate);
			product.updatePrice(price);
		}
    	return product;
    }
    
    public Order createOrder(Order order) {
    	Document document = new Document();
    	document.append("_id", order.getObjectId());
    	document.append("dateOfOrder", order.getDateOfOrder());
    	document.append("address", order.getAddress());
    	document.append("coordX", order.getCoordX());
    	document.append("CoordY", order.getCoordY());
    	document.append("Client", order.getClient());
    	document.append("actualStatus", order.getActualStatus());
    	document.append("status", order.getStatus());
    	this.getDb().getCollection("Order").insertOne(document);
    	
    	return order;
    }
    


}
