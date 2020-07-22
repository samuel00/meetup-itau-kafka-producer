package sls.meetup.itau.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sls.meetup.itau.kafka.producer.api.validate.CustomerTypeSubset;
import sls.meetup.itau.kafka.producer.entity.SignatureDTO;
import sls.meetup.itau.kafka.producer.entity.StatusSubscription;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignatureImplDTO implements SignatureDTO {

    @NotNull
    @CustomerTypeSubset(anyOf = {StatusSubscription.SUBSCRIPTION_PURCHASED, StatusSubscription.SUBSCRIPTION_RESTARTED,
            StatusSubscription.SUBSCRIPTION_CANCELED})
    private StatusSubscription notificationType;

    @NotNull
    private String subscription;

    public String getNotificationType(){
        return Optional.ofNullable(this.notificationType).isPresent() ? this.notificationType.name() : null;
    }
}
