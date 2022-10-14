package com.store.Application.dao.model.Repository.elk;

import com.store.Application.common.data.*;
import com.store.Application.dao.model.ModelEntity;
import com.store.Application.service.KafkaConsumerService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ElasticSearchComponent implements ElasticSearchRepository {
    private final KafkaConsumerService kafkaConsumerService;
    @Qualifier("elasticsearch")
    @Autowired
    private ElasticsearchOperations elastic;

    public ElasticSearchComponent(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }


    @Override
    public <S extends ElasticData> S save(S entity) {
        if (existsById(entity.getId()))
            return null;
        kafkaConsumerService.receive(entity);
        return elastic.save(entity, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));
    }

    //TODO
    @Override
    public <S extends ElasticData> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ElasticData> findById(UUID uuid) {
        Query query = getQueryById(uuid.toString());
        SearchHit<ElasticData> searchHit = elastic.searchOne(query, ElasticData.class, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));
        if (searchHit == null)
            return Optional.empty();
        return Optional.of(searchHit.getContent());
    }

    @Override
    public boolean existsById(UUID uuid) {
        return elastic.exists(uuid.toString(), IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));
    }

    @Override
    public Iterable<ElasticData> findAll() {
        QueryBuilder queryBuilder = getSearchForAll();
        // return it native for using builder
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        SearchHits<ElasticData> searchHits = elastic.search(nativeSearchQuery, ElasticData.class, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public Iterable<ElasticData> findAllById(Iterable<UUID> uuids) {
        List<ElasticData> elasticDataList = new ArrayList<>();
        for (UUID id : uuids) {
            Query query = getQueryById(id.toString());
            SearchHit<ElasticData> searchHit = elastic.searchOne(query, ElasticData.class, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));

            if (searchHit.getContent() != null)
                elasticDataList.add(searchHit.getContent());
        }
        return elasticDataList;
    }

    //TODO
    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        if (existsById(uuid))
            elastic.delete(uuid.toString(), IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));

    }

    @Override
    public void delete(ElasticData entity) {
        if (existsById(entity.getId()))
            elastic.delete(entity);

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for (UUID uuid : uuids) {
            if (existsById(uuid))
                elastic.delete(uuid, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME));
        }
    }

    @Override
    public void deleteAll(Iterable<? extends ElasticData> entities) {
        for (ElasticData entity : entities) {
            if (existsById(entity.getId()))
                elastic.delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        findAll().forEach(elasticData -> elastic.delete(elasticData, IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME)));
    }


    private Query getQueryById(String id) {
        return new NativeSearchQueryBuilder().withQuery(
                new BoolQueryBuilder().must(QueryBuilders.matchQuery("id", id))
        ).build();
    }

    private QueryBuilder getSearchForAll() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        QueryBuilder queryBuilder = boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        return queryBuilder;
    }


    protected Query getSearchQueryForBuckets(List<FilteringListEntities> filteringListModels) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder = getNativeSearchQueryBuilder(nativeSearchQueryBuilder);

        // use bool builder to create must and Query that matches documents matching boolean combinations of other queries.
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        filteringListModels.forEach(filteringListModel -> {

            // Using Match builder of elasticsearch
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(filteringListModel.getField(), filteringListModel.getText().toString());
//
            // here we need to call must  be aware of using should or filter
            boolQueryBuilder.must(matchQueryBuilder);

        });

        // combine the Aggregation and Query
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);


        if (filteringListModels.size() == 0) {
            nativeSearchQueryBuilder.withQuery(
                    new BoolQueryBuilder()
                            .must(QueryBuilders.matchAllQuery()));
        }
        return nativeSearchQueryBuilder.build();
    }

    /**
     * the Query get Aggregations of name and price
     */
    @NotNull
    private NativeSearchQueryBuilder getNativeSearchQueryBuilder(NativeSearchQueryBuilder searchQueryBuilder) {
        // create Query + Aggregation
        NativeSearchQueryBuilder nativeSearchQueryBuilder = searchQueryBuilder;

        // Using TermsAggregationBuilder to get the Entity
        TermsAggregationBuilder nameAggregationBuilder =
                AggregationBuilders.terms("name").
                        field("itemName");

        // in case want to get count
//        TermsAggregationBuilder priceAggregationBuilder =
//                AggregationBuilders.terms("price")
//                        .field("price");

        // in case of summation
        SumAggregationBuilder sumAggregationBuilder =
                AggregationBuilders.sum("price")
                        .field("price");


        // build the Term Aggregation by using add
        nativeSearchQueryBuilder.addAggregation(nameAggregationBuilder);
//        nativeSearchQueryBuilder.addAggregation(priceAggregationBuilder);
        nativeSearchQueryBuilder.addAggregation(sumAggregationBuilder);
        return nativeSearchQueryBuilder;
    }


    public List<FilterEntity> getAllBucket(UserId userId, List<FilteringListEntities> filteringListEntitiesList) {
        Query query = getSearchQueryForBuckets(
                filteringListEntitiesList);

        SearchHits<ElasticData> searchResults = elastic.search(
                query
                , ElasticData.class
                , IndexCoordinates.of(ModelEntity.ELASTICSEARCH_NAME)
        );

        var aggregationss = searchResults.getAggregations();
        Aggregations aggregations = (Aggregations) aggregationss.aggregations();
        List<FilterEntity> filterEntities = new ArrayList<>();
        if (aggregations != null) {
            aggregations.forEach(aggregation -> {
                if (aggregations.get(aggregation.getName()) instanceof ParsedTerms) {
                    ParsedTerms aggreg = aggregations.get(aggregation.getName());
                    List<? extends Terms.Bucket> buckets = aggreg.getBuckets();
                    List<ContentFilterEntity> filters = buckets
                            .stream()
                            .map(bucket -> ContentFilterEntity
                                    .builder()
                                    .name(bucket.getKeyAsString())
                                    .count(bucket.getDocCount())
                                    .build())
                            .collect(Collectors.toList());

                    filterEntities.add(FilterEntity.builder()
                            .FilterName(ModelEntity.Term.concat(" " + aggregation.getName()))
                            .contentFilterEntities(filters)
                            .build());
                } else if (aggregations.get(aggregation.getName()) instanceof ParsedSum) {
                    ParsedSum parsedSum = aggregations.get(aggregation.getName());
                    ContentFilterEntity contentFilterEntity =
                            ContentFilterEntity.builder()
                                    .name(parsedSum.getName())
                                    .count((long) parsedSum.getValue())
                                    .build();
                    List<ContentFilterEntity> filters = new ArrayList<>();
                    filters.add(contentFilterEntity);
                    filterEntities.add(FilterEntity.builder()
                            .FilterName(ModelEntity.SUM.concat(" " + aggregation.getName()))
                            .contentFilterEntities(filters)
                            .build());
                }
            });
        }

        return filterEntities;
    }

    //TODO
    @Override
    public Page<ElasticData> searchSimilar(ElasticData entity, String[] fields, Pageable pageable) {
        return null;
    }

    //TODO
    @Override
    public Iterable<ElasticData> findAll(Sort sort) {
        return null;
    }

    //TODO
    @Override
    public Page<ElasticData> findAll(Pageable pageable) {
        return null;
    }
}