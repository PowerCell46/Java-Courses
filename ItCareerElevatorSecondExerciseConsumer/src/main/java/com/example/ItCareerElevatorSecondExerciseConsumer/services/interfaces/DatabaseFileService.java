package com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseConsumer.entities.DatabaseFile;

public interface DatabaseFileService {

    DatabaseFile savePdf(String snowflakeId, byte[] pdfByteArray);
}
