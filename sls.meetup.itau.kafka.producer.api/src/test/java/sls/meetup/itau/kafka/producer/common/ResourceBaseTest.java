package sls.meetup.itau.kafka.producer.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

public class ResourceBaseTest {

    protected MockMvc mockMvc;

    protected final static String UTF_8 = "utf8";

    protected final static String TOPIC_NAME = "signature-events";


    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName(UTF_8));
}
