package com.redhat.developers.msa.reactive.frontend;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
class APIGateway {


  private static final DeliveryOptions DELIVERY_OPTIONS =
      new DeliveryOptions().setSendTimeout(2000);

  private final Vertx vertx;

  APIGateway(Vertx vertx) {
    this.vertx = vertx;
  }


  void health(RoutingContext context) {
    List<Future> futures = new ArrayList<>();
    JsonObject result = new JsonObject();
    for (String svc : Services.SERVICES) {
      futures.add(ping(svc, result));
    }

    CompositeFuture.all(futures).setHandler(
        ar -> context.response()
            .putHeader("content-type", "application/json")
            .end(result.encode()));
  }

  void invokeAll(RoutingContext context) {
    String name = context.request().getParam("name");
    JsonObject result = new JsonObject();

    List<Future> futures = new ArrayList<>();
    for (String svc : Services.SERVICES) {
      futures.add(invoke(svc, name, result));
    }

    CompositeFuture.all(futures).setHandler(ar ->
        context.response()
            .putHeader("content-type", "application/json")
            .end(result.encode())
    );

  }

  private Future<Void> invoke(String service, String name, JsonObject result) {
    Future<Void> future = Future.future();
    vertx.eventBus().<JsonObject>send(service, name,
        DELIVERY_OPTIONS,
        ar -> {
          result.put(service, ar.failed() ? "D'oh! can't invoke " + service
              : ar.result().body().getString(service));
          future.complete();
        });
    return future;
  }

  private Future<Void> ping(String service, JsonObject result) {
    Future<Void> future = Future.future();
    vertx.eventBus().<JsonObject>send(service + "/health", null,
        DELIVERY_OPTIONS,
        ar -> {
          result.put(service, ar.failed() ? "ko" : "ok");
          future.complete();
        });
    return future;
  }
}
