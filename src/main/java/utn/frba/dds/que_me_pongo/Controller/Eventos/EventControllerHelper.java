package utn.frba.dds.que_me_pongo.Controller.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.DateHelper;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.FirebaseNotificationRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.NotificationBody;
import utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones.NotificationData;
import utn.frba.dds.que_me_pongo.WebServices.Responses.Notificacion.FirebaseNotificationrResponse;


public class EventControllerHelper {


    public static FirebaseNotificationrResponse sendFirebaseNotification(String userToken, long idEvento, @NonNull String bodyS, @Nullable String title){
        try{
            NotificationBody body = new NotificationBody();
            body.setBody(bodyS);
            if(title != null){
                body.setTitle(title);
            }
            FirebaseNotificationRequest request = new FirebaseNotificationRequest(userToken, body, new NotificationData(idEvento));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));;
            headers.set("Authorization", "key=AIzaSyB6jzT_JA4kv_iCCTGiz8PSR723N4VRKHg");
            headers.set("Content-Type", "application/json");

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
            default: return 0;
        }
    }
    public static Cliente getEventosFuturos(ClientesRepository repository,Cliente c, Evento e){
        e.setUidEvento(String.valueOf(e.getId()));
        int numRepeticiones = 0;
        int intervaloRepeticion = convertirRepeticionANumeroDeDias(e.getFrecuencia());
        switch (intervaloRepeticion){
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
        try{
            Evento evento = e.clone();
            evento.setDesde(e.getDesde());
            evento.setHasta(e.getHasta());
            for(int i = 0; i < numRepeticiones; i++){
                Evento ev = clonarYAumentarFecha(evento, intervaloRepeticion);
                if(i == numRepeticiones -1){
                    ev.setNotificado(null);
                }
                evento.setDesde(ev.getDesde());
                evento.setHasta(ev.getHasta());
                c.getEventos().add(ev);
            }
            return c;
        }catch (CloneNotSupportedException ex){
            ex.printStackTrace();
        }
        return c;
    }


    private static Evento clonarYAumentarFecha(Evento original, int intervalo){
        Evento evento = new Evento();
        evento.setId(null);
        evento.setFrecuencia(original.getFrecuencia());
        evento.setUidEvento(original.getUidEvento());
        evento.setAtuendo(original.getAtuendo());
        evento.setNombre(original.getNombre());
        evento.setFormalidad(original.getFormalidad());
        evento.setGenerados(original.getGenerados());
        evento.setLatitud(original.getLatitud());
        evento.setLongitud(original.getLongitud());
        evento.setId_guardarropa(original.getId_guardarropa());
        evento.setDesde(DateHelper.sumarDiasAFecha(original.getDesde(), intervalo));
        evento.setHasta(DateHelper.sumarDiasAFecha(original.getHasta(), intervalo));
        return evento;
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


    public static Cliente borrarEventosAPartirDeUnaSemana(Cliente cliente){
        List<Evento> eventoList = cliente.getEventos();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, - 1);
        Date semanaAntes = calendar.getTime();
        eventoList.removeIf( evento -> evento.getDesde().before(semanaAntes));
        return cliente;
    }
}
