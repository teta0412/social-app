package org.teta.client;

import constants.FeignConstants;
import constants.PathConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = FeignConstants.WEBSOCKET_SERVICE)
@FeignClient(name = FeignConstants.WEBSOCKET_SERVICE)
public interface WebSocketClient {

    @PostMapping(PathConstants.API_V1_WEBSOCKET)
    void send(@RequestParam("destination") String destination, @RequestBody Object request);
}
