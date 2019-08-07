package utn.frba.dds.que_me_pongo.WebServices.Request.Notificaciones;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class NotificationBody {
    String title = "Que me pongo";
    String body = "Ya estan listos tus atuendos!";
    String sound = "default";
}
