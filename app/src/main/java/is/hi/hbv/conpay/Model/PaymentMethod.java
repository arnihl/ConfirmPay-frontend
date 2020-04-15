package is.hi.hbv.conpay.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class PaymentMethod implements Serializable {
    private String pName;
    private String nameOfPayer;
    private String SSN;
    private String cardType; //MasterCard, Visa, etc.
    private String cardNo; // Var accountno í UML-i
    private String expirationDate;
    private String securityNo;
    private String email;
    @JsonProperty("ownerId")
    @JsonDeserialize(as = Long.class)
    @JsonSerialize(using= ToStringSerializer.class)
    private long ownerId; // finna út hvernig optional virkar almennilega!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date pDate;
    public PaymentMethod() {
    }

    /*public PaymentMethod(String name, String nameOfPayer,
                         String SSN, String cardType, String cardNo,
                         String expirationDate, String securityNo, String email, Date date) {
        this.pName = name;
        this.nameOfPayer = nameOfPayer;
        this.SSN = SSN;
        this.cardType = cardType;
        this.cardNo = cardNo;
        this.expirationDate = expirationDate;
        this.securityNo = securityNo;
        this.email = email;
        this.pDate = date;
    }*/
    // annar constructor fyrir optional ownerId;
    public PaymentMethod(String name, String nameOfPayer,
                         String SSN, String cardType, String cardNo,
                         String expirationDate, String securityNo, String email, Date date, long ownerId) {
        this.pName = name;
        this.nameOfPayer = nameOfPayer;
        this.SSN = SSN;
        this.cardType = cardType;
        this.cardNo = cardNo;
        this.expirationDate = expirationDate;
        this.securityNo = securityNo;
        this.email = email;
        this.ownerId = ownerId;
        this.pDate = date;
    }

    public String getName() {
        return pName;
    }

    public void setName(String pName) {
        this.pName = pName;
    }

    public String getNameOfPayer() {
        return nameOfPayer;
    }

    public void setNameOfPayer(String nameOfPayer) {
        this.nameOfPayer = nameOfPayer;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityNo() {
        return securityNo;
    }

    public void setSecurityNo(String securityNo) {
        this.securityNo = securityNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

   public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }
}