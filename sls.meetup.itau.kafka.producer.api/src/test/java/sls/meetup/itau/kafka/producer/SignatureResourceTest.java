package sls.meetup.itau.kafka.producer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sls.meetup.itau.kafka.producer.common.ResourceBaseTest;
import sls.meetup.itau.kafka.producer.dto.SignatureImplDTO;
import sls.meetup.itau.kafka.producer.entity.StatusSubscription;
import sls.meetup.itau.kafka.producer.util.BuilderRequestUtil;
import sls.meetup.itau.kafka.producer.util.ConverterUtil;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"SUBSCRIPTION_RESTARTED", "SUBSCRIPTION_PURCHASED", "SUBSCRIPTION_CANCELED"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
@ActiveProfiles("test")
public class SignatureResourceTest extends ResourceBaseTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Consumer<String, String> consumer;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    private String getResource() {
        return new BuilderRequestUtil().getSignatureURL().build();
    }

    @Test
    void testSignatureCreated() throws Exception {
        SignatureImplDTO signatureImplDTO = new SignatureImplDTO().builder()
                .notificationType(StatusSubscription.SUBSCRIPTION_PURCHASED)
                .subscription("xyz32xpto").build();
        mockMvc.perform(post(getResource()).contentType(MediaType.APPLICATION_JSON)
                .content(ConverterUtil.ObjectToJsonBytes(signatureImplDTO)))
                .andDo(print())
                .andExpect(status().isCreated()).andExpect(jsonPath("$.httpText", is("201 CREATED"))).andReturn();
        ConsumerRecord<String, String> received = KafkaTestUtils.getSingleRecord(consumer, "SUBSCRIPTION_PURCHASED");
        String expected = "{\"notificationType\":\"SUBSCRIPTION_PURCHASED\",\"subscription\":\"xyz32xpto\"}";
        Assertions.assertNotNull(received.value());
        Assertions.assertEquals(expected, received.value());
    }

    @Test
    void testSignatureUpdated() throws Exception {
        SignatureImplDTO signatureImplDTO = new SignatureImplDTO().builder()
                .notificationType(StatusSubscription.SUBSCRIPTION_RESTARTED)
                .subscription("xyz32xpto").build();
        mockMvc.perform(put(getResource()).contentType(MediaType.APPLICATION_JSON)
                .content(ConverterUtil.ObjectToJsonBytes(signatureImplDTO)))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.httpText", is("200 OK"))).andReturn();
        ConsumerRecord<String, String> received = KafkaTestUtils.getSingleRecord(consumer, "SUBSCRIPTION_RESTARTED");
        Assertions.assertNotNull(received.value());
    }

    @Test
    void testSignatureBadRequest() throws Exception {
        SignatureImplDTO signatureImplDTO = new SignatureImplDTO().builder().build();
        mockMvc.perform(put(getResource()).contentType(MediaType.APPLICATION_JSON)
                .content(ConverterUtil.ObjectToJsonBytes(signatureImplDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
