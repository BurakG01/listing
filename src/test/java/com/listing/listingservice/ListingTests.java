package com.listing.listingservice;

import com.listing.listingservice.domain.Listing;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ListingTests {

    @Test
    public void decreaseStock_should_throw_IllegalArgumentException_when_quantity_greater_then_stock() {

        // Arrange
        Listing listing = new Listing();
        listing.setStock(6);
        int greaterQuantity = 7;
        // Act
        Throwable throwable = catchThrowable(() -> listing.decreaseStock(greaterQuantity));
        //Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void decreaseStock_should_throw_IllegalArgumentException_when_quantity_is_less_then_zero() {

        // Arrange
        Listing listing = new Listing();
        listing.setStock(6);
        int lessThenZero = -5;
        // Act
        Throwable throwable = catchThrowable(() -> listing.decreaseStock(lessThenZero));
        //Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void decreaseStock_should_decrease_listing_stock_when_quantity_is_valid() {
        // Arrange
        Listing listing = new Listing();
        listing.setStock(6);
        int greaterQuantity = 1;
        // Act
        listing.decreaseStock(greaterQuantity);
        //Assert
        assertThat(listing.getStock()).isEqualTo(5);
    }


}
