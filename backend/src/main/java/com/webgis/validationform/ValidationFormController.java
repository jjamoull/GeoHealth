package com.webgis.validationform;

import com.webgis.MessageDto;
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

    public ValidationFormController(ValidationFormService validationFormService,
                                    JwtService jwtService,
                                    CookieService cookieService, UserService userService) {
        this.validationFormService = validationFormService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.userService = userService;
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

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new MessageDto("You are not logged in or your cookie is not valid"));
        }

        final User user= optionalUser.get();

        if(validationFormService.hasAlreadyAFormForDepartment(user, saveFormDto.department())){
            return ResponseEntity.status(401).body(new MessageDto("You have already a validation form for this department"));
        }

        try{
            final ValidationForm validationForm= validationFormService.saveForm(
                    saveFormDto.department(),
                    saveFormDto.agreementLevel(),
                    saveFormDto.perceivedRisk(),
                    saveFormDto.certaintyLevel(),
                    saveFormDto.comment(),
                    user,
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
    @PostMapping("/updateForm")
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
     * Get the form from the conncted user for a specific department
     *
     * @param department the department you are interested in
     * @param request the Http request containing the JWT token
     *
     * @return the form information if it exists, error message or not found otherwise
     */
    @GetMapping("/myForm/{department}")
    public ResponseEntity<Object> getConnectUserFormForDepartment(
            @PathVariable String department,
            HttpServletRequest request
            ){

        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> optionalUser = userService.findByUsername(username);

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new MessageDto("You are not logged in or your cookie is not valid"));
        }

        final User user= optionalUser.get();

        final Optional<ValidationForm> optionalValidationForm = validationFormService.getFormForUserAndDepartment(user,department);

        if(optionalValidationForm.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("You have no form for the specified department"));
        }

        final ValidationForm validationForm= optionalValidationForm.get();
        final ResponseValidationFormDto responseValidationFormDto= new ResponseValidationFormDto(validationForm);
        return ResponseEntity.status(200).body(responseValidationFormDto);
    }

    /**
     * Get all existing form
     *
     * @return the froms information in a list
     */
    @GetMapping("/allForm")
    public ResponseEntity<Object> getAllForm(){
        final List<ValidationForm> validationForms = validationFormService.getAllForm();
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:validationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }

    /**
     * Get all the form for a specific department
     *
     * @param department the department you are interested in
     *
     * @return the forms information in a list
     */
    @GetMapping("/allFormForDepartment/{department}")
    public ResponseEntity<Object> getAllFormForDepartment(@PathVariable String department){

        final List<ValidationForm> validationForms = validationFormService.getAllFormForDepartment(department);
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:validationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }




}
