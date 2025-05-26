package com.sneakerspot.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceOffer {
    private Buyer buyer;
    private Sneaker sneaker;
    private BigDecimal offeredPrice;
    private LocalDateTime offerDate;
    private OfferStatus status;


    public PriceOffer() {}
    public PriceOffer(Buyer buyer, Sneaker sneaker, BigDecimal offeredPrice) {
        this.buyer = buyer;
        this.sneaker = sneaker;
        this.offeredPrice = offeredPrice;
        this.offerDate = LocalDateTime.now();
        this.status = OfferStatus.PENDING;
    }

    // Getters
    public Buyer getBuyer() {
        return buyer;
    }

    public Sneaker getSneaker() {
        return sneaker;
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

    // Setters
    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}