package ru.zaikin.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zaikin.orderservice.model.Product;
import ru.zaikin.orderservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Epic("Order Service")
@Feature("Product API")
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService service;

    @Test
    @Story("Получение списка продуктов")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Контроллер должен вернуть 200 OK при запросе списка продуктов")
    void getAll() throws Exception {
        Allure.step("Мокаем сервис", () ->
                when(service.findAll())
                        .thenReturn(List.of(new Product("a", "b", new BigDecimal("1.0"))))
        );

        Allure.step("Выполняем GET /api/products", () ->
                mvc.perform(get("/api/products"))
                        .andExpect(status().isOk())
        );
    }

    @Test
    @Story("Создание продукта")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Контроллер должен вернуть 201 CREATED при создании продукта")
    void create() throws Exception {
        Product p = new Product("a", "b", new BigDecimal("1.0"));
        p.setId(1L);

        Allure.step("Мокаем создание продукта", () ->
                when(service.create(org.mockito.ArgumentMatchers.any()))
                        .thenReturn(p)
        );

        Allure.step("Выполняем POST /api/products", () ->
                mvc.perform(post("/api/products")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(p)))
                        .andExpect(status().isCreated())
        );
    }
}
