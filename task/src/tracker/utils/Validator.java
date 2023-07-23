package tracker.utils;


import java.util.regex.Pattern;

public class Validator {
    public boolean validateMail(String mail){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{1,}$");
        return pattern.matcher(mail).matches();
    }

    public boolean validateFirstName(String firstName){
        Pattern pattern = Pattern.compile("^(?!.*[-'][-'])(?!.*[-']$)(?!^[-'])(?!^[-'][-'])[A-Za-z][-'A-Za-z ]{1,}$");
        return pattern.matcher(firstName).matches();
    }

    public boolean validateLastName(String lastName){
        Pattern pattern = Pattern.compile("^(?!.*[-'][-'])(?!.*[-']$)(?!^[-'])(?!^[-'][-'])[A-Za-z][-'A-Za-z ]{1,}$");
        return pattern.matcher(lastName).matches();
    }

    public boolean validateFirstLastName(String firstLastName){
        Pattern pattern = Pattern.compile("^(?!.*[-'][-'])(?!.*[-']$)(?!^[-'])(?!^[-'][-']).{2,} [^\\s][-'\\w ]{1,}(?<!-)$");
        return pattern.matcher(firstLastName).matches();
    }

}
