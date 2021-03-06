package com.listing.listingservice.repository;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.listing.listingservice.domain.Listing;
import com.listing.listingservice.exception.ListingNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ListingRepository {

    private final Cluster couchbaseCluster;
    private final Collection listingCollection;

    public ListingRepository(Cluster couchbaseCluster, Collection listingCollection) {
        this.couchbaseCluster = couchbaseCluster;
        this.listingCollection = listingCollection;
    }

    public void insert(Listing listing) {
        listingCollection.insert(listing.getId(), listing);
    }

    public void update(Listing listing) {
        listingCollection.replace(listing.getId(), listing);
    }

    public Listing findById(String id){
        try {

            GetResult getResult = listingCollection.get(id);
            Listing listing = getResult.contentAs(Listing.class);
            return Optional.of(listing).get();

        } catch (DocumentNotFoundException exception) {
            throw new ListingNotFoundException(id);
        }
    }

    public List<Listing> getByIdList(List<String> ids) {

        List<String> idList = ids.stream().map(id -> "'" + id + "'").collect(Collectors.toList());
        String statement = " SELECT sku,id,merchantId,price,stock FROM `listing` WHERE id IN " + idList;
        QueryResult query = couchbaseCluster.query(statement);
        return query.rowsAs(Listing.class);
    }
}
