package com.icommerce.productservice.service.audit;

import com.icommerce.productservice.dto.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("AuditService")
public class AuditServiceImpl implements AuditService {

    private Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, AuditMessage> auditKafkaTemplate;

    @Override
    public void sendAuditMessage(String path, Map<String, Object> params) {
        try {
            auditKafkaTemplate.send(auditKafkaTemplate.getDefaultTopic(), new AuditMessage(path, params));
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
    }

}
