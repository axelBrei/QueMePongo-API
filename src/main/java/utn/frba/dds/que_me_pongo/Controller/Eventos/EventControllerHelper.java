package utn.frba.dds.que_me_pongo.Controller.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public static Integer convertirRepeticionANumeroDeDias(String repeticion){
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
    public static List<Evento> getEventosFuturos(Evento e){
        e.setUidEvento(String.valueOf(e.getId()));
        List<Evento> aux = new ArrayList<>();
        int numRepeticiones = 1;
        switch (convertirRepeticionANumeroDeDias(e.getFrecuencia())){
            case 1: {
                numRepeticiones = 30;
                break;
            }
            case 7: {
                numRepeticiones = 8;
                break;
            }
            case 30: {
                numRepeticiones = 12;
                break;
            }
            case 365: {
                numRepeticiones = 4;
            }
        }
        for(int i = 0; i < numRepeticiones; i++){
            aux.add(e);
            if(i == numRepeticiones -1){
                e.setNotificado(null);
            }
        }
        return aux;
    }

    public static boolean hayQueCrearNuevosEventos(Evento evento){
        return evento.getNotificado() == null;
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
