package com.listing.listingservice;

import com.listing.listingservice.exception.ListingNotFoundException;
import com.listing.listingservice.repository.ListingRepository;
import com.listing.listingservice.service.ListingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListingServiceTests {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    @Test
    public void getListingsByIdList_should_throw_ListingNotFoundException_when_there_is_no_listing() {
        List<String> ids = new ArrayList<>();

        //Arrange
        when(listingRepository.getByIdList(ids)).thenThrow(new ListingNotFoundException("emrah"));

        //Act
        Throwable throwable = catchThrowable(() -> listingService.getListingsByIdList(ids));

        // Assert
        assertThat(throwable).isInstanceOf(ListingNotFoundException.class).hasMessage("emrah");

    }


}
