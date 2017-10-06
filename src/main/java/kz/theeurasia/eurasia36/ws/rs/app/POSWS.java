package kz.theeurasia.eurasia36.ws.rs.app;

import static com.lapsa.utils.RESTUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.lapsa.eurasia36.facade.CompanyPointOfSaleFacade;
import com.lapsa.insurance.domain.CompanyContactEmail;
import com.lapsa.insurance.domain.CompanyContactPhone;
import com.lapsa.insurance.domain.CompanyPointOfSale;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.kz.country.KZCity;
import com.lapsa.validation.NotNullValue;

import kz.theeurasia.eurasia36.ws.jaxb.entity.XmlGeo;
import kz.theeurasia.eurasia36.ws.jaxb.entity.XmlPOS;
import kz.theeurasia.eurasia36.ws.jaxb.entity.XmlPOSCity;
import kz.theeurasia.eurasia36.ws.jaxb.entity.XmlPOSEmail;
import kz.theeurasia.eurasia36.ws.jaxb.entity.XmlPOSPhone;
import kz.theeurasia.eurasia36.ws.rs.entity.LocalizationLanguageWrapped;

@Path("/pos")
@Produces({ MediaType.APPLICATION_JSON })
@PermitAll
@Singleton
public class POSWS extends ALanguageDetectorWS {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CompanyPointOfSaleFacade posFacade;

    @GET
    @Path("/all/{lang}")
    public Response getAllOwnAvailableLangPath(
	    @PathParam("lang") @NotNullValue LocalizationLanguageWrapped queryLangWrapped) {
	LocalizationLanguage lang = getLanguageOrDefault(queryLangWrapped);

	return responseOk(all(lang), lang.getLocale());
    }

    @GET
    @Path("/all")
    public Response getAllOwnAvailableLangDefault(@QueryParam("lang") LocalizationLanguageWrapped queryLangWrapped) {
	LocalizationLanguage lang = getLanguageOrDefault(queryLangWrapped);
	return responseOk(all(lang), lang.getLocale());
    }

    private Object all(LocalizationLanguage language) {

	final List<CompanyPointOfSale> poses = posFacade.findAllOwnOffices();

	List<KZCity> order = new ArrayList<>();
	Map<KZCity, List<CompanyPointOfSale>> cityMap = new HashMap<>();
	for (CompanyPointOfSale pos : poses) {
	    if (!cityMap.containsKey(pos.getAddress().getCity())) {
		cityMap.put(pos.getAddress().getCity(), new ArrayList<CompanyPointOfSale>());
		order.add(pos.getAddress().getCity());
	    }
	    cityMap.get(pos.getAddress().getCity()).add(pos);
	}

	List<XmlPOSCity> result = new ArrayList<>();
	for (KZCity kzc : order) {
	    XmlPOSCity city = new XmlPOSCity();
	    result.add(city);
	    city.setName(kzc.displayName(language.getLocale()));

	    {
		List<XmlPOS> list1 = new ArrayList<>();
		for (CompanyPointOfSale cpos : cityMap.get(kzc)) {
		    XmlPOS pos = new XmlPOS();
		    list1.add(pos);
		    pos.setId(cpos.getId());
		    pos.setName(cpos.displayNameShort(language.getLocale()));
		    pos.setAddress(cpos.displayName(language.getLocale()));
		    pos.setDeliveryServiceEnable(cpos.isDeliveryServicesAvailable());
		    pos.setPickupServiceAvailable(cpos.isPickupAvailable());

		    if (cpos.getGeoPoint() != null) {
			pos.setGeoPoint(new XmlGeo());
			pos.getGeoPoint().setLat(cpos.getGeoPoint().getLatitude());
			pos.getGeoPoint().setLng(cpos.getGeoPoint().getLongitude());
		    }

		    {
			List<XmlPOSPhone> list2 = new ArrayList<>();
			for (CompanyContactPhone ccp : cpos.getPhones()) {
			    XmlPOSPhone phone = new XmlPOSPhone();
			    list2.add(phone);
			    phone.setType(ccp.getPhoneType().displayName(language.getLocale()));
			    phone.setFullNumber(ccp.getPhone().getFormatted());
			}
			pos.setPhones(list2.toArray(new XmlPOSPhone[0]));
		    }

		    {
			List<XmlPOSEmail> list2 = new ArrayList<>();
			for (CompanyContactEmail cce : cpos.getEmailAddresses()) {
			    XmlPOSEmail xmlPOSEmail = new XmlPOSEmail();
			    list2.add(xmlPOSEmail);
			    xmlPOSEmail.setAddress(cce.getAddress());
			}
			pos.setEmails(list2.toArray(new XmlPOSEmail[0]));
		    }

		}
		city.setPoses(list1.toArray(new XmlPOS[0]));
	    }
	}

	return result;
    }
}
