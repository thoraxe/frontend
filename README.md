# frontend
_The reactive microservice demo frontend_

This service exposes a web user interface to run the reactive microservice demo. It lets the user invokes the different microservices using 3 different invocation patterns:

1. the browser is the client - and interacts directly with the vert.x event bus to invoke the other services
2. the browser uses an API gateway using REST - and the frontend (server) invokes the services in parallel, and sends the aggregted results to the browser.
3. the browser calls `hello`, calling `ola`... - the browser uses the event bus to invoke the first service of the chain. It gets the results as a reply.

The detailed instructions to run the Red Hat Reactive MSA demo, can be found at the following repository: https://github.com/redhat-reactive-msa/redhat-reactive-msa

## Execute the frontend locally

Open a command prompt and navigate to the root directory of this microservice.
Then to package this microservice run:

```
mvn clean package
```

Type this command to execute the application:

```
java -jar target/frontend-0.0.1-SNAPSHOT-fat.jar -cluster
```

The UI is available on `http://localhost:8080`.

## Execute on Openshift

Refer to https://github.com/redhat-reactive-msa/redhat-reactive-msa.
