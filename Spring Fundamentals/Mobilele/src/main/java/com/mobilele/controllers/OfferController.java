package com.mobilele.controllers;

import com.mobilele.models.DTOs.AddOfferDTO;
import com.mobilele.models.entities.Offer;
import com.mobilele.models.enums.Engines;
import com.mobilele.models.enums.Transmissions;
import com.mobilele.services.interfaces.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/add")
    public String addOfferPage(Model model) {
        if (!model.containsAttribute("addOfferDTO")) {
            model.addAttribute("addOfferDTO", new AddOfferDTO());
        }
        model.addAttribute("engineTypes", Engines.values());
        model.addAttribute("transmissionTypes", Transmissions.values());

        return "offer-add";
    }

    @PostMapping("/add")
    public ModelAndView addOfferHandler(@Valid AddOfferDTO addOfferDTO, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
        }

        offerService.createOffer(addOfferDTO);

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView offerDetailsPage(ModelAndView modelAndView, @PathVariable Long id) {
        Offer offer = offerService.findById(id);
        modelAndView.addObject("offer", offer);
        modelAndView.setViewName("details");

        return modelAndView;
    }
}
