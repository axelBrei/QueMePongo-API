package utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FirebaseNotificationRequest {
    String to;
    NotificationBody notification;
    NotificationData data;
}
