package com.sneakerspot.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clasa pentru ofertele de preț făcute de cumpărători
 */


public class PriceOffer {
    private Buyer buyer;
    private Product product;
    private BigDecimal offeredPrice;
    private LocalDateTime offerDate;
    private OfferStatus status;

    //Constructor
    public PriceOffer(Buyer buyer, Product product, BigDecimal offeredPrice) {
        this.buyer = buyer;
        this.product = product;
        this.offeredPrice = offeredPrice;
        this.offerDate = LocalDateTime.now();
        this.status = OfferStatus.PENDING;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public BigDecimal getOfferedPrice() {
        return offeredPrice;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public OfferStatus getStatus() {
        return status;
    }

    // Setter pentru status
    public void setStatus(OfferStatus offerStatus) {
        this.status = offerStatus;
    }
}