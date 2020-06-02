package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * @author Zoltan Fridrich
 */
@Controller
@RequestMapping("/ability")
public class AbilityController {

    private final static Logger log = LoggerFactory.getLogger(AbilityController.class);

    private AbilityFacade abilityFacade;

    @Autowired
    public AbilityController(AbilityFacade abilityFacade) {
        this.abilityFacade = abilityFacade;
    }

    public void setAbilityFacade(AbilityFacade abilityFacade) {
        this.abilityFacade = abilityFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("abilities", abilityFacade.getAllAbilities());
        return "ability/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("ability", abilityFacade.getAbilityById(id));
        return "ability/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        log.debug("new()");
        model.addAttribute("abilityCreate", new AbilityCreateDTO());
        return "ability/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("abilityCreate") AbilityCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(abilityCreate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "ability/new";
        }

        try {
            Long id = abilityFacade.createAbility(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Ability " + formBean.getName() + " was created");
            return "redirect:" + uriBuilder.path("/ability/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (DataAccessException ex) {
            log.error("Ability " + formBean.getName() + " cannot be created!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            model.addAttribute("name_error", true);
            return "ability/new";
        } catch (Exception ex) {
            log.error("Ability " + formBean.getName() + " cannot be created!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            return "ability/new";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateSubmit(@Valid @ModelAttribute("abilityUpdate") AbilityDTO formBean, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("update(abilityUpdate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "ability/update";
        }

        try {
            abilityFacade.updateAbility(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Ability " + formBean.getName() + " was updated");
            Long id = formBean.getId();
            return "redirect:" + uriBuilder.path("/ability/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (DataAccessException ex) {
            log.error("Ability " + formBean.getName() + " cannot be updated!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            model.addAttribute("name_error", true);
            return "ability/update";
        } catch (Exception ex) {
            log.error("Ability " + formBean.getName() + " cannot be updated!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            return "ability/update";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model) {
        log.debug("update({})", id);

        model.addAttribute("abilityUpdate", abilityFacade.getAbilityById(id));
        return "ability/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        AbilityDTO ability = abilityFacade.getAbilityById(id);
        log.debug("delete({})", id);
        try {
            abilityFacade.deleteAbility(id);
            redirectAttributes.addFlashAttribute("alert_success", "Ability \"" + ability.getName() + "\" was deleted.");
        } catch (Exception ex) {
            log.error("Ability " + id + " cannot be deleted!");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Ability \"" + ability.getName() + "\" cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/ability/list").toUriString();
    }
}
