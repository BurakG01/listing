package com.listing.listingservice.controller;

import com.listing.listingservice.contract.request.CreateListingRequest;
import com.listing.listingservice.contract.request.DecreaseStockRequest;
import com.listing.listingservice.domain.Listing;
import com.listing.listingservice.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity getByIds(@RequestParam String ids) {

        List<String> idList = Arrays.asList(ids.split(",").clone());
        List<Listing> listings = listingService.getListingsByIdList(idList);
        return ResponseEntity.ok(listings);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreateListingRequest createListingRequest) {
        String id = listingService.create(createListingRequest);

        URI location = URI.create(String.format("/listings/%s", id));
        return ResponseEntity.created(location).build();

    }

    @PatchMapping
    public ResponseEntity decreaseStock(@RequestParam String id, @RequestBody DecreaseStockRequest decreaseStockRequest) {
       this.listingService.decreaseStock(id, decreaseStockRequest.getQuantity());

        return ResponseEntity.accepted().build();

    }


}
