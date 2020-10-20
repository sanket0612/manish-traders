package com.manishtraders;

import com.manishtraders.model.Order;
import com.manishtraders.model.TallyLedger;
import com.manishtraders.repository.LedgerRepository;
import com.manishtraders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final OrderRepository orderRepository;

    private final LedgerRepository ledgerRepository;

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Sanket","Ritu","Chanki","Manish").forEach(partyName -> orderRepository.save(new Order(1, partyName)));

        Stream.of("Sanket","Tally").map(name-> TallyLedger.builder().name(name).build()).forEach(ledgerRepository::save);
        Order order = orderRepository.findByPartyName("Sanket");

        orderRepository.findAll().forEach(System.out::println);
    }
}
