package com.webgis.validationform;

import com.webgis.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValidationFormService{

    private final ValidationFormRepository validationFormRepository;

    public ValidationFormService(ValidationFormRepository validationFormRepository) {
        this.validationFormRepository = validationFormRepository;
    }

    public ValidationForm saveForm(
            String department,
            Integer agreementLevel,
            String perceivedRisk,
            Integer certaintyLevel,
            String comment,
            User user,
            boolean isPublic){

        final ValidationForm form = new ValidationForm(
                department,
                agreementLevel,
                perceivedRisk,
                certaintyLevel,
                comment,
                user,
                isPublic);

        return validationFormRepository.save(form);
    }

    public Optional<ValidationForm> findFormById(long id){ return validationFormRepository.findById(id);}

    public ValidationForm updateForm(
            long id,
            String department,
            Integer agreementLevel,
            String perceivedRisk,
            Integer certaintyLevel,
            String comment,
            User user,
            boolean isPublic){

            final Optional<ValidationForm> optionalForm =findFormById(id);
            if(optionalForm.isEmpty()){
                throw new IllegalArgumentException("Form does not exist");
            }

            final ValidationForm form= optionalForm.get();

            form.setDepartment(department);
            form.setAgreementLevel(agreementLevel);
            form.setPerceivedRisk(perceivedRisk);
            form.setCertaintyLevel(certaintyLevel);
            form.setComment(comment);
            form.setUser(user);
            form.setIsPublic(isPublic);

            return validationFormRepository.save(form);
    }

    public List<ValidationForm> getAllForm(){
        return validationFormRepository.findAll();
    }

    public List<ValidationForm> getAllFormFromAUser(User user){
        return validationFormRepository.findByUser(user);
    }

    public List<ValidationForm> getAllFormFromDepartment(String department){
        return validationFormRepository.findByDepartment(department);
    }
}
