package com.icommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditMessage {

    private Long userId;

    private String path;

    private Map<String, Object> params;

    public AuditMessage(String path, Map<String, Object> params) {
        this.path = path;
        this.params = params;
    }

}
