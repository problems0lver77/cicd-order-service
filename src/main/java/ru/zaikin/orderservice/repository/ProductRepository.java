package ru.zaikin.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zaikin.orderservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
