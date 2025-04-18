package kr.hhplus.be.server.domain.product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findProductById(Long id);

}
