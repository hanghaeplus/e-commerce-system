package kr.hhplus.be.server.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<Product> findProduct(Long productId) {
        return productRepository.findProductById(productId);
    }

}
