package com.jiguang.test.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetMealEnum implements IEnum<Object,Object> {
    SP( "06","one"),
    IHC( "07","two");
    @JsonValue
    private String type;
    private String value;
    public static class Converter extends EnumConverter {
        public Converter() {
            super(values());
        }
    }

}

