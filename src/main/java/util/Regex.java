package util;

import lombok.Getter;

import java.util.regex.Pattern;

public class Regex {
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
    private static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])(com)$");
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
    private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");
    private static final Pattern customerId = Pattern.compile("^C0[0-9]{2,}$");
    private static final Pattern ItemCode = Pattern.compile("^I0[0-9]{2,}$");

    //private static final Pattern inventoryId = Pattern.compile("^I0[0-9]{2,}$");
    private static final Pattern employeeId = Pattern.compile("^E0[0-9]{2,}$");
    private static final Pattern serviceId = Pattern.compile("^Svc0[0-9]{2,}$");
    private static final Pattern supplierId = Pattern.compile("^Sup0[0-9]{2,}$");
    private static final Pattern contactPattern = Pattern.compile("^0[0-9]{9}$");
    private static final Pattern passwordPattern = Pattern.compile("^[0-9a-zA-Z]{8}$");
    private static final Pattern addressPattern = Pattern.compile("^[A-Za-z0-9'\\/\\.\\,]{5,}$");

    private static final Pattern oldIDPattern = Pattern.compile("^[0-9]{9}[vVxX]$");

    public static Pattern getItemCode(){return ItemCode;}

    public static Pattern getOldIDPattern() {return oldIDPattern;}

    public static Pattern getAddressPattern(){
        return addressPattern;
    }
    public static Pattern getCustomerId() {
        return customerId;
    }

    public static Pattern getNamePattern() {
        return namePattern;
    }

    public static Pattern getDoublePattern() {
        return doublePattern;
    }

    public static Pattern getEmailPattern() {
        return emailPattern;
    }

    public static Pattern getIntPattern() {
        return intPattern;
    }

    public static Pattern getContactPattern() {
        return contactPattern;
    }


    public static Pattern getEmployeeId() {
        return employeeId;
    }

    public static Pattern getServiceId() {
        return serviceId;
    }

    public static Pattern getSupplierId() {
        return supplierId;
    }

    public static Pattern getPasswordPattern() {
        return passwordPattern;
    }
}
