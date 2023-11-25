package com.example.damanhacker.utlities;

public class matchingValues {

    public boolean match(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if ((currentValue == 9) || currentValue == 1) {
                check = true;
            }

        } else if (matchValue == 1) {
            if ((currentValue == 0) || currentValue == 2) {
                check = true;
            }
        } else if (matchValue == 2) {
            if ((currentValue == 1) || currentValue == 3) {
                check = true;
            }
        } else if (matchValue == 3) {
            if ((currentValue == 2) || currentValue == 4) {
                check = true;
            }
        } else if (matchValue == 4) {
            if ((currentValue == 3) || currentValue == 5) {
                check = true;
            }
        } else if (matchValue == 5) {
            if ((currentValue == 4) || currentValue == 6) {
                check = true;
            }
        } else if (matchValue == 6) {
            if ((currentValue == 5) || currentValue == 7) {
                check = true;
            }
        } else if (matchValue == 7) {
            if ((currentValue == 6) || currentValue == 8) {
                check = true;
            }
        } else if (matchValue == 8) {
            if ((currentValue == 7) || currentValue == 9) {
                check = true;
            }
        } else if (matchValue == 9) {
            if ((currentValue == 8) || currentValue == 0) {
                check = true;
            }
        }

        return check;
    }

    public boolean matchOposite(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {
            if ((currentValue == 9) || (currentValue == 1) || (currentValue == 5)) {
                check = true;
            }

        } else if (matchValue == 1) {
            if ((currentValue == 0) || (currentValue == 2) || (currentValue == 9)) {
                check = true;
            }
        } else if (matchValue == 2) {
            if ((currentValue == 1) || (currentValue == 3) || (currentValue == 6)) {
                check = true;
            }
        } else if (matchValue == 3) {
            if ((currentValue == 2) || (currentValue == 4) || (currentValue == 5)) {
                check = true;
            }
        } else if (matchValue == 4) {
            if ((currentValue == 3) || (currentValue == 5) || (currentValue == 4)) {
                check = true;
            }
        } else if (matchValue == 5) {
            if ((currentValue == 4) || (currentValue == 6) || (currentValue == 3)) {
                check = true;
            }
        } else if (matchValue == 6) {
            if ((currentValue == 5) || (currentValue == 7) || (currentValue == 2)) {
                check = true;
            }
        } else if (matchValue == 7) {
            if ((currentValue == 6) || (currentValue == 8) || (currentValue == 1)) {
                check = true;
            }
        } else if (matchValue == 8) {
            if ((currentValue == 7) || (currentValue == 9) || (currentValue == 0)) {
                check = true;
            }
        } else if (matchValue == 9) {
            if ((currentValue == 0) || (currentValue == 1) || (currentValue == 9)) {
                check = true;
            }
        }
        //  System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public boolean matchSB_(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if (getValue(currentValue).equals("Big")) {
                check = true;
            }

        } else if (matchValue == 1) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 2) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 3) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 4) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 5) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 6) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 7) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 8) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 9) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        }
        //System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public String getValueSB(int number) {
        String value = "";
        switch (number) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 9:
                value = "Small";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                value = "Big";
                break;
        }
        return value;
    }

    public boolean matchSB(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if (getValue(currentValue).equals("Small")) {
                check = true;
            }

        } else if (matchValue == 1) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 2) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 3) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 4) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 5) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 6) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 7) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 8) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 9) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        }
        //System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public boolean matchOpositeTEST(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if ((currentValue == 1) || (currentValue == 5)) {
                check = true;
            }
        }

        //  System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public int getFValue(int currentValue) {
        if (currentValue == 0) {
            currentValue = 9;
        }
        return currentValue;
    }

    public boolean matchSB__(int matchValue, int currentValue) {
        return (matchValue <= 4 && getValue(currentValue).equals("Small")) || (matchValue >= 5 && getValue(currentValue).equals("Big"));
    }

    public int getPValue(int currentValue) {
        if (currentValue == 9) {
            currentValue = 0;
        }
        return currentValue;
    }

    public String getValue(int number) {
        String value = "";
        switch (number) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                value = "Small";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                value = "Big";
                break;
        }
        return value;
    }

    public String convertOpositeValue(String str) {
        String returnValue;
        if (str.equals("Small")) {
            returnValue = "Big";
        } else {
            returnValue = "Small";
        }
        return returnValue;
    }

    public boolean valueMatching(String currentValue, String matchValue) {
        // System.out.println(currentValue + ":" + matchValue);
        return !matchValue.equals(currentValue);
    }

}
