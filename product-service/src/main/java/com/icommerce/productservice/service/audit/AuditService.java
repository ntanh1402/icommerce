package com.icommerce.productservice.service.audit;

import java.util.Map;

public interface AuditService {

    void sendAuditMessage(String path, Map<String, Object> params);

}
