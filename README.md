The multi-gradle-project consists of 2 sub projects

1. aerospike-demo and
2. aerospike-benchmark-demo

<b> <h7> aerospike-demo</h7> </b>


This is spring data project to connect with remote aerospike database.

If you don't have aerospike, please run the below command

`docker run -d -p 3000:3000 -p 3001:3001 -p 3002:3002 -p 3003:3003  -p 8081:8081  --name aerospike aerospike/aerospike-server
`

Then run the application `com.demo.aerospike.MySpringDataAerospikeDemo`


<b>aerospike-benchmark-demo</b>

This project depends on aerospike-demo and benchmarks the CRUD operations on aerospike.

Run this project using the command `gradle :aerospike-benchmark-demo:jmh --stacktrace`
  
