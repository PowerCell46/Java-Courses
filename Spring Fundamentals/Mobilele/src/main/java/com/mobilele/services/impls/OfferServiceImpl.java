package com.mobilele.services.impls;

import com.mobilele.models.DTOs.AddOfferDTO;
import com.mobilele.models.entities.Offer;
import com.mobilele.repositories.OfferRepository;
import com.mobilele.services.interfaces.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public Offer findById(Long id) {
        return offerRepository.findById(id).get();
    }

    @Override
    public void createOffer(AddOfferDTO addOfferDTO) {
        offerRepository.saveAndFlush(mapAddOfferDTOToOffer(addOfferDTO));
    }

      private Offer mapAddOfferDTOToOffer(AddOfferDTO addOfferDTO) {
        Offer offer = new Offer();
        offer.setEngine(addOfferDTO.getEngineType());
        offer.setTransmission(addOfferDTO.getTransmissionType());
        offer.setMileage(addOfferDTO.getMileage());
        offer.setPrice(addOfferDTO.getPrice());
        offer.setYear(addOfferDTO.getManufacturingYear());
        offer.setDescription(addOfferDTO.getDescription());
//        offer.setModel(addOfferDTO.getModel());

        return offer;
    }
}
