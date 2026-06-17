package Warehousemanagement.project.service.service;

import java.util.List;

public interface ProductService {
        Detail create(create request);
        Detail getById(Integer id);
        List<ProductSummary> getAll();
        List<UserSummary> search(Integer query);
        Detail update(Integer id, UpdateProduct req);
        boolean delete(String id);
}
