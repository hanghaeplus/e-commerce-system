package kr.hhplus.be.server.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findProductById(Long productId);

    List<Product> findEnabledProductByOptionIds(List<Long> optionIds);

    List<Stock> findStocksByOptionIds(List<Long> optionIds);

    List<Stock> saveStocks(List<Stock> stocks);

}
