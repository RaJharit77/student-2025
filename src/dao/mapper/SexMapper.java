package dao.mapper;

import entity.Sex;

import java.util.Arrays;
import java.util.List;

public class SexMapper {
    public Sex mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<Sex> sexList = Arrays.stream(Sex.values()).toList();
        return sexList.stream().filter(
                        sex -> stringValue.equals(sex.toString())
                ).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sex value " + stringValue));
    }
}
