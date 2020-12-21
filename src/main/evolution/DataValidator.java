package evolution;

public class DataValidator {
    public static boolean validateInputData(String[] data) throws IllegalArgumentException {
        for(int i = 0; i < 7; i++) {
            //third string is jungleRatio and should be check differently
            if(i != 2) {
                String isGood = DataValidator.validateData(data[i]);
                if(!isGood.equals("true"))
                    throw new IllegalArgumentException(isGood);
            }
            else {
                if(!((data[i].length() == 1 && (data[i].charAt(0) == 48 || data[i].charAt(0) == 49)) ||
                        (data[i].length() > 2 && data[i].charAt(0) == 48 && (data[i].charAt(1) == 44 || data[i].charAt(1) == 46))))
                    throw new IllegalArgumentException(data +
                            " must be from range [0,1] and second symbol cannot be other than dot or coma");

                for (int j = 2; j < data[i].length(); j++) {
                    if(data[i].charAt(j) < 48 || data[i].charAt(j) > 57)
                        throw new IllegalArgumentException(data + " cannot contain symbol other than number from 0 to 9");
                }
            }
        }
        if(Integer.parseInt(data[0]) * Integer.parseInt(data[1]) < Integer.parseInt(data[3]))
            throw new IllegalArgumentException("map " + data[0] + "x" + data[1] + " cannot contain so many animals");
        return true;
    }

    //check whether data is a right number; return information about result
    public static String validateData(String data) throws IllegalArgumentException{
        if(data.length() < 1)
            return "data cannot be empty string";
        if(data.charAt(0) < 49 || data.charAt(0) > 57)
            return data + " cannot begin with symbol other than number from 1 to 9";
        for(int i = 1; i < data.length(); i++) {
            if(data.charAt(i) < 48 || data.charAt(i) > 57)
                return data + " cannot contain symbol other than number from 0 to 9";
        }
        return "true";
    }
}
