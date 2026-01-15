package com.github.dimitryivaniuta.dealflow.domain.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PermissionCodeConverter implements AttributeConverter<PermissionCode, String> {

    @Override
    public String convertToDatabaseColumn(PermissionCode attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PermissionCode convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PermissionCode.valueOf(dbData);
    }
}
