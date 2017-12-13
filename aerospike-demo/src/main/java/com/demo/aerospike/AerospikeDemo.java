package com.demo.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.demo.aerospike.entity.Person;

public class AerospikeDemo {
    public static void main(String []args) {

        //AerospikeClient client = new AerospikeClient("172.28.128.3", 3000);
        AerospikeClient client = new AerospikeClient("localhost", 3000);
        Key key = new Key("test", "demo", "putgetkey");
        //Key key2 = new Key("1", "2", "3");
        Bin bin1 = new Bin("bin1", 1);
        Bin bin2 = new Bin("bin2", new Person("", "", 0));
        Bin bin3 = new Bin("bin3", "value3");

        // Write a record
        client.put(null, key, bin1, bin2, bin3);

        // Read a record
        Record record = client.get(null, key);

        System.out.println("record is "+ record);
        System.out.println("record bins is " + record.bins);

        client.close();
    }
}
