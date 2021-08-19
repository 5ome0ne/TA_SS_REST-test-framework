package entity;

import constant.OrderType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListOptions {
    @Builder.Default private boolean pagination = true;
    @Builder.Default private int page = 1;
    @Builder.Default private int size = 10;
    private OrderType orderType;
    private String sortBy;
}
