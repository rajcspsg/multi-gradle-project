package com.demo.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.ClientPolicy;

public class RetreivingKeys {

    public static void main(String []args) {
        AerospikeClient client = new AerospikeClient("172.28.128.3", 3000);
        //AerospikeClient client = new AerospikeClient("localhost", 3000);
        Key key = new Key("test", "Person", "Dave-01");


        // Read a record
        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;
        Record record = client.get(null, key);

        System.out.println("record is "+ record);
        System.out.println("record bins is " + record.bins);

        client.close();
    }
}
