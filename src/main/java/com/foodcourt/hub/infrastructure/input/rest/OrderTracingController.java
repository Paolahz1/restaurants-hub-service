package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hub-service/order/tracing")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order-tracing related endpoints")
public class OrderTracingController {

    private final IOrderHandler handler;


    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<GetTracingOrderByClientResponse> getTracingOrderByClientAndOrderId(
            @PathVariable long orderId, @AuthenticationPrincipal UserPrincipal principal) {

        long clientId = principal.id();
        GetTracingOrderByClientResponse response = handler.getTracingOrderByClient(orderId, clientId);
        return ResponseEntity.ok(response);
    }

}





