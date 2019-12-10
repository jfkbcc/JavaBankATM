package edu.cuny.csi.csc330.bankatm;

public enum BankAccountType {
    None,
    Checking,
    Savings;

    public static BankAccountType getAccountType(String accountType) {
        switch (accountType.toLowerCase()) {
            case "checking":    return Checking;
            case "savings":     return Savings;
            default:            return None;
        }
    }

    public static String getAccountType(BankAccountType accountType) {
        switch (accountType) {
            case Checking:      return "checking";
            case Savings:       return "savings";
            default:            return "";
        }
    }
}
