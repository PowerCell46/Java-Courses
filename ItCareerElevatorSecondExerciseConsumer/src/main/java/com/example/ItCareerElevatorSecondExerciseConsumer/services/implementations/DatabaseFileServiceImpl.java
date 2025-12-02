package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.entities.DatabaseFile;
import com.example.ItCareerElevatorSecondExerciseConsumer.repositories.DatabaseFileRepository;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.DatabaseFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseFileServiceImpl implements DatabaseFileService {

    private final DatabaseFileRepository databaseFileRepository;

    @Override
    public DatabaseFile savePdf(String snowflakeId, byte[] pdfByteArray) {
        Long referenceEntityId = DatabaseFile.convertSnowflakeIdToId(snowflakeId);
        final String CONTENT_TYPE = "application/pdf";

        DatabaseFile dbFile = new DatabaseFile(
                referenceEntityId,
                pdfByteArray,
                CONTENT_TYPE
        );

        log.info("Saving PDF document, generated for entity with id: {} to the Database.", referenceEntityId);

        return databaseFileRepository.save(dbFile);
    }
}
