package wanted.n.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import wanted.n.domain.Restaurant;
import wanted.n.domain.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscordWebhookRequestDTO {

    @Value("${discord.bot}")
    private String username;

    private String content;

    @Setter
    private List<Embed> embeds;

    public static DiscordWebhookRequestDTO from(User user) {
        return DiscordWebhookRequestDTO.builder()
                .content(user.getAccount() + "님! 오늘 점심은 이 식당에서 어떠신가요?")
                .embeds(new ArrayList<>())
                .build();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Embed {
        private String title;
        private List<Field> fields;

        public static Embed from(String restaurantType) {
            return builder()
                    .title("오늘의 추천 " + restaurantType)
                    .fields(new ArrayList<>())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Field {
        private String name;
        private String value;
        private boolean inline;

        public static DiscordWebhookRequestDTO.Field from(Restaurant restaurant) {
            return DiscordWebhookRequestDTO.Field.builder()
                    .name(restaurant.getBizPlaceName()+"("+restaurant.getRate()+")")
                    .value(restaurant.getRefinedRoadNameAddress())
                    .inline(true)
                    .build();
        }

    }
}