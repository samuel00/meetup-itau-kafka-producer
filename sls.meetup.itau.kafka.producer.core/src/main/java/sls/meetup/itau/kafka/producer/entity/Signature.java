package sls.meetup.itau.kafka.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Signature implements  SignatureDTO {

    private String notificationType;

    private String subscription;
}
