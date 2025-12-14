package ru.zaikin.orderservice.repository;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.zaikin.orderservice.model.Product;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Epic("Order Service")
@Feature("Product Repository")
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @Story("Сохранение и чтение продукта")
    @Severity(SeverityLevel.NORMAL)
    @Description("Продукт должен сохраняться и читаться из базы данных")
    void saveAndFind() {
        Product p = new Product("x", "y", new BigDecimal("2.5"));

        Allure.step("Сохраняем продукт", () -> {
            Product saved = repository.save(p);
            assertThat(saved.getId()).isNotNull();
        });

        Allure.step("Читаем продукт по id", () -> {
            var found = repository.findById(p.getId());
            assertThat(found).isPresent();
        });
    }
}
