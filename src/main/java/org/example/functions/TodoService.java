package org.example.functions;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class TodoService {

    public List<Todo> getTodos() {

        MongoClient mongoClient = getMongoClient();

        MongoCollection<Todo> collection = getTodoCollection(mongoClient);

        List<Todo> todos = new ArrayList<>();
        MongoCursor<Todo> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Todo todo = cursor.next();
            todos.add(todo);
        }

        mongoClient.close();

        return todos;
    }

    public Todo getTodo(String id) {
        MongoClient mongoClient = getMongoClient();

        MongoCollection<Todo> collection = getTodoCollection(mongoClient);

        Bson filter = getCollectionFilterById(id);

        Todo todo = collection.find(filter).first();

        mongoClient.close();

        return todo;
    }

    public Todo createTodo(Todo todo) {
        MongoClient mongoClient = getMongoClient();

        MongoCollection<Todo> collection = getTodoCollection(mongoClient);

        ObjectId objId = new ObjectId();
        todo.setId(objId.toString());
        collection.insertOne(todo);

        Todo newTodo = getTodo(objId.toString());

        mongoClient.close();

        return newTodo;
    }

    public Todo updateTodo(String id, Todo todo) {
        MongoClient mongoClient = getMongoClient();

        MongoCollection<Todo> collection = getTodoCollection(mongoClient);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("_id", id);

        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", todo);

        collection.updateOne(searchQuery, setQuery);

        Todo newTodo = getTodo(id);

        mongoClient.close();

        return newTodo;
    }

    public void deleteTodo(String id) {
        MongoClient mongoClient = getMongoClient();
        MongoCollection<Todo> collection = getTodoCollection(mongoClient);

        Bson filter = getCollectionFilterById(id);

        collection.findOneAndDelete(filter);

        mongoClient.close();
    }

    private MongoClient getMongoClient(){
        ConnectionString connectionString = new ConnectionString("<MongoDB Connection String>");

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(
                        PojoCodecProvider.builder().automatic(true).build(),
                        new UuidCodecProvider(UuidRepresentation.STANDARD)
                )
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        return mongoClient;
    }

    private MongoCollection<Todo> getTodoCollection(MongoClient mongoClient){
        MongoDatabase todoAppDatabase = mongoClient.getDatabase("TodoApp");
        MongoCollection<Todo> collection = todoAppDatabase.getCollection("todos", Todo.class);
        return collection;
    }

    private Bson getCollectionFilterById(String id){
        Bson filterCollectionById = Filters.eq("_id", id);
        return filterCollectionById;
    }

}
