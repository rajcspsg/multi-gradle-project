package com.demo.aerospike;

import com.aerospike.client.query.IndexType;
import com.demo.aerospike.config.DuplicateConfig;
import com.demo.aerospike.entity2.Person;
import com.demo.aerospike.repositories2.PersonRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DuplicateNameSpaceDemo {
    public static void main(String []args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(DuplicateConfig.class);

        PersonRepository repository = ctx.getBean(PersonRepository.class);;

        repository.deleteAll();

        repository.createIndex(Person.class, "person_name_index_repository", "name", IndexType.STRING);

        Person dave = new Person("Dave-01", "Matthews", 42);
        Person donny = new Person("Dave-02", "Macintire", 39);
        Person oliver = new Person("Oliver-01", "Matthews", 4);
        Person carter = new Person("Carter-01", "Beauford", 49);
        Person boyd = new Person("Boyd-01", "Tinsley", 45);
        Person stefan = new Person("Stefan-01", "Lessard", 34);
        Person leroi = new Person("Leroi-01", "Moore", 41);
        Person leroi2 = new Person("Leroi-02", "Moore", 25);
        Person alicia = new Person("Alicia-01", "Keys", 30);

        repository.save(Arrays.asList(oliver,
                dave, donny, carter, boyd, stefan, leroi, leroi2, alicia));
        Iterable<Person> savedIterablePersons = repository.findAll();

        List<Person> savedPersons= StreamSupport.stream(savedIterablePersons.spliterator(), false)
                .collect(Collectors.toList());

        System.out.println(savedPersons);
    }
}
