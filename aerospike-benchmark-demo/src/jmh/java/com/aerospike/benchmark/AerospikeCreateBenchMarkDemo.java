package com.aerospike.benchmark;

import com.aerospike.client.query.IndexType;
import com.demo.aerospike.config.AerospikeConfig;
import com.demo.aerospike.entity.Person;
import com.demo.aerospike.repositories.PersonRepository;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.demo.aerospike.entity")
@ComponentScan(basePackages = {"com.demo.aerospike", "com.demo.aerospike"})
@EnableJpaRepositories("com.demo.aerospike.repositories")*/


public class AerospikeCreateBenchMarkDemo {

    @State(Scope.Benchmark)
    public static class AerospikeCreateBenchmarkPlan {

        @Autowired
        private ApplicationContext context;

        @Autowired
        private PersonRepository personRepository;

        public PersonRepository getPersonRepository() {
            try {
                String args = "";
                if(context == null) {
                    context = SpringApplication.run(AerospikeConfig.class, args );
                }
                personRepository = context.getBean(PersonRepository.class);
                if(personRepository != null)
                    System.out.println(personRepository + " is not null");
                else
                    System.out.println(personRepository + "is null");
            } catch(Exception e) {
                e.printStackTrace();
            }
            return personRepository;
        }

        @Param({ "10" })
        public int iterations;

        public List<Person> persons = new ArrayList<>();

        Random rand = new Random();

        @Setup(Level.Trial)
        public void setUp() {
            try {
                String args = "";
                if(context == null) {
                    context = SpringApplication.run(AerospikeConfig.class, args );
                }
                personRepository = context.getBean(PersonRepository.class);
                if(personRepository != null)
                System.out.println(personRepository + " is not null");
                else
                    System.out.println(personRepository + "is null");
            } catch(Exception e) {
                e.printStackTrace();
            }
            for(int i=0; i < iterations; i++) {
                persons.add(generatePerson());
            }
        }

        private Person generatePerson() {
            String id = UUID.randomUUID().toString();
            String name = UUID.randomUUID().toString();
            int age = rand.nextInt();
            return new Person(id, name, age);
        }
    }

    @Fork(value = 1, warmups = 2)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5)
    public void createPerson(AerospikeCreateBenchmarkPlan plan) {
        PersonRepository personRepository;

        if(plan.getPersonRepository() != null) {
            personRepository = plan.getPersonRepository();
        } else {
            ApplicationContext context = SpringApplication.run(AerospikeConfig.class, "" );
            personRepository = context.getBean(PersonRepository.class);
        }


        plan.persons.forEach(p -> plan.getPersonRepository().save(p));
    }

}
