package com.store.Application.dao.model.Repository.elk;

import com.store.Application.common.data.ElasticData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

@Qualifier("elasticsearch")
public interface ElasticSearchRepository extends CrudRepository<ElasticData, UUID>, ElasticsearchRepository<ElasticData, UUID> {

}
