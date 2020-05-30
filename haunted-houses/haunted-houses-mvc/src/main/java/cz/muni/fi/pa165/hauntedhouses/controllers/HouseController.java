package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * @author Petr Vitovsky
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    private final static Logger log = LoggerFactory.getLogger(HouseController.class);

    private HouseFacade houseFacade;

    @Autowired
    public HouseController(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    public void setHouseFacade(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("houses", houseFacade.getAllHouses());
        return "house/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("house", houseFacade.getHouseById(id));
        return "house/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        log.debug("new()");
        model.addAttribute("houseCreate", new HouseCreateDTO());
        return "house/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("houseCreate") HouseCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(houseCreate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "house/new";
        }

        try {
            Long id = houseFacade.createHouse(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "House " + id + " was created");
            return "redirect:" + uriBuilder.path("/house/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (DataAccessException ex) {
            log.error("House " + formBean.getName() + " cannot be updated!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            model.addAttribute("address_error", true);
            return "house/new";
        } catch (Exception ex) {
            log.error("House " + formBean.getName() + " cannot be created!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            return "house/new";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateSubmit(@Valid @ModelAttribute("houseUpdate") HouseDTO formBean, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("update(houseUpdate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "house/update";
        }


        try {
            houseFacade.updateHouse(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "House " + formBean.getName() + " was updated");
            Long id = formBean.getId();
            return "redirect:" + uriBuilder.path("/house/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (DataAccessException ex) {
            log.error("House " + formBean.getName() + " cannot be updated!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            model.addAttribute("address_error", true);
            return "house/update";
        } catch (Exception ex) {
            log.error("House " + formBean.getName() + " cannot be updated!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            return "house/update";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model) {
        log.debug("update({})", id);

        model.addAttribute("houseUpdate", houseFacade.getHouseById(id));
        return "house/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        HouseDTO house = houseFacade.getHouseById(id);
        log.debug("delete({})", id);
        try {
            houseFacade.deleteHouse(id);
            redirectAttributes.addFlashAttribute("alert_success", "House \"" + house.getName() + "\" was deleted.");
        } catch (Exception ex) {
            log.error("House " + id + " cannot be deleted!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "House \"" + house.getName() + "\" cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/house/list").toUriString();
    }
}
