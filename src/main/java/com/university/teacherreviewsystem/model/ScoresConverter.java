// БОЛЬШЕ НЕ ИСПОЛЬЗУЕТСЯ, ТАК КАК ТЕКУЩАЯ ВЕРСИЯ HIBERNATE ПОДДЕРЖИВАЕТ АВТОМАТИЧЕСКУЮ КОНВЕРТАЦИЮ СТРОК В JSON


//package com.university.teacherreviewsystem.model;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Converter
//public class ScoresConverter implements AttributeConverter<Map<UUID, Integer>, String> {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(Map<UUID, Integer> scores) {
//        try {
//            // IMPORTANT: add ::jsonb so that PostgreSQL understands that it is a JSONB, not a string.
//            return objectMapper.writeValueAsString(scores) + "::jsonb";
//        }
//        catch (JsonProcessingException e) {
//            throw new IllegalArgumentException("Error converting Map to JSON", e);
//        }
//    }
//
//    @Override
//    public Map<UUID, Integer> convertToEntityAttribute(String scoresJson) {
//        try {
//            // We remove ::jsonb if it is preserved along with the string.
//            if (scoresJson.endsWith("::jsonb")) {
//                scoresJson = scoresJson.replace("::jsonb", "");
//            }
//            return objectMapper.readValue(scoresJson, objectMapper.getTypeFactory().constructMapType(HashMap.class, UUID.class, Integer.class));
//        }
//        catch (IOException e) {
//            throw new IllegalArgumentException("Error converting JSON to Map", e);
//        }
//    }
//}
