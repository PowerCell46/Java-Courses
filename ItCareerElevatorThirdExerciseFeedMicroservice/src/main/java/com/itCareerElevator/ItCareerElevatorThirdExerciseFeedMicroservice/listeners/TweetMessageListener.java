package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.listeners;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweetMessageListener {

    private final UserFeedService userFeedService;

    @KafkaListener(
            topics = "tweet",
            groupId = "tweets-consumer",
            containerFactory = "tweetKafkaContainerFactory"
    )
    public void handleTweetMessage(String snowflakeId) {
        log.info("--- Handling message in 'tweet' topic.");

        if (snowflakeId == null) {
            log.error("Null tweet snowflakeId.");
            return;
        }

        log.info("Received tweet snowflakeId from Kafka: {}.", snowflakeId);
        userFeedService.fanOutTweetToFollowers(snowflakeId);
    }
}
