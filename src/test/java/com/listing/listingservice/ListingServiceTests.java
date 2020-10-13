package com.listing.listingservice;

import com.listing.listingservice.contract.request.CreateListingRequest;
import com.listing.listingservice.domain.Listing;
import com.listing.listingservice.exception.ListingNotFoundException;
import com.listing.listingservice.repository.ListingRepository;
import com.listing.listingservice.service.ListingService;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ListingServiceTests {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    @Test
    public void getListingsByIdList_should_throw_ListingNotFoundException_when_there_is_no_listing() {
        //Arrange
        String ids = "listingid,listingid1";
        List<String> idList = Arrays.asList(ids.split(",").clone());
        when(listingRepository.getByIdList(idList)).thenThrow(new ListingNotFoundException("burak"));
        //Act
        Throwable throwable = catchThrowable(() -> listingService.getListingsByIdList(ids));
        // Assert
        assertThat(throwable).isInstanceOf(ListingNotFoundException.class).hasMessage("burak");
    }

    @Test
    public void getListingsByIdList_should_throw_IllegalArgumentException_when_ids_empty() {
        //Arrange
        String ids = "";
        //Act
        Throwable throwable = catchThrowable(() -> listingService.getListingsByIdList(ids));

        // Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getListingsByIdList_should_throw_IllegalArgumentException_when_ids_null() {
        //Arrange
        String ids = null;
        //Act
        Throwable throwable = catchThrowable(() -> listingService.getListingsByIdList(ids));

        // Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }


    @Test // todo : paramatirez olacak
    public void create_should_throw_IllegalArgumentException_when_getMerchantId_is_empty() {

        //Arrange
        CreateListingRequest createListingRequest = new CreateListingRequest();
        //Act
        Throwable throwable = catchThrowable(() -> listingService.create(createListingRequest));

        // Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void create_should_called_when_createListingRequest_is_valid() {
        //Arrange
        CreateListingRequest createListingRequest = new CreateListingRequest();
        createListingRequest.setMerchantId("merchantId");
        createListingRequest.setSku("sku");
        createListingRequest.setStock(11);
        createListingRequest.setPrice(99.99);
        //Act
        listingService.create(createListingRequest);
        //Assert
        verify(listingRepository, times(1)).insert(any(Listing.class));
    }

    @Test // todo : any parameter gonderilmesi gerek
    public void decreaseStock_should_throw_ListingNotFoundException_when_listing_not_found_by_id() {
        //Arrange
        when(listingRepository.findById(any())).thenThrow(new ListingNotFoundException("listingid"));
        //Act
        Throwable throwable = catchThrowable(() -> listingService.decreaseStock("id", 3));
        // Assert
        assertThat(throwable).isInstanceOf(ListingNotFoundException.class);
    }



    @Test
    public void decreaseStock_should_called_update_method_when_parameters_is_valid() {
        // Arrange
        Listing listing = new Listing();
        listing.setStock(6);
        int quantity = 1;
        when(listingRepository.findById(listing.getId())).thenReturn(listing);
        // Act
        listingService.decreaseStock(listing.getId(), quantity);
        //Assert
        verify(listingRepository, times(1)).update(listing);
    }

}
