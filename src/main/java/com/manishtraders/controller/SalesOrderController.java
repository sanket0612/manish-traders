package com.manishtraders.controller;

import com.manishtraders.model.Order;
import com.manishtraders.model.User;
import com.manishtraders.repository.OrderRepository;
import com.manishtraders.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class SalesOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/order")
    public Collection<Order> getAllOrders(Principal principal){
        return orderRepository.findAllByUserId(principal.getName());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Optional<Order> order = orderRepository.findById(id);
        return order.map(response-> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order,
                                             @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Creating a new sales order with body {}", order);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        //check to see if user exists
        Optional<User> user = userRepository.findById(userId);
        order.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));

        Order result = orderRepository.save(order);
        return ResponseEntity.created(new URI("/v1/api/order" + result.getId()))
                .body(result);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order){
        log.info("Updating the order {}", order);
        Order result = orderRepository.save(order);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id){
        log.info("Deleteing order by id {}", id);
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
