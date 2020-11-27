package com.jiguang.test.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 枚举转换
 * @author huang
 */
@Component
public class EnumConvertFactory implements ConverterFactory<String, IEnum> {
    @SuppressWarnings("NullableProblems")
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> enumClass) {
        return new StringToIEnum<>(enumClass);
    }

    @SuppressWarnings("all")
    private static class StringToIEnum<T extends IEnum> implements Converter<String, T> {
        private Class<T> enumClass;
        public StringToIEnum(Class<T> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            return (T) EnumConvertFactory.getEnum(this.enumClass, source);
        }
    }

    public static <T extends IEnum> Object getEnum(Class<T> enumClass, String source) {
        for (T enumObj : enumClass.getEnumConstants()) {
            if (source.equals(String.valueOf(enumObj.getValue()))||source.equals(enumObj.toString())) {
                return enumObj;
            }
        }
        return null;
    }
}
