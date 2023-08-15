package shopping.common.converter;

import shopping.common.domain.Quantity;

import javax.persistence.AttributeConverter;

public class QuantityConverter implements AttributeConverter<Quantity, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Quantity quantity) {
        return quantity.getValue();
    }

    @Override
    public Quantity convertToEntityAttribute(Integer value) {
        return Quantity.valueOf(value);
    }
}
