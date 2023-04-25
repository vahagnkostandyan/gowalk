package io.gowalk.gowalk.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        Throwable error = getError(request);

        if (error instanceof GoWalkException ex) {
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", ex.getMessage());
            return map;
        }

        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", "Something went wrong.");
        return map;
    }

}
