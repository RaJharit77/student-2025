package dao.mapper;

import entity.Sex;

import static entity.Sex.FEMALE;
import static entity.Sex.MALE;

public class SexMapper {
    public Sex mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        return switch (stringValue) {
            case "MALE" -> MALE;
            case "FEMALE" -> FEMALE;
            default -> throw new IllegalArgumentException("Unknown sex value " + stringValue);
        };
    }
}
