package com.demo.aerospike.repositories2;

import com.demo.aerospike.entity2.Person;
import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.List;

public interface PersonRepository extends AerospikeRepository<Person, String> {

    List<Person> findByName(String name);

    List<Person> findByNameStartsWith(String prefix);
}
