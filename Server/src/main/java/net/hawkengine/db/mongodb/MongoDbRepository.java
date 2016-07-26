package net.hawkengine.db.mongodb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import net.hawkengine.db.IDbRepository;
import net.hawkengine.model.DbEntry;

import org.bson.types.ObjectId;

import javax.ws.rs.NotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MongoDbRepository<T extends DbEntry> implements IDbRepository<T> {

    private MongoCollection collection;
    private Type entryType;
    private Gson jsonConverter;
    private MongoDatabase mongoDatabase;

    public MongoDbRepository(Class<T> entry) {
        this.entryType = entry;
        this.jsonConverter = new GsonBuilder().create();
        this.mongoDatabase = MongoDbManager.getInstance().getDb();
        this.collection = this.mongoDatabase.getCollection(this.entryType.getTypeName());
    }

    public MongoDbRepository(Class entry, MongoCollection mockedMongoCollection) {
        this.entryType = entry;
        this.jsonConverter = new GsonBuilder().create();
        this.collection = mockedMongoCollection;
    }

    @Override
    public T getById(String id) {
        T result;
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("_id",new ObjectId(id));
            DBObject document = (DBObject) collection.find(query);

            if (document != null) {
//                String document = JSON.serialize(documents.next());
                result = this.jsonConverter.fromJson((JsonElement) document, this.entryType);
                return result;
            }
            return null;
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<T> getAll() {
        T resultElement;
        List<T> result = new ArrayList<>();
        try {
            FindIterable documents = this.collection.find();

            for (Object document : documents) {
                String documentToJson = JSON.serialize(document);
                resultElement = this.jsonConverter.fromJson(documentToJson, this.entryType);
                result.add(resultElement);
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    @Override
    public T add(T entry) {
        if (this.getById(entry.getId()) == null) {
            try {
                String entryToJson = this.jsonConverter.toJson(entry);
                DBObject myDoc = (DBObject) JSON.parse(entryToJson);

                this.collection.insertOne(myDoc);

                return entry;
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            return null;
        }
    }

    @Override
    public T update(T entry) {
        try {
            BasicDBObject newDocument = (BasicDBObject) JSON.parse(this.jsonConverter.toJson(entry));

            BasicDBObject searchQuery = new BasicDBObject().append("id", entry.getId());

            this.collection.findOneAndUpdate(searchQuery, newDocument);

            return entry;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T delete(String id) {
        T result = null;
        try {
            BasicDBObject searchQuery = new BasicDBObject().append("id", id);
            DBObject dbObject = (DBObject) this.collection.findOneAndDelete(searchQuery);
            String document = JSON.serialize(dbObject);
            result = this.jsonConverter.fromJson(document, this.entryType);
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return result;
        }
    }
}