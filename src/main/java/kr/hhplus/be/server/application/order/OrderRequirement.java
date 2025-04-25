package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.product.ProductCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrderRequirement {

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Place {

        private final Long userId;
        private final List<Item> items;

        @Nullable
        private final List<Long> userCouponIds;

        public List<Long> toOptionIds() {
            return this.items.stream().map(Item::getOptionId).toList();
        }

        public List<ProductCommand.ChangeStock> toChangeStockCommands() {
            return this.items.stream()
                    .map(item -> ProductCommand.ChangeStock.builder()
                            .optionId(item.getOptionId())
                            .amount(item.getQuantity())
                            .build()
                    )
                    .toList();
        }

    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Item {

        private final Long productId;
        private final Long optionId;
        private final Integer quantity;

    }

}
