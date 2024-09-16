package com.photon.order_service.controller;

import com.photon.order_service.entity.Order;
import com.photon.order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/orders/")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(OrderRestController.class);

    @PostMapping
    public ResponseEntity<Order> createOreder(@RequestBody Order order){
        logger.info("create Oreder  with order request : "+order.toString());
        try{
        Order savedOrder = orderService.createOrder(order);
            logger.info("Oreder has been saved : "+savedOrder.toString());
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (RuntimeException exception){
            logger.error("Bad request : "+exception.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
