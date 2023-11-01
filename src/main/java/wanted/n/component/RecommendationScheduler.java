package wanted.n.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.domain.Restaurant;
import wanted.n.domain.User;
import wanted.n.dto.DiscordWebhookRequestDTO;
import wanted.n.repository.RestaurantQRepository;
import wanted.n.repository.UserQRepository;
import wanted.n.service.DiscordWebhookService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationScheduler {

    private final RestaurantQRepository restaurantQRepository;
    private final UserQRepository userQRepository;
    private final DiscordWebhookService discordWebhookService;

    /**
     * 매일 11:30에 실행되는 스케줄링
     * 점심추천에 동의한 사용자들에게 점심 추천 알림을 보냅니다.
     */

    @Scheduled(cron = "0 30 11 * * ?")
    @Transactional(readOnly = true)
    public void sendRecommendations() {
        // 점심 추천을 동의한 모든 사용자 가져오기
        List<User> allUsersAgreed = userQRepository.getAllUsersAgreed();

        // 각 사용자에게 알림을 보내기
        allUsersAgreed.forEach(user -> {

            // 사용자에게 해당하는 알림 메시지 생성
            DiscordWebhookRequestDTO discordWebhookRequestDTO = createWebhookRequest(user);

            // 사용자에게 추천할 식당 목록 가져오기
            List<Restaurant> recommendRestaurants =
                    restaurantQRepository.findRecommendRestaurants(user.getLat(), user.getLon());

            // Embed 목록을 생성
            List<DiscordWebhookRequestDTO.Embed> embeds = createEmbeds(recommendRestaurants);

            // 알림 메시지에 Embeds를 설정
            discordWebhookRequestDTO.setEmbeds(embeds);

            // 알림 전송
            log.info("알림전송 완료. 수신자 : {}", user.getAccount());
            discordWebhookService.send(discordWebhookRequestDTO);
        });
    }

    /**
     * 추천된 식당 목록을 포함하는 Embed 목록을 생성합니다.
     *
     * @param recommendRestaurants 추천된 식당 목록
     * @return 생성된 Embed 목록
     */
    private List<DiscordWebhookRequestDTO.Embed> createEmbeds(List<Restaurant> recommendRestaurants) {
        List<DiscordWebhookRequestDTO.Embed> embeds = new ArrayList<>();
        DiscordWebhookRequestDTO.Embed currentEmbed = null;

        for (int i = 0; i < recommendRestaurants.size(); i++) {
            // 매 5개의 식당마다 새로운 Embed를 생성
            if (i % 5 == 0) {
                currentEmbed =
                        createEmbed(recommendRestaurants.get(i).getSanitationBusinessCondition());
                embeds.add(currentEmbed);
            }

            Restaurant restaurant = recommendRestaurants.get(i);

            // 각 식당에 대한 필드를 생성
            DiscordWebhookRequestDTO.Field field =
                    DiscordWebhookRequestDTO.Field.from(restaurant);

            currentEmbed.getFields().add(field);
        }

        return embeds;
    }

    /**
     * 식당 정보를 포함하는 Embed를 생성합니다.
     *
     * @param restaurantType 식당 종류
     * @return 생성된 Embed
     */
    private DiscordWebhookRequestDTO.Embed createEmbed(String restaurantType) {
        return DiscordWebhookRequestDTO.Embed.from(restaurantType);
    }

    /**
     * Discord 웹훅 알림 메시지를 생성합니다.
     *
     * @param user 수신자 정보
     * @return 생성된 Discord 웹훅 알림 메시지
     */
    private DiscordWebhookRequestDTO createWebhookRequest(User user) {
        return DiscordWebhookRequestDTO.from(user);
    }
}