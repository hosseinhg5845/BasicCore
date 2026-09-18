package club.mineman.basic.managers;

import club.mineman.basic.Basic;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collections;

public class MongoDBManager {
    private final Basic plugin;
    private MongoClient client;
    private MongoDatabase database;
    private boolean connected;

    public MongoDBManager(Basic plugin) {
        this.plugin = plugin;
        this.connected = false;
    }

    public boolean connect() {
        try {
            String host = this.plugin.getConfig().getString("mongodb.host", "localhost");
            int port = this.plugin.getConfig().getInt("mongodb.port", 27017);
            String dbName = this.plugin.getConfig().getString("mongodb.database", "mineman");
            String username = this.plugin.getConfig().getString("mongodb.username", "");
            String password = this.plugin.getConfig().getString("mongodb.password", "");
            boolean authEnabled = this.plugin.getConfig().getBoolean("mongodb.auth-enabled", false);

            if (authEnabled && !username.isEmpty()) {
                MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
                this.client = new MongoClient(
                        new ServerAddress(host, port),
                        Collections.singletonList(credential)
                );
            } else {
                this.client = new MongoClient(
                        new ServerAddress(host, port)
                );
            }

            this.database = this.client.getDatabase(dbName);
            this.database.runCommand(new Document("ping", 1));
            this.connected = true;
            this.plugin.getLogger().info("Connected to MongoDB successfully!");
            return true;

        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to connect to MongoDB: " + e.getMessage());
            this.connected = false;
            this.client = null;
            this.database = null;
            return false;
        }
    }

    public void disconnect() {
        if (this.client != null) {
            this.client.close();
            this.connected = false;
            this.plugin.getLogger().info("Disconnected from MongoDB.");
        }
    }

    public boolean isConnected() {
        return this.connected && this.client != null;
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoCollection<Document> getCollection(String name) {
        if (!isConnected()) {
            return null;
        }
        return this.database.getCollection(name);
    }

    public void saveDocument(String collection, Document document) {
        if (!isConnected()) {
            return;
        }
        try {
            MongoCollection<Document> coll = this.database.getCollection(collection);
            String id = document.getString("_id");
            if (id == null || id.isEmpty()) {
                coll.insertOne(document);
            } else {
                coll.replaceOne(new Document("_id", id), document);
            }
        } catch (Exception e) {
            this.plugin.getLogger().warning("Error saving document: " + e.getMessage());
        }
    }

    public Document loadDocument(String collection, String id) {
        if (!isConnected()) {
            return null;
        }
        try {
            MongoCollection<Document> coll = this.database.getCollection(collection);
            return coll.find(new Document("_id", id)).first();
        } catch (Exception e) {
            this.plugin.getLogger().warning("Error loading document: " + e.getMessage());
            return null;
        }
    }
}