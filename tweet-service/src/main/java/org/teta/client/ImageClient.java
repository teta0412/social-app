package org.teta.client;

import org.springframework.http.MediaType;
import org.teta.configuration.FeignConfiguration;
import org.teta.constants.FeignConstants;
import org.teta.constants.PathConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@CircuitBreaker(name = FeignConstants.IMAGE_SERVICE)
@FeignClient(name = FeignConstants.IMAGE_SERVICE, path = PathConstants.API_V1_IMAGE, configuration = FeignConfiguration.class)
public interface ImageClient {

    @PostMapping(value = PathConstants.UPLOAD , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImage(@RequestPart("file") MultipartFile file);
}
