package com.example.SpringDataJpaDemo.service;

import com.example.SpringDataJpaDemo.dto.CreateOrderDto;
import com.example.SpringDataJpaDemo.dto.OrderDto;
import com.example.SpringDataJpaDemo.entities.Order;
import com.example.SpringDataJpaDemo.entities.User;
import com.example.SpringDataJpaDemo.repository.OrderRepository;
import com.example.SpringDataJpaDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderDto createOrder(Long userId, CreateOrderDto createOrderDto) {
        User user=userRepository.findById(userId).orElseThrow();
        Order order=new Order();
        order.setUser(user);
        order.setProductname(createOrderDto.getProductname());
        Order savedOrder=orderRepository.save(order);
        return new OrderDto(savedOrder.getId(), savedOrder.getProductname(), savedOrder.getUser());
    }
    public List<OrderDto> getOrdersByUserId(Long userId){
        return orderRepository.findOrderByUserId(userId)
                .stream()
                .map(order ->new OrderDto(order.getId(), order.getProductname(),order.getUser()))
                .toList();
    }
}
