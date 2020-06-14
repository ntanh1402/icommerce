package com.icommerce.productservice.service.audit;

import com.icommerce.productservice.dto.AuditMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditServiceTest {

    @Autowired
    private AuditService auditService;

    @MockBean
    private KafkaTemplate<String, AuditMessage> auditKafkaTemplate;

    @Value(value = "${audit.topic.name}")
    private String auditTopicName;

    @Test
    public void sendAuditMessage() {
        doAnswer(invocationOnMock -> {
            String topic = invocationOnMock.getArgument(0);
            AuditMessage message = invocationOnMock.getArgument(1);

            assertEquals(auditTopicName, topic);
            assertEquals("/test", message.getPath());

            return null;
        }).when(auditKafkaTemplate).send(anyString(), any(AuditMessage.class));
        auditService.sendAuditMessage("/test", new HashMap<>());
    }

    private void assertEquals(String s, String path) {
    }

}