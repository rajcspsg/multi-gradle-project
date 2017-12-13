package com.demo.aerospike.repositories;

import com.demo.aerospike.entity.Person;
import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.List;

public interface PersonRepository extends AerospikeRepository<Person, String> {

    List<Person> findByName(String name);

    List<Person> findByNameStartsWith(String prefix);
}
