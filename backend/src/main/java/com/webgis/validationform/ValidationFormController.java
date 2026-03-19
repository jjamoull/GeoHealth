package com.webgis.validationform;

import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.User;
import com.webgis.user.UserService;
import com.webgis.validationform.dto.ResponseValidationFormDto;
import com.webgis.validationform.dto.SaveValidationFormDto;
import com.webgis.validationform.dto.UpdateValidationFormDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/validationForm")
public class ValidationFormController {

    private final ValidationFormService validationFormService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserService userService;
    private final FinalMapService finalMapService;

    public ValidationFormController(ValidationFormService validationFormService,
                                    JwtService jwtService,
                                    CookieService cookieService,
                                    UserService userService,
                                    FinalMapService finalMapService) {
        this.validationFormService = validationFormService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.userService = userService;
        this.finalMapService=finalMapService;
    }

    /**
     * Save a new form
     *
     * @param saveFormDto information needed to save a new form
     * @param request the Http request containing the JWT token
     *
     * @return saved form information if it succeeds, error message otherwise
     */
    @PostMapping("/saveForm")
    public ResponseEntity<Object> saveForm(
            @RequestBody @Valid SaveValidationFormDto saveFormDto,
            HttpServletRequest request){

        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> optionalUser = userService.findByUsername(username);
        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(saveFormDto.finalMapId());

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new MessageDto("You are not logged in or your cookie is not valid"));
        }

        final User user= optionalUser.get();
        final FinalMap finalMap= optionalFinalMap.get();

        if(validationFormService.hasAlreadyAFormForDepartmentForFinalMap(user, saveFormDto.department(),finalMap)){
            return ResponseEntity.status(401).body(new MessageDto("You have already a validation form for this department in this map"));
        }

        try{
            final ValidationForm validationForm= validationFormService.saveForm(
                    saveFormDto.department(),
                    saveFormDto.agreementLevel(),
                    saveFormDto.perceivedRisk(),
                    saveFormDto.certaintyLevel(),
                    saveFormDto.comment(),
                    user,
                    finalMap,
                    saveFormDto.isPublic());
            final ResponseValidationFormDto responseValidationFormDto= new ResponseValidationFormDto(validationForm);
            return ResponseEntity.status(200).body(responseValidationFormDto);

        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MessageDto(e.getMessage()));
        }


    }

    /**
     * Update a form
     *
     * @param updateValidationFormDto  information of about the form you want to update
     * @param request the Http request containing the JWT token
     *
     * @return updated form information if it succeeds, error message otherwise
     */
    @PutMapping("/updateForm")
    public ResponseEntity<Object> updateForm(
            @RequestBody @Valid UpdateValidationFormDto updateValidationFormDto,
            HttpServletRequest request){

        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> optionalUser = userService.findByUsername(username);

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new MessageDto("You are not logged in or your cookie is not valid"));
        }

        final Optional<ValidationForm> optionalValidationForm = validationFormService.findFormById(updateValidationFormDto.id());

        if(optionalValidationForm.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("No form with the specify id"));
        }

        final User user= optionalUser.get();
        final ValidationForm validationForm =optionalValidationForm.get();

        if(!user.equals(validationForm.getUser()) && !user.isAdmin()){
            return ResponseEntity.status(401).body(new MessageDto("You are not an admin or the author of this form"));
        }

        try {

            final ValidationForm newValidationForm = validationFormService.updateForm(
                    updateValidationFormDto.id(),
                    validationForm.getDepartment(),
                    updateValidationFormDto.agreementLevel(),
                    updateValidationFormDto.perceivedRisk(),
                    updateValidationFormDto.certaintyLevel(),
                    updateValidationFormDto.comment(),
                    user,
                    validationForm.getFinalMap(),
                    updateValidationFormDto.isPublic());
            final ResponseValidationFormDto responseValidationFormDto= new ResponseValidationFormDto(newValidationForm);
            return ResponseEntity.status(200).body(responseValidationFormDto);

        }catch (Exception e) {
            return ResponseEntity.status(401).body(new MessageDto(e.getMessage()));
        }
    }

    /**
     * Get a form based on its id
     *
     * @param id the id of the form you are looking for
     *
     * @return the form information if it exists, not found otherwise
     */
    @GetMapping("/form/{id}")
    public ResponseEntity<Object> getForm(@PathVariable long id){

       final Optional<ValidationForm> optionalValidationForm = validationFormService.findFormById(id);

       if(optionalValidationForm.isEmpty()){
           return ResponseEntity.status(404).body(new MessageDto("No form with the specify id"));
       }

        final ValidationForm validationForm= optionalValidationForm.get();
        final ResponseValidationFormDto responseValidationFormDto= new ResponseValidationFormDto(validationForm);
        return ResponseEntity.status(200).body(responseValidationFormDto);
    }

    /**
     * Get the form from the connected user for a specific department for a specific map
     *
     * @param finalMapId the id of the map you are intrested in
     * @param department the department you are interested in
     * @param request the Http request containing the JWT token
     *
     * @return the form information if it exists, error message or not found otherwise
     */
    @GetMapping("/myForm/{finalMapId}/{department}")
    public ResponseEntity<Object> getConnectUserFormForFinalMapForDepartment(
            @PathVariable long finalMapId,
            @PathVariable String department,
            HttpServletRequest request
            ){

        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> optionalUser = userService.findByUsername(username);
        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(finalMapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new MessageDto("You are not logged in or your cookie is not valid"));
        }

        final User user= optionalUser.get();
        final FinalMap finalMap= optionalFinalMap.get();

        final Optional<ValidationForm> optionalValidationForm = validationFormService
                .getFormForUserAndDepartmentAndFinalMap(
                user,
                department,
                finalMap);

        if(optionalValidationForm.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("You have no form for the specified department for this map"));
        }

        final ValidationForm validationForm= optionalValidationForm.get();
        final ResponseValidationFormDto responseValidationFormDto= new ResponseValidationFormDto(validationForm);
        return ResponseEntity.status(200).body(responseValidationFormDto);
    }

    /**
     * Get all existing form for a specific map
     *
     * @param finalMapId the id of the map you are intrested in
     *
     * @return the froms information for a map in a list, not found if the map doesn't exist
     */
    @GetMapping("/allFormForFinalMap/{finalMapId}")
    public ResponseEntity<Object> getAllFormForFinalMap(
            @PathVariable long finalMapId
    ){
        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(finalMapId);
        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }
        final FinalMap finalMap= optionalFinalMap.get();

        final List<ValidationForm> validationForms = validationFormService.getAllFormForFinalMap(finalMap);
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:validationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }

    /**
     * Get all the form for a specific department for a specific map
     *
     *@param finalMapId the id of the map you are intrested in
     * @param department the department you are interested in
     *
     * @return the forms information for a department for a map in a list, not found if the map doesn't exist
     */
    @GetMapping("/allFormForDepartmentForFinalMap/{finalMapId}/{department}")
    public ResponseEntity<Object> getAllFormForDepartmentForFinalMap(
            @PathVariable String department,
            @PathVariable long finalMapId){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(finalMapId);
        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }
        final FinalMap finalMap= optionalFinalMap.get();

        final List<ValidationForm> validationForms = validationFormService.getAllFormForDepartmentAndFinalMap(department,finalMap);
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:validationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }




}
