package sls.meetup.itau.kafka.producer.api.validate;

import sls.meetup.itau.kafka.producer.entity.StatusSubscription;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CustomerTypeSubSetValidator implements ConstraintValidator<CustomerTypeSubset, StatusSubscription> {
    private StatusSubscription[] subset;

    @Override
    public void initialize(CustomerTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(StatusSubscription value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
