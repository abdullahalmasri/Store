package com.store.Application.Controller;

import com.store.Application.common.data.ElasticDataRequest;
import com.store.Application.common.data.FilterEntity;
import com.store.Application.common.data.FilteringListEntities;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.elk.ElasticSearchComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchController extends BaseConfigController {

    @Autowired
    private ElasticSearchComponent elasticSearchComponent;


    @RequestMapping(value = "/filters", method = RequestMethod.POST)
    @ResponseBody
    public List<FilterEntity> getFilters(@RequestBody ElasticDataRequest elasticDataRequest){
        log.info("The retrieve data of {} will go in process ",elasticDataRequest);
        UserId userId = getCurrentUser();
        List<FilteringListEntities> filteringListModels = new ArrayList<>();
        if(elasticDataRequest.getNames().isPresent() && elasticDataRequest.getPrice().isPresent()){
            elasticDataRequest.getNames().get().forEach(
                    s -> filteringListModels
                            .add(FilteringListEntities.builder()
                            .field("name")
                            .text(elasticDataRequest.getNames().get())
                            .build())
            );
            elasticDataRequest.getPrice().get().forEach(
                    s -> filteringListModels
                            .add(FilteringListEntities.builder()
                            .field("price")
                            .text(elasticDataRequest.getPrice().get())
                            .build())
            );
        }

        List<FilterEntity> allIndexModels = elasticSearchComponent.getAllBucket(userId,filteringListModels);
        return allIndexModels;

    }
}
