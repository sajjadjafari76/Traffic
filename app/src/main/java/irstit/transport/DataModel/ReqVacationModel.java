package irstit.transport.DataModel;

public class ReqVacationModel {

    private String FromDate;
    private String ToDate;
    private String Code;
    private String VacationType;
    private String Desc;
    private String Status;

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getVacationType() {
        return VacationType;
    }

    public void setVacationType(String vacationType) {
        VacationType = vacationType;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
