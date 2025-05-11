package org.teta.client;

import org.teta.configuration.FeignConfiguration;
import org.teta.constants.FeignConstants;
import org.teta.constants.PathConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = FeignConstants.WEBSOCKET_SERVICE)
@FeignClient(name = FeignConstants.WEBSOCKET_SERVICE, configuration = FeignConfiguration.class)
public interface WebSocketClient {

    @PostMapping(PathConstants.API_V1_WEBSOCKET)
    void send(@RequestParam("destination") String destination, @RequestBody Object request);
}
