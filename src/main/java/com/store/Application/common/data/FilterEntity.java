package com.store.Application.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterEntity {
    private String FilterName;
    private List<ContentFilterEntity> contentFilterEntities;
}
