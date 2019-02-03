package irstit.transport.DataModel;

public class DriverInfoModel {

    private String Name;
    private String Family;
    private String Parent;
    private String NationalCode;
    private String Telephone;
    private String BirthCertificate;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getBirthCertificate() {
        return BirthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        BirthCertificate = birthCertificate;
    }
}
