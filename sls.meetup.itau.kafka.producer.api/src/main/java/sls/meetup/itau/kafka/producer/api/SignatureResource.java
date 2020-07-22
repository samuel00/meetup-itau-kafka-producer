package sls.meetup.itau.kafka.producer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sls.meetup.itau.kafka.producer.dto.SignatureImplDTO;
import sls.meetup.itau.kafka.producer.dto.SignatureImplVO;
import sls.meetup.itau.kafka.producer.entity.Signature;
import sls.meetup.itau.kafka.producer.service.SignatureService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/signature")
public class SignatureResource {

    @Autowired
    private SignatureService service;

    @PostMapping
    public ResponseEntity<?> createSignature(@Valid @RequestBody SignatureImplDTO signature,
                                             HttpServletRequest request) {
        this.service.sendSignature(new Signature().builder()
                .notificationType(signature.getNotificationType())
                .subscription(signature.getSubscription())
                .build());
        return new ResponseEntity<>(new SignatureImplVO().builder().httpStatus(HttpStatus.CREATED)
        		.httpText(HttpStatus.CREATED.toString()).list(Arrays.asList(signature)).build(), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<?> updateSignature(@Valid @RequestBody SignatureImplDTO signature,
                                             HttpServletRequest request) {
        this.service.sendSignature(new Signature().builder()
                .notificationType(signature.getNotificationType())
                .subscription(signature.getSubscription())
                .build());
        return new ResponseEntity<>(new SignatureImplVO().builder().httpStatus(HttpStatus.OK)
        		.httpText(HttpStatus.OK.toString()).list(Arrays.asList(signature)).build(), HttpStatus.OK);
    }


}
