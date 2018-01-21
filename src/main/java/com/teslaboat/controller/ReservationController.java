package com.teslaboat.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.teslaboat.model.ChargeRequest;
import com.teslaboat.model.Currency;
import com.teslaboat.model.ReservationStatus;
import com.teslaboat.model.db.Reservation;
import com.teslaboat.services.*;
import com.teslaboat.viewModels.reservation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Controller
public class ReservationController {

    @Autowired
    private BoatManager boatManager;

    @Autowired
    private EquipmentManager equipmentManager;

    @Autowired
    private ReservationManager reservationManager;

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private StripeService paymentsService;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    EmailService emailService;

    @Value("${stripe.public_key}")
    private String stripePublicKey;

    @RequestMapping(value = "/",  method = RequestMethod.GET )
    public String sear(Model model) {
        BoatSearchForm boatSearchForm = new BoatSearchForm();
        boatSearchForm.setDestinations(locationManager.getAllLocations());
        model.addAttribute("boatSearchForm", boatSearchForm);

        return "reservation-index";
    }

    @RequestMapping(value = "/",  method = RequestMethod.POST)
    public String postIndex(@RequestParam(value="destination") Long destination,
                         @RequestParam(value="date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                         Model model) {

        List<BoatTypeModel> boatTypeModels = boatManager.getAvailableBoatTypes(destination, date).stream()
                .map(b -> new BoatTypeModel(b.getId(), b.getName(), b.getDescription(), b.getSeats(),
                        boatManager.getBoatPrice(b.getId(),date)))
                .collect(Collectors.toList());

        BoatSearchForm boatSearchForm = new BoatSearchForm(destination, date, locationManager.getAllLocations(), boatTypeModels);

        model.addAttribute("boatSearchForm", boatSearchForm);

        return "reservation-index";
    }

    @RequestMapping(value = "/rent",  method = RequestMethod.POST)
    public String rent(
            @RequestParam("boatTypeId") Long boatTypeId,
            @RequestParam("locationId") Long locationId,
            @RequestParam("reservationDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date reservationDate) {

        Reservation reservation = reservationManager.create(boatTypeId, locationId, reservationDate);
        return "redirect:/configure/" + reservation.getCode();
    }

    @RequestMapping(value = "/configure/{code}",  method = RequestMethod.GET)
    public String configure(@PathVariable("code") String code, Model model) {
        Reservation reservation = reservationManager.getByCode(code);

        if (reservation.getStatus() != ReservationStatus.CREATED) {
            model.addAttribute("error", "");
            return "reservation-failure";
        }

        ConfigurationForm configurationForm = new ConfigurationForm(reservation.getCode(), reservation.getBoatPrice());
        List<EquipmentModel> equipmentModels = equipmentManager.getAvailableEquipments(
                reservation.getLocation().getId(), reservation.getReservationDate())
                .stream()
                .map(e -> e.getEquipmentType())
                .collect(Collectors.groupingBy(Function.identity(), (Collectors.counting())))
                .entrySet()
                .stream()
                .map(e -> new EquipmentModel(e.getKey().getId(), e.getKey().getName(),
                        equipmentManager.getEquipmentPrice(e.getKey().getId(), reservation.getReservationDate()),
                        e.getValue().intValue()))
                .collect(Collectors.toList());

        configurationForm.setEquipment(equipmentModels);
        model.addAttribute("configurationForm", configurationForm);

        return "reservation-configure";
    }

    @RequestMapping(value = "/configure/{code}",  method = RequestMethod.POST)
    public String saveConfiguration(@PathVariable("code") String code, @ModelAttribute("configuration") ConfigurationForm configuration, Model model) {
        Reservation reservation = reservationManager.getByCode(code);
        if (reservation.getStatus() != ReservationStatus.CREATED) {
            model.addAttribute("error", "Reservation expired!");
            return "reservation-failure";
        }
        configuration.getEquipment()
                .stream()
                .filter(e -> e.getQuantity() > 0)
                .forEach(e -> {
                    reservationManager.addEquipmentToReservation(code, e.getId(), e.getQuantity());
                });

        return "redirect:/charge/" + code;
    }

    @RequestMapping(value = "/charge/{code}",  method = RequestMethod.GET)
    public String checkout(@PathVariable("code") String code,  Model model) {
        Reservation reservation = reservationManager.getByCode(code);

        if (reservation.getStatus() != ReservationStatus.CREATED) {
            model.addAttribute("error", "Reservation expired!");
            return "reservation-failure";
        }

        model.addAttribute("amount", (int) (reservation.getTotalPrice() * 100)); // in cents
        model.addAttribute("stripePublicKey", this.stripePublicKey);
        model.addAttribute("currency", Currency.EUR);
        model.addAttribute("reservation", reservation);

        return "reservation-checkout";
    }

    @RequestMapping(value = "/cancel/{code}",  method = RequestMethod.GET)
    public String cancelForm(@PathVariable("code") String code,  Model model) {
        Reservation reservation = reservationManager.getByCode(code);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED &&
                (new Date()).compareTo(reservation.getReservationDate()) > 0) {
            model.addAttribute("error");
            return "reservation-failure";
        }
        return "reservation-cancel";
    }

    @RequestMapping(value = "/cancel/{code}",  method = RequestMethod.POST)
    public String cancel(@PathVariable("code") String code,  Model model) {
        Reservation reservation = reservationManager.getByCode(code);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED &&
                (new Date()).compareTo(reservation.getReservationDate()) > 0) {
            model.addAttribute("error");
            return "reservation-failure";
        }

        reservationManager.markAsCancelled(reservation.getCode());

        return "reservation-cancelled";
    }


    @RequestMapping(value = "/charge/{code}",  method = RequestMethod.POST)
    public String charge(@PathVariable("code") String code, ChargeRequest chargeRequest, Model model) {
        Reservation reservation = reservationManager.updateEmail(code, chargeRequest.getStripeEmail());

        // wrong status - possibly expired
        if (reservation.getStatus() != ReservationStatus.CREATED) {
            model.addAttribute("error", "Reservation expired!");
            return "reservation-failure";
        }


        if (chargeRequest.getAmount() != (int) reservation.getTotalPrice() * 100) {
            model.addAttribute("error", "There was an error processing your payment");
            return "reservation-failure";
        }

        try {
            chargeRequest.setDescription("Tesla boat reservation " + code);
            chargeRequest.setCurrency(Currency.EUR);
            Charge charge = this.paymentsService.charge(chargeRequest.getDescription(), chargeRequest.getAmount(),
                    chargeRequest.getCurrency(), chargeRequest.getStripeToken());
            reservationManager.markAsPaid(reservation.getCode(), charge.getId());

            this.emailService.sendConfirmationEmail(reservation.getEmail(), code);

            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
            return "reservation-success";

        } catch (StripeException ex) {
            model.addAttribute("error", ex.getLocalizedMessage());
            return "reservation-failure";
        } catch (MessagingException ex) {
            model.addAttribute("error", "Unable to send confirmation email!");
            return "reservation-failure";
        }
    }
}
