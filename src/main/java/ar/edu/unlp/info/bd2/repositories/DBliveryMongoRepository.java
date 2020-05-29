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
    
    public ObjectId createProduct(Product product) {
    	Document document = new Document();
    	document.append("name", product.getName());
    	document.append("price", product.getPrice());
    	document.append("weight", product.getWeight());
    	document.append("Supplier", product.getSupplier());
    	this.getDb().getCollection("Product").insertOne(document);
    	ObjectId id = (ObjectId)document.get("_id");
    	return id;
    }
    
    public ObjectId createSupplier(Supplier supplier){
    	Document document = new Document();
    	document.append("name", supplier.getName());
    	document.append("cuil", supplier.getCuil());
    	document.append("address", supplier.getAddress());
    	document.append("coordX", supplier.getCoordX());
    	document.append("coordY", supplier.getCoordY());
    	this.getDb().getCollection("Supplier").insertOne(document);
    	ObjectId id = (ObjectId)document.get("_id");
    	return id;

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


}
