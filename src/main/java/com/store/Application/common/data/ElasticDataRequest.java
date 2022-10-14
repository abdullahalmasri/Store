package com.store.Application.common.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class ElasticDataRequest {

    private Optional<List<String>> names;

    private Optional<List<Double>> price;

    public ElasticDataRequest() {
        super();
    }

    public ElasticDataRequest(Optional<List<String>> names, Optional<List<Double>> price) {
        this.names = names;
        this.price = price;
    }

    public ElasticDataRequest(ElasticDataRequest elasticDataRequest){
        super();
        this.names = elasticDataRequest.getNames();
        this.price = elasticDataRequest.getPrice();
    }


    @Override
    public String toString() {
        return "ElasticDataRequest{" +
                "names=" + names +
                ", price=" + price +
                '}';
    }
}
