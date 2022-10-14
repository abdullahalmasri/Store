package com.store.Application.common.data;

import com.store.Application.dao.model.ModelEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
// set create index true only first time then turn it false
@Document(indexName = ModelEntity.ELASTICSEARCH_NAME, createIndex = true)
public class ElasticData implements HasIndexId {

    @Id
    private UUID id;

    @Field(type = FieldType.Keyword)
    private String itemName;

    @Field(type = FieldType.Double)
    private BigDecimal price;


}
