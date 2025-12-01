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
        DatabaseFile dbFile = new DatabaseFile(
                DatabaseFile.convertSnowflakeIdToId(snowflakeId),
                pdfByteArray,
                "application/pdf"
        );

        log.info("Saving PDF document, generated for: {}.", snowflakeId);

        return databaseFileRepository.save(dbFile);
    }
}
