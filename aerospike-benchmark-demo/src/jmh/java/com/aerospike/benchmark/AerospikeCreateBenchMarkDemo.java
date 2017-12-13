package com.aerospike.benchmark;

import com.demo.aerospike.config.AerospikeConfig;
import com.demo.aerospike.entity.Person;
import com.demo.aerospike.repositories.PersonRepository;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AerospikeCreateBenchMarkDemo {

    @State(Scope.Benchmark)
    public static class AerospikeCreateBenchmarkPlan {

        @Param({ "10" })
        public int iterations;

        public List<Person> persons = new ArrayList<>();

        //public PersonRepository repository;

        Random rand = new Random();

        @Setup(Level.Invocation)
        public void setUp() {
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
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AerospikeConfig.class);
        PersonRepository repository = ctx.getBean(PersonRepository.class);
        plan.persons.forEach(p -> repository.save(p));
    }

}
