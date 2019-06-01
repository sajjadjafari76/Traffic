package irstit.transport.DataModel;

public class DriverInfoModel {

    private String Name;
    private String Family;
    private String Parent;
    private String NationalCode;
    private String Telephone;
    private String BirthCertificate;
    private String VehiclePelak;
    private String VehicleModel;
    private String VehicleCode;
    private String VehicleType;
    private String LineType;
    private String RegisterDate;
    private String Picture;
    private String Owner;
    private String OwnerId;
    private String IsTaxi;

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

    public String getVehiclePelak() {
        return VehiclePelak;
    }

    public void setVehiclePelak(String vehiclePelak) {
        VehiclePelak = vehiclePelak;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getLineType() {
        return LineType;
    }

    public void setLineType(String lineType) {
        LineType = lineType;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public String getVehicleCode() {
        return VehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        VehicleCode = vehicleCode;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getIsTaxi() {
        return IsTaxi;
    }

    public void setIsTaxi(String isTaxi) {
        IsTaxi = isTaxi;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }
}
