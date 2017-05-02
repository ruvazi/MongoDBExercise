
package com.cba.ruvazi.mongodbtweets;

import java.io.FileNotFoundException;


public class main {
    
    public static void main(String[] args) throws FileNotFoundException, Exception {
        MongoDBAccessor mdb = new MongoDBAccessor("mongodb://localhost:27017","social_net_big","tweets");
        System.out.println(" -- Amount of distinct users: " + mdb.getDistinctUserCount());
        System.out.println(" -- Top users: "+ mdb.getTopUsers());
        System.out.println(" -- Top mentioning users: "+mdb.getTopMentioningUsers());
        System.out.println(" -- Top grumpiest users: "+mdb.getGrumpiestUsers());
        System.out.println(" -- Top happiest users: "+mdb.getHappiestUsers());
        System.out.println(" -- Top mentined users: "+mdb.getTopMentionedUsers());
        System.out.println(" - all ok - ");
    }
    
}
