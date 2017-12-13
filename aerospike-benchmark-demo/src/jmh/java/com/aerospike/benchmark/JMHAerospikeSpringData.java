package com.aerospike.benchmark;

import com.demo.aerospike.config.AerospikeConfig;
import com.demo.aerospike.entity.Person;
import com.demo.aerospike.repositories.PersonRepository;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Random;
import java.util.UUID;


public class JMHAerospikeSpringData {

    //Aeros

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 2)
    public void generatePerson() {
        Random rand = new Random();
        String id = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        int age = rand.nextInt();
        Person person = new Person(id, name, age);
    }

    public static void main(String[] args) throws RunnerException {
        System.out.println("JMHAerospikeSpringData is running ...");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AerospikeConfig.class);

        PersonRepository repository = ctx.getBean(PersonRepository.class);

        Options options = new OptionsBuilder()
                .include(JMHAerospikeSpringData.class.getSimpleName()).forks(1).build();

        new Runner(options).run();
    }

}
