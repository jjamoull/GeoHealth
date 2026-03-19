package com.webgis.validationform;

import com.webgis.map.finalmap.FinalMap;
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
     * @param finalMap the map for which the validation form is made
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
            FinalMap finalMap,
            boolean isPublic){

        final ValidationForm form = new ValidationForm(
                department,
                agreementLevel,
                perceivedRisk,
                certaintyLevel,
                comment,
                user,
                finalMap,
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
     * @param finalMap the new map to which the form is attached
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
            FinalMap finalMap,
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
            form.setFinalMap(finalMap);
            form.setIsPublic(isPublic);

            return validationFormRepository.save(form);
    }

    /**
     * Get all form for a map
     *
     * @param finalMap the map from which you want the validation forms
     *
     * @return A list of all form linked to a specific map
     */
    public List<ValidationForm> getAllFormForFinalMap(FinalMap finalMap){
        return validationFormRepository.findByFinalMap(finalMap);
    }

    /**
     * Get all the form concerning a specific department for a map
     *
     * @param department the department you are interested in
     * @param finalMap the map from which you want the validation forms
     *
     * @return a list of all the forms for a department linked to a specific map
     */
    public List<ValidationForm> getAllFormForDepartmentAndFinalMap(String department,FinalMap finalMap){
        return validationFormRepository.findByDepartmentAndFinalMap(department,finalMap);
    }

    /**
     * Get a form for a specific user and a specific department for a specific map
     *
     * @param user the user you are interested in
     * @param department the department you are interested in
     * @param finalMap the map from which you want the validation forms
     *
     * @return the form of the specify user for the specify department for a specific map if it exists, empty otherwise
     */
    public Optional<ValidationForm> getFormForUserAndDepartmentAndFinalMap(User user, String department,FinalMap finalMap){
        return validationFormRepository.findByUserAndDepartmentAndFinalMap(user,department,finalMap);
    }

    /**
     * Check whether a user has a validation form for a department for a specific map
     *
     * @param user the user you want to check
     * @param department the department you want to check
     * @param finalMap the map from which you want the validation form
     *
     * @return True if the specify user has already a form for the specific department for a specific map , False otherwise
     */
    public boolean hasAlreadyAFormForDepartmentForFinalMap(User user,String department,FinalMap finalMap){
        return validationFormRepository.existsByUserAndDepartmentAndFinalMap(user,department,finalMap);
    }

}
