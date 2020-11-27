package com.jiguang.test.enums;

import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;

public class EnumConverter implements AttributeConverter<IEnum,String> {
    final IEnum[] values;

    public EnumConverter(IEnum[] values) {
        this.values = values;
    }

    @Override
    public String convertToDatabaseColumn(IEnum gender) {
        if ( gender == null ) {
            return null;
        }

        return gender.getType().toString();
    }

    @Override
    public IEnum convertToEntityAttribute(String value) {
        if ( value == null ) {
            return null;
        }
        for (IEnum iEnum : values) {
            if (iEnum.getType().equals(value)){
                return iEnum;
            }
        }
        return null;
    }

}
