package com.listing.listingservice.service;

import com.listing.listingservice.contract.request.CreateListingRequest;
import com.listing.listingservice.domain.Listing;
import com.listing.listingservice.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getListingsByIdList(List<String> ids) {
        return listingRepository.getByIdList(ids);
    }

    public String create(CreateListingRequest createListingRequest) {

        ensureCreateListingRequestValid(createListingRequest);

        Listing listing = new Listing();
        listing.setMerchantId(createListingRequest.getMerchantId());
        listing.setPrice(createListingRequest.getPrice());
        listing.setSku(createListingRequest.getSku());
        listing.setStock(createListingRequest.getStock());

        listingRepository.insert(listing);

        return listing.getId();

    }

    public void decreaseStock(String id, int quantity) {
        Listing listing = listingRepository.findById(id);

        if (listing.getStock() < quantity && quantity <= 0) {
            // todo : exception
        }
        listing.decreaseStock(quantity);
        listingRepository.update(listing);

    }

    private void ensureCreateListingRequestValid(CreateListingRequest createListingRequest) {
        if (createListingRequest.getMerchantId() == null || createListingRequest.getMerchantId().isEmpty()) {
            throw new IllegalArgumentException("MerchantId cannot be null or empty");
        }
        if (createListingRequest.getSku() == null || createListingRequest.getSku().isEmpty()) {
            throw new IllegalArgumentException("Sku cannot be null or empty");
        }
        if (createListingRequest.getPrice() <= 0) {
            throw new IllegalArgumentException("Price cannot be lass than or equal zero");
        }
        if (createListingRequest.getStock() <= 0) {
            throw new IllegalArgumentException("Stock cannot be lass than or equal zero");
        }
    }

}
