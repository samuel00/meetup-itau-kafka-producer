package sls.meetup.itau.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import sls.meetup.itau.kafka.producer.entity.SignatureDTO;
import sls.meetup.itau.kafka.producer.entity.SignatureVO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignatureImplVO implements SignatureVO {

	private HttpStatus httpStatus;

	private String httpText;

	private List<SignatureDTO> list;
}
