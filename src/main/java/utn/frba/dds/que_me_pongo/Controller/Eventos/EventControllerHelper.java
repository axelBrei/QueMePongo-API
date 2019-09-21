package utn.frba.dds.que_me_pongo.Controller.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.DateHelper;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.FirebaseNotificationRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.NotificationBody;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.NotificationData;
import utn.frba.dds.que_me_pongo.WebServices.Responses.Notificacion.FirebaseNotificationrResponse;


public class EventControllerHelper {


    public static FirebaseNotificationrResponse sendFirebaseNotification(String userToken, long idEvento){
        try{
            FirebaseNotificationRequest request = new FirebaseNotificationRequest(userToken, new NotificationBody(), new NotificationData(idEvento));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));;
            headers.set("Authorization", "key=AIzaSyB6jzT_JA4kv_iCCTGiz8PSR723N4VRKHg");

            HttpEntity<FirebaseNotificationRequest> entity = new HttpEntity<>(request, headers);
            return restTemplate.postForObject("https://fcm.googleapis.com/fcm/send",entity, FirebaseNotificationrResponse.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static Integer convertirRepeticionANumeroDeDias(String repeticion){
        // diario, semanal, mensual, anual
        // TODO: fijarse los casos particulares cuando el mes tiene mas/menos de 30 dias y el año mas o menos de 365
        switch (repeticion.toLowerCase()){
            case "diario": return 1;
            case "semanal": return 7;
            case "mensual": return 30;
            case "anual": return 365;
            default: return null;
        }
    }

    public static Evento clonarEventorepetitivo(Evento original) {
        original.setDesde(
                DateHelper.sumarDiasAFecha(
                original.getDesde(),
                EventControllerHelper.convertirRepeticionANumeroDeDias(original.getFrecuencia())
        ));

        original.setHasta(
                DateHelper.sumarDiasAFecha(
                        original.getHasta(),
                        EventControllerHelper.convertirRepeticionANumeroDeDias(original.getFrecuencia())
                ));
        original.setAtuendo(null);
        original.setNotificado(false);
        return original;
    }
}
