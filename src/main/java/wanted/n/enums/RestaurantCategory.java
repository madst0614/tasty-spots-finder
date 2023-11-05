package wanted.n.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RestaurantCategory {
    CAFE("까페"),
    BUFFET("뷔페식"),
    CHINESE_FOOD("중국식"),
    JAPANESE_FOOD("일식"),
    FAST_FOOD("패스트푸드");

    private final String value;
}
