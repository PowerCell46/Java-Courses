package bg.softuni.automappingobjectsexercise.services.impls;

import bg.softuni.automappingobjectsexercise.entities.Order;
import bg.softuni.automappingobjectsexercise.repositories.OrderRepository;
import bg.softuni.automappingobjectsexercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
