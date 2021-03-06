package tech.lapsa.insurance.ws.rs.app;

import java.time.LocalDate;

import com.lapsa.insurance.elements.InsuranceClassType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.InsuredAgeClass;
import com.lapsa.insurance.elements.InsuredExpirienceClass;
import com.lapsa.insurance.elements.VehicleAgeClass;
import com.lapsa.insurance.elements.VehicleClass;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.CountryCode;
import com.lapsa.international.phone.PhoneNumber;
import com.lapsa.kz.country.KZArea;

import tech.lapsa.insurance.ws.jaxb.entity.XmlCallbackRequestInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPaymentInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPeriodInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPersonalData;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPolicyDriverInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPolicyInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPolicyRequestInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlPolicyVehicleInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlRequestCheckPolicy;
import tech.lapsa.insurance.ws.jaxb.entity.XmlRequesterInfo;
import tech.lapsa.insurance.ws.jaxb.entity.XmlUTMInfo;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class SamplesUtil {
    public static XmlPolicyDriverInfo driverSample() {
	final XmlPolicyDriverInfo sample = new XmlPolicyDriverInfo(TaxpayerNumber.of("811203400953"),
		InsuranceClassType.CLASS_10,
		InsuredAgeClass.OVER25, InsuredExpirienceClass.LESS2, false,
		new XmlPersonalData("Иван", "Иванов", "Иванович", LocalDate.of(1976, 4, 1)));
	return sample;
    }

    public static XmlPolicyVehicleInfo vehicleSample() {
	final XmlPolicyVehicleInfo sample = new XmlPolicyVehicleInfo(KZArea.GALM, true, VehicleClass.CAR,
		VehicleAgeClass.UNDER7, false);
	return sample;
    }

    public static XmlPolicyInfo policySample() {
	final XmlPolicyInfo sample = new XmlPolicyInfo(new XmlPolicyDriverInfo[] { driverSample() },
		new XmlPolicyVehicleInfo[] { vehicleSample() }, periodSample(), 18252.27d);
	return sample;
    }

    public static XmlPeriodInfo periodSample() {
	return new XmlPeriodInfo(LocalDate.of(2019, 04, 01), LocalDate.of(2019, 04, 01).plusYears(1).minusDays(1));
    }

    public static XmlUTMInfo utmSample() {
	return new XmlUTMInfo("google", "cpc", "context-advertising", "something", "key word one and two");
    }

    public static XmlRequesterInfo requesterSample() {
	final XmlRequesterInfo sample = new XmlRequesterInfo("Джон Булл",
		PhoneNumber.of(CountryCode.KZ, "701", "9655474"),
		LocalizationLanguage.RUSSIAN, "john.smith@email.com");
	return sample;
    }

    public static XmlPolicyRequestInfo policyRequestSample() {
	final XmlPolicyRequestInfo sample = new XmlPolicyRequestInfo(requesterSample(), utmSample(),
		policySample(), paymentSample(), InsuranceRequestType.EXPRESS);
	return sample;
    }

    public static XmlPaymentInfo paymentSample() {
	return new XmlPaymentInfo();
    }

    public static XmlCallbackRequestInfo callbackRequestSample() {
	final XmlCallbackRequestInfo sample = new XmlCallbackRequestInfo(requesterSample(), utmSample(),
		"Pls call back ASAP");
	return sample;
    }

    public static XmlRequestCheckPolicy checkPolicyRequestSample() {
	final XmlRequestCheckPolicy sample = new XmlRequestCheckPolicy("ASDADSA");
	return sample;
    }

}
