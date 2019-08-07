package utn.frba.dds.que_me_pongo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Prendas")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Prenda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String tipoDeTela = "";
    String descripcion = "";
    String colorP = "";
    String colorS = "";
    Double abrigo;
    // Ej. Superior-4, Inferior
    String tipoDePrenda = "";
    Integer indiceSuperposicion;


    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Prenda p = (Prenda)obj;
        return p.getId() != null && this.id.equals(p.getId());
    }
}


