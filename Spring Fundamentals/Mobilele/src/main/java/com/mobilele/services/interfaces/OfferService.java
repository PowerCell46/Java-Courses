package com.mobilele.services.interfaces;

import com.mobilele.models.DTOs.AddOfferDTO;
import com.mobilele.models.entities.Offer;


public interface OfferService {

    Offer findById(Long id);

    void createOffer(AddOfferDTO addOfferDTO);
}
