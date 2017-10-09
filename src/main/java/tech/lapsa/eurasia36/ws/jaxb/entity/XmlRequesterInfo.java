package tech.lapsa.eurasia36.ws.jaxb.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lapsa.international.internet.validators.ValidEmail;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;
import com.lapsa.international.phone.validators.ValidPhoneNumber;
import com.lapsa.validation.NotNullValue;

@XmlRootElement(name = "requester")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlRequesterInfo implements Serializable {
    private static final long serialVersionUID = -4399606300070268217L;

    @XmlAttribute
    protected String name;

    @XmlAttribute
    @NotNullValue
    @ValidPhoneNumber
    protected PhoneNumber phone;

    @XmlAttribute
    @NotNullValue
    protected LocalizationLanguage language;

    @XmlAttribute
    @ValidEmail
    protected String email;

    public XmlRequesterInfo() {
    }

    public XmlRequesterInfo(String name, PhoneNumber phone, LocalizationLanguage language, String email) {
	this.name = name;
	this.phone = phone;
	this.language = language;
	this.email = email;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public PhoneNumber getPhone() {
	return phone;
    }

    public void setPhone(PhoneNumber phone) {
	this.phone = phone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public LocalizationLanguage getLanguage() {
	return language;
    }

    public void setLanguage(LocalizationLanguage language) {
	this.language = language;
    }
}