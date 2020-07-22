package sls.meetup.itau.kafka.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sls.meetup.itau.kafka.producer.entity.Messageria;
import sls.meetup.itau.kafka.producer.entity.Signature;

@Service
@Slf4j
public class SignatureService {

    @Autowired
    private Messageria messageria;

    public void sendSignature(Signature signature) {
        log.info("Signature {}", signature);
        messageria.send(signature);
    }
}
