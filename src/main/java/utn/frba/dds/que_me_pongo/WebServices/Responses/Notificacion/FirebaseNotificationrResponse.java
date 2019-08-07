package utn.frba.dds.que_me_pongo.WebServices.Responses.Notificacion;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FirebaseNotificationrResponse {
    long multicast_id;
    int success;
    int failure;
    int canonical_ids;
    List<Map<String, Object>> results;
}
