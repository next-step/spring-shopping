package shopping.common.converter;

import shopping.common.domain.Rate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RateConverter implements AttributeConverter<Rate, Double> {

    @Override
    public Double convertToDatabaseColumn(Rate rate) {
        return rate.getValue();
    }

    @Override
    public Rate convertToEntityAttribute(Double value) {
        return Rate.valueOf(value);
    }
}
