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
import org.springframework.web.bind.annotation.*;

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
            ValidationForm validationForm= validationFormService.saveForm(
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

    @GetMapping("/allForm")
    public ResponseEntity<Object> getAllForm(){
        final List<ValidationForm> ValidationForms = validationFormService.getAllForm();
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:ValidationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }

    @GetMapping("/allFormForDepartment/{department}")
    public ResponseEntity<Object> getAllFormForDepartment(@PathVariable String department){

        final List<ValidationForm> ValidationForms = validationFormService.getAllFormForDepartment(department);
        final List<ResponseValidationFormDto> responseValidationForms = new ArrayList<>();
        for(ValidationForm validationForm:ValidationForms){
            responseValidationForms.add(new ResponseValidationFormDto(validationForm));
        }
        return ResponseEntity.status(200).body(responseValidationForms);
    }




}
