# Virtual Threads Quarkus Demo

Quarkus demo application with three controllers:
* `ControllerEvolution1_Sequential`: sequential code on platform threads
* `ControllerEvolution2_CompletableFuture`: asynchronous code using CompletableFuture
* `ControllerEvolution3_VirtualThreads`: sequential code on virtual threads

Start the application in dev mode using:

```shell
mvn quarkus:dev
```

Then query the endpoints using:

```shell
curl localhost:8080/stage1-seq/product/1
curl localhost:8080/stage2-cf/product/1
curl localhost:8080/stage3-vt/product/1
```

Within IntelliJ, you can also open the `endpoints-test.http` file to run the HTTP requests.
