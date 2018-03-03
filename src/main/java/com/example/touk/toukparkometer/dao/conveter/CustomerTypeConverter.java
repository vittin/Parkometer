package com.example.touk.toukparkometer.dao.conveter;


import com.example.touk.toukparkometer.dao.model.helper.CustomerType;

import javax.persistence.AttributeConverter;

public class CustomerTypeConverter implements AttributeConverter<CustomerType, String> {
    @Override
    public String convertToDatabaseColumn(CustomerType attribute) {
        return CustomerType.toShortName(attribute);
    }

    @Override
    public CustomerType convertToEntityAttribute(String dbData) {
        return CustomerType.fromShortName(dbData);
    }
}
