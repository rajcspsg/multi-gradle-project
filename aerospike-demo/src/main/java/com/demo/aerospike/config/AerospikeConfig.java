package com.demo.aerospike.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.core.AerospikeTemplate;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;

@Configuration
@EnableAerospikeRepositories(basePackages = "com.demo.aerospike.repositories")
public class AerospikeConfig {

    public @Bean(destroyMethod = "close") AerospikeClient aerospikeClient() {

        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;
        //String localhost = "172.28.128.3";
        String localhost = "localhost";

        return new AerospikeClient(policy, localhost, 3000);
    }

    public @Bean AerospikeTemplate aerospikeTemplate() {
        return new AerospikeTemplate(aerospikeClient(), "test");
    }


}
