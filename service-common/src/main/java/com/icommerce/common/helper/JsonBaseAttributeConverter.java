package com.icommerce.common.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

public interface JsonBaseAttributeConverter<X> extends AttributeConverter<X, String> {

    default String convertToDatabaseColumn(X objectValue) {
        if (objectValue == null) {
            return null;
        }

        return JsonHelper.convertToJsonString(objectValue);
    }

    default X convertToEntityAttribute(String dataValue) {
        if (StringUtils.isEmpty(dataValue)) {
            return null;
        }
        return JsonHelper.readFromJson(dataValue, getObjectTypeRef());
    }

    TypeReference<X> getObjectTypeRef();

}
