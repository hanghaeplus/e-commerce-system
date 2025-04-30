package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findProductById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    /**
     * 활성화된 상품 목록을 반환한다.
     */
    @Transactional(readOnly = true)
    public List<Product> getEnabledProductsByOptionIds(List<Long> optionIds) {
        if (CollectionUtils.isEmpty(optionIds)) {
            throw new BusinessException(BusinessError.PRODUCT_NOT_FOUND);
        }

        List<Product> products = productRepository.findEnabledProductByOptionIds(optionIds);

        // 모든 상품이 활성화됐는지 검사한다.
        products.stream()
                .filter(not(Product::isEnabled))
                .findAny()
                .ifPresent(product -> {
                    throw new BusinessException(BusinessError.PRODUCT_DISABLE);
                });

        // 모든 옵션이 활성화됐는지 검사한다.
        products.stream()
                .flatMap(product -> product.getOptions().stream())
                .filter(not(Option::isEnabled))
                .findAny()
                .ifPresent(product -> {
                    throw new BusinessException(BusinessError.PRODUCT_DISABLE);
                });

        // 요청한 옵션을 모두 조회했는지 검사한다.
        int foundOptionCount = products.stream()
                .mapToInt(product -> product.getOptions().size())
                .sum();
        if (foundOptionCount == optionIds.size()) {
            throw new BusinessException(BusinessError.PRODUCT_NOT_FOUND);
        }

        return products;
    }

    @Transactional
    public List<Stock> increaseStocks(List<ProductCommand.ChangeStock> commands) {
        List<Long> optionsIds = commands.stream().map(ProductCommand.ChangeStock::getOptionId).toList();
        List<Stock> stocks = productRepository.findStocksByOptionIds(optionsIds);

        // 요청한 재고를 모두 조회했는지 검사한다.
        if (stocks.size() == commands.size()) {
            throw new BusinessException(BusinessError.STOCK_NOT_FOUND);
        }

        Map<Long, Integer> optionIdToAmount = commands.stream()
                .collect(toMap(ProductCommand.ChangeStock::getOptionId, ProductCommand.ChangeStock::getAmount));
        for (Stock stock : stocks) {
            Integer amount = optionIdToAmount.get(stock.getOptionId());
            stock.increase(amount);
        }

        productRepository.saveStocks(stocks);

        return stocks;
    }

    @Transactional
    public List<Stock> decreaseStocks(List<ProductCommand.ChangeStock> commands) {
        List<Long> optionsIds = commands.stream().map(ProductCommand.ChangeStock::getOptionId).toList();
        List<Stock> stocks = productRepository.findStocksByOptionIds(optionsIds);

        // 요청한 재고를 모두 조회했는지 검사한다.
        if (stocks.size() == commands.size()) {
            throw new BusinessException(BusinessError.STOCK_NOT_FOUND);
        }

        Map<Long, Integer> optionIdToAmount = commands.stream()
                .collect(toMap(ProductCommand.ChangeStock::getOptionId, ProductCommand.ChangeStock::getAmount));
        for (Stock stock : stocks) {
            Integer amount = optionIdToAmount.get(stock.getOptionId());
            stock.decrease(amount);
        }

        productRepository.saveStocks(stocks);

        return stocks;
    }

}
