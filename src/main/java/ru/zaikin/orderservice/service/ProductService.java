package ru.zaikin.orderservice.service;

import org.springframework.stereotype.Service;
import ru.zaikin.orderservice.model.Product;
import ru.zaikin.orderservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() { return repository.findAll(); }

    public Optional<Product> findById(Long id) { return repository.findById(id); }

    public Product create(Product product) { return repository.save(product); }

    public Optional<Product> update(Long id, Product incoming) {
        return repository.findById(id).map(existing -> {
            existing.setName(incoming.getName());
            existing.setDescription(incoming.getDescription());
            existing.setPrice(incoming.getPrice());
            return repository.save(existing);
        });
    }

    public void delete(Long id) { repository.deleteById(id); }
}