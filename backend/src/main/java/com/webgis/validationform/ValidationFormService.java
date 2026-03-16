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

    /**
     * Save a validation form
     *
     * @param department the department which is concerned by the form
     * @param agreementLevel the agreement level of the expert for this department (1-4)
     * @param perceivedRisk the risk perceived by the expert (low-medium-hight)
     * @param certaintyLevel the certainty level of the expert on its own evaluation (1-4)
     * @param comment any comment of the expert
     * @param user the expert that produce the form
     * @param isPublic whether the form should be public for other experts or not
     *
     * @return the newly saved validationForm
     */
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

    /**
     * Search a form based on its id
     *
     * @param id the id of the form you are looking for
     *
     * @return the form with the specify id if it exists, empty otherwise
     */
    public Optional<ValidationForm> findFormById(long id){ return validationFormRepository.findById(id);}

    /**
     * Update the value of the form with the specify id
     *
     * @param id the id of the form you want to update
     * @param department the new department for the form
     * @param agreementLevel the new agreement level for the form
     * @param perceivedRisk the new perceived risk for the form
     * @param certaintyLevel the new certainty level for the form
     * @param comment the new comment for the form
     * @param user the new user for the form
     * @param isPublic whether the form should be public for other experts or not
     *
     * @throws IllegalArgumentException if there is no form with the specify id
     *
     * @return the modified validationForm
     */
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

    /**
     * Get all form
     *
     * @return A list of all form
     */
    public List<ValidationForm> getAllForm(){
        return validationFormRepository.findAll();
    }

    /**
     * Get all the form concerning a specific department
     *
     * @param department the department you are interested in
     *
     * @return a list of all the forms for this department
     */
    public List<ValidationForm> getAllFormForDepartment(String department){
        return validationFormRepository.findByDepartment(department);
    }

    /**
     * Get a form for a specific user and a specific department
     *
     * @param user the user you are interested in
     * @param department the department you are interested in
     *
     * @return the form of the specify user for the specify department if it exists, empty otherwise
     */
    public Optional<ValidationForm> getFormForUserAndDepartment(User user,String department){
        return validationFormRepository.findByUserAndDepartment(user,department);
    }

    /**
     * Check whether a user has a validation form for a department
     * @param user the user you want to check
     * @param department the department you want to check
     *
     * @return True if the specify user has already a form for the specify department, False otherwise
     */
    public boolean hasAlreadyAFormForDepartment(User user,String department){
        return validationFormRepository.existsByUserAndDepartment(user,department);
    }

}
