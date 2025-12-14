package ru.zaikin.orderservice.service;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.zaikin.orderservice.model.Product;
import ru.zaikin.orderservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Epic("Order Service")
@Feature("Product Service")
class ProductServiceTest {

    private final ProductRepository repo = Mockito.mock(ProductRepository.class);
    private final ProductService service = new ProductService(repo);

    @Test
    @Story("Создание продукта")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Сервис должен создать продукт и вернуть его с id")
    void createProduct() {
        Product p = new Product("p1", "desc", new BigDecimal("10.00"));

        Allure.step("Мокаем сохранение в репозитории", () ->
                when(repo.save(any())).thenAnswer(inv -> {
                    Product arg = inv.getArgument(0);
                    arg.setId(1L);
                    return arg;
                })
        );

        Allure.step("Создаём продукт через сервис", () -> {
            Product created = service.create(p);
            assertThat(created.getId()).isEqualTo(1L);
            assertThat(created.getName()).isEqualTo("p1");
        });
    }

    @Test
    @Story("Обновление несуществующего продукта")
    @Severity(SeverityLevel.MINOR)
    @Description("Сервис должен вернуть empty, если продукт не найден")
    void updateNotFound() {
        Allure.step("Мокаем отсутствие продукта", () ->
                when(repo.findById(42L)).thenReturn(Optional.empty())
        );

        Allure.step("Пытаемся обновить продукт", () -> {
            Optional<Product> r = service.update(42L, new Product());
            assertThat(r).isEmpty();
        });
    }
}
