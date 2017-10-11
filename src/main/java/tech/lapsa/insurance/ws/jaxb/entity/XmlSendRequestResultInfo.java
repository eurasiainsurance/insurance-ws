package tech.lapsa.insurance.ws.jaxb.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name = "sendRequestResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSendRequestResultInfo extends XmlSendRequestResultShort {
    private static final long serialVersionUID = -5641687980684467014L;

    @XmlAttribute
    protected Integer requestId;

    @XmlAttribute
    protected String ebillId;

    public XmlSendRequestResultInfo() {
    }

    public XmlSendRequestResultInfo(String message, Integer requestId, String ebillId) {
	super(message);
	this.requestId = requestId;
	this.ebillId = ebillId;
    }

    public XmlSendRequestResultInfo(String message, Integer requestId) {
	super(message);
	this.requestId = requestId;
    }

    public XmlSendRequestResultInfo(String message) {
	super(message);
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public Integer getRequestId() {
	return requestId;
    }

    public void setRequestId(Integer requestId) {
	this.requestId = requestId;
    }

    public String getEbillId() {
	return ebillId;
    }

    public void setEbillId(String ebillId) {
	this.ebillId = ebillId;
    }
}