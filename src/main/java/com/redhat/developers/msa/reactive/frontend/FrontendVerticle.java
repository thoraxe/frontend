package com.redhat.developers.msa.reactive.frontend;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.util.Arrays;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class FrontendVerticle extends AbstractVerticle {

  @Override
  public void start() {
    Router router = Router.router(vertx);



    // API Gateway
    APIGateway gateway = new APIGateway(vertx);
    router.get("/api").handler(gateway::invokeAll);
    router.get("/api/health").handler(gateway::health);

    // Frontend
    setupSockJsBridge(router);
    router.route("/*").handler(StaticHandler.create("assets"));

    vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(8080);
  }


  private void setupSockJsBridge(Router router) {
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    BridgeOptions options = new BridgeOptions()
        .addOutboundPermitted(
            new PermittedOptions().setAddress("vertx.circuit-breaker"))
        .addInboundPermitted(
            new PermittedOptions().setAddress("vertx.circuit-breaker"));

    Arrays.stream(Services.SERVICES).forEach(
        svc -> options
            .addOutboundPermitted(
                new PermittedOptions().setAddress(svc))
            .addOutboundPermitted(
                new PermittedOptions().setAddress(svc + "/chain"))
            .addOutboundPermitted(
                new PermittedOptions().setAddress(svc + "/health"))
            .addInboundPermitted(
                new PermittedOptions().setAddress(svc))
            .addInboundPermitted(
                new PermittedOptions().setAddress(svc + "/chain"))
            .addInboundPermitted(
                new PermittedOptions().setAddress(svc + "/health"))
    );

    sockJSHandler.bridge(options);
    router.route("/eventbus/*").handler(sockJSHandler);
  }

}
