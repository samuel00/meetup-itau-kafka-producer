package sls.meetup.itau.kafka.producer.entity;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface Messageria {

    ListenableFuture<SendResult<String, String>> send(SignatureDTO signature);
}
