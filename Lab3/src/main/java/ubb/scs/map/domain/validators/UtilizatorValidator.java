package ubb.scs.map.domain.validators;


import ubb.scs.map.domain.Utilizator;

/***
 * Clasa validator pentru utilizator
 */
public class UtilizatorValidator implements Validator<Utilizator> {
    /***
     * Metoda de validare pentru un utilizator
     * @param entity Utilizatorul care va fi validat
     * @throws ValidationException daca utilizatorul are numele sau prenumele vid sau daca nu incep cu litera mare
     */
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if(entity.getFirstName().isEmpty())
            throw new ValidationException("Utilizatorul nu este valid");
        if(entity.getLastName().isEmpty()){
            throw new ValidationException("Utilizatorul nu este valid");
        }
        if(!Character.isUpperCase(entity.getFirstName().charAt(0))){
            throw new ValidationException("Utilizatorul nu este valid");
        }
        if(!Character.isUpperCase(entity.getLastName().charAt(0))){
            throw new ValidationException("Utilizatorul nu este valid");
        }
    }
}
