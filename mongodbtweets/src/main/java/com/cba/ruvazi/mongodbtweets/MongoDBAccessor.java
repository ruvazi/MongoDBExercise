
package com.cba.ruvazi.mongodbtweets;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;


public class MongoDBAccessor {

    private String clientURI;
    private String dbName;
    private String collectionName;
    MongoCollection<Document> collection;
    MongoDatabase db;

    public MongoDBAccessor(String clientURI, String dbName, String collectionName) {
        this.clientURI = clientURI;
        this.dbName = dbName;
        this.collectionName = collectionName;
        MongoClientURI connStr = new MongoClientURI(clientURI);
        MongoClient mongoClient = new MongoClient(connStr);

        db = mongoClient.getDatabase(dbName);
        collection = db.getCollection(collectionName);
    }

    /**
     *
     * @return users Amount of distinct users in the collection.
     */
    public int getDistinctUserCount() {

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$user")), // Gathers a group of all users.
                new Document("$group", new Document("_id", null).append("count", new Document("$sum", 1))), // Calculates sum for the already-retrieved group of users, putting the result of $sum into the variable "count".
                new Document("$project", new Document("_id", 0).append("count", 1)) // Returns, with the append specifying the inclusion of the field "count" in the return result.
        ));

        int count = 0;

        for (Document dbObject : output) {

            count = (int) dbObject.get("count");
        }
        return count;
    }

    public List<Object> getTopUsers() {

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                // "user","$user" means that we're creating variables "user", and inserting variables from every user by saying $user.
                // Could, for example, write '22' and we would create 'user' variables only for user 22.
                new Document("$group", new Document("_id", new Document("user", "$user").append("tweet_id", "$id"))), // Get sets of every user and tweet association.
                new Document("$group", new Document("_id", "$_id.user").append("tweet_count", new Document("$sum", 1))), // Get the sum of every occurence of a unique user id.
                new Document("$project", new Document("_id", 0).append("user", "$_id").append("tweet_count", 1)), // Cuts off unnecessary data, leaving only the users and their tweet counts.
                new Document("$sort", new Document("tweet_count", -1)), //-1 sorts in descending order.
                new Document("$limit", 10) // Get the top 10.
        )).allowDiskUse(Boolean.TRUE);

        List<Object> topUsers = new ArrayList<>();

        for (Document dbObject : output) {

            topUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count"));
        }

        return topUsers;
    }

    public List<Object> getGrumpiestUsers() {

        List<Object> condArray = new ArrayList<>();
        List<Object> eqArray = new ArrayList<>();
        List<Object> divideArray = new ArrayList<>();
        eqArray.add("$polarity");
        eqArray.add(0);
        divideArray.add("$tweet_count");
        divideArray.add("$polarity");

        condArray.add(new Document("$eq", eqArray));
        condArray.add(0);
        condArray.add(new Document("$divide", divideArray));

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$user")
                        .append("polarity", new Document("$sum", "$polarity"))
                        .append("tweet_count", new Document("$sum", 1))),
                new Document("$project", new Document("_id", 0)
                        .append("user", "$_id")
                        .append("avg_polarity", new Document("$cond", condArray))
                        .append("polarity", 1)
                        .append("tweet_count", 1)),
                new Document("$sort", new Document("avg_polarity", 1)
                        .append("tweet_count", -1)),
                new Document("$limit", 5)
        )).allowDiskUse(Boolean.TRUE);

        List<Object> grumpiestUsers = new ArrayList<>();

        for (Document dbObject : output) {
//            System.out.println(dbObject);

            grumpiestUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count") + ", " + dbObject.get("polarity"));
        }

        return grumpiestUsers;
    }

    public List<Object> getHappiestUsers() {
        List<Object> condArray = new ArrayList<>();
        List<Object> eqArray = new ArrayList<>();
        List<Object> divideArray = new ArrayList<>();
        eqArray.add("$polarity");
        eqArray.add(0);
        divideArray.add("$tweet_count");
        divideArray.add("$polarity");

        condArray.add(new Document("$eq", eqArray));
        condArray.add(0);
        condArray.add(new Document("$divide", divideArray));

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$user")
                        .append("polarity", new Document("$sum", "$polarity"))
                        .append("tweet_count", new Document("$sum", 1))),
                new Document("$project", new Document("_id", 0) // Do not project _id in the final documents.
                        .append("user", "$_id")
                        // If/Else statement. $cond provides the if/else, which has two documents in the array.
                        // In this case, it's $eq, as in if $polarity equals 0, then return 0,
                        // otherwise, call $divide on $tweet_count and $polarity.
                        // Results are added to the avg_polarity variable in the document.
                        .append("avg_polarity", new Document("$cond", condArray))
                        .append("polarity", 1)
                        .append("tweet_count", 1)),
                new Document("$sort", new Document("avg_polarity", -1)
                        .append("tweet_count", -1)),
                new Document("$limit", 5)
        )).allowDiskUse(Boolean.TRUE);

        List<Object> happiestUsers = new ArrayList<>();

        for (Document dbObject : output) {
//            System.out.println(dbObject);

            happiestUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count") + ", " + dbObject.get("polarity"));
        }

        return happiestUsers;
    }
    
    // To get this function to work, you have to first run the JS script(script.js) in the resources folder on the mongoDB database.

    public List<Object> getTopMentionedUsers() throws Exception {

        MongoDBAccessor mdb = new MongoDBAccessor("mongodb://localhost:27017", "social_net_big", "tweets");
        MongoCollection<Document> mentionCollection = db.getCollection("most_mentioned");

        AggregateIterable<Document> output = mentionCollection.aggregate(Arrays.asList(
                new Document("$sort", new Document("value", -1)),
                new Document("$limit", 5)
        )).allowDiskUse(Boolean.TRUE);

        List<Object> topUsers = new ArrayList<>();

        for (Document dbObject : output) {
            topUsers.add(dbObject.get("_id") + ", " + dbObject.get("value"));
        }
        return topUsers;
    }

    public List<Object> getTopMentioningUsers() {

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$match", new Document("text", new Document("$regex", ".*@.*"))),
                new Document("$group", new Document("_id", new Document("user", "$user").append("tweet_id", "$id"))),
                new Document("$group", new Document("_id", "$_id.user").append("tweet_count", new Document("$sum", 1))),
                new Document("$project", new Document("_id", 0).append("user", "$_id").append("tweet_count", 1)),
                new Document("$sort", new Document("tweet_count", -1)),
                new Document("$limit", 10)
        )).allowDiskUse(Boolean.TRUE);

        List<Object> topUsers = new ArrayList<>();

        for (Document dbObject : output) {
            topUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count"));
        }

        return topUsers;
    }

}
