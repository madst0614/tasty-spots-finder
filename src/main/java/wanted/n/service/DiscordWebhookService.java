package wanted.n.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import wanted.n.dto.DiscordWebhookRequestDTO;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscordWebhookService {

    private final RestTemplate restTemplate;

    @Value("${discord.webhookURL}")
    private String webHookUrl;

    /**
     * 주어진 notification 객체로 Recommendations 메시지를 전송합니다.
     *
     * @param webhookRequestDTO 메세지 객체
     */
    public void send(DiscordWebhookRequestDTO webhookRequestDTO) {
        try {
            HttpEntity<DiscordWebhookRequestDTO> messageEntity =
                    createMessageEntity(webhookRequestDTO);
            ResponseEntity<String> response = sendMessage(messageEntity);

            handleResponse(response);
        } catch (RestClientException e) {
            log.error("RestTemplate 호출 중 에러 발생: " + e.getMessage(), e);
            // 예외 처리 로직 추가
        } catch (Exception e) {
            log.error("에러 발생: " + e.getMessage(), e);
        }
    }

    /**
     * 주어진 NotificationDTO 객체를 HTTP 요청 엔터티로 만듭니다.
     *
     * @param discordWebhookRequestDTO NotificationDTO 객체
     * @return HTTP 요청 엔터티
     */
    private HttpEntity<DiscordWebhookRequestDTO> createMessageEntity(
            DiscordWebhookRequestDTO discordWebhookRequestDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return new HttpEntity<>(discordWebhookRequestDTO, httpHeaders);
    }

    /**
     * 주어진 HTTP 요청 엔터티로 Recommendations 메시지를 전송하고 응답을 반환합니다.
     *
     * @param messageEntity HTTP 요청 엔터티
     * @return Recommendations 메시지 전송에 대한 HTTP 응답
     */
    private ResponseEntity<String> sendMessage(HttpEntity<DiscordWebhookRequestDTO> messageEntity) {
        return restTemplate.exchange(webHookUrl, HttpMethod.POST, messageEntity, String.class);
    }

    /**
     * Recommendations 메시지 전송 이후의 HTTP 응답에 대한 처리를 수행합니다.
     *
     * @param response Recommendations 메시지 전송 HTTP 응답
     */
    private void handleResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() != NO_CONTENT) {
            log.error("메시지 전송 이후 에러 발생");
        }
    }

}
