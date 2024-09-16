package com.photon.order_service.service;

import com.photon.order_service.entity.Order;
import com.photon.order_service.respository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.base.url}")
    private String productServiceBaseUrl;

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order createOrder(Order order){
        List<Long> productIds = order.getProducts();
        logger.info("Product ids "+productIds);
        for (long productId : productIds){
            try {
                restTemplate.getForObject(productServiceBaseUrl+productId,String.class);
            } catch (Exception exception){
                throw new RuntimeException("Product details not found for id :"+productId);
            }
        }
        order.setOrderDate(LocalDate.now());
       return orderRepository.save(order);
    }

}
