package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.TweetProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TweetProducerServiceImpl implements TweetProducerService {

    @Value("${app.kafka.topics.tweet}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, String> tweetKafkaTemplate;

    @Override
    public void send(String tweetSnowflakeId) {
        String key = String.format("tweet-%s", tweetSnowflakeId);

        tweetKafkaTemplate
                .send(TOPIC_NAME, key, tweetSnowflakeId)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send tweet with id {} to topic {}.", key, TOPIC_NAME, ex);

                    } else {
                        log.info("Sent tweet with id {} to topic {} partition {} offset {}.",
                                key,
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });
    }
}
