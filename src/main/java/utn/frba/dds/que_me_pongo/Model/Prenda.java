package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Helpers.Serializer.PrendaSerializer;

import java.awt.image.BufferedImage;
import java.io.File;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Prendas")
@JsonDeserialize( using = PrendaDeserializer.class)
@JsonSerialize(using = PrendaSerializer.class)
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    public Prenda(Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        this.id = id;
        this.tipoDeTela = tipoDeTela != null ? tipoDeTela : "";
        this.descripcion = descripcion != null ? descripcion : "";
        this.colorP = colorP != null ? colorP : "";
        this.colorS = colorS != null ? colorS : "";
        this.tipoDePrenda = tipoDePrenda != null ? tipoDePrenda: "";
        this.abrigo = 8.0;
        inicializarSuperposicion(tipoDePrenda);
    }

    @Override
    public boolean equals(Object obj) {
        Prenda p = (Prenda)obj;
        return p.getId() != -1 && this.id.equals(p.getId());
    }



    public int getId(){
        return id;
    }

    public Double getAbrigo(){
        return abrigo;
    }
    public String getDescripcion(){
        return descripcion;
    }

    public String getTipoDeTela(){
        return tipoDeTela;
    }
    public Integer getIndiceSuperposicion(){
        return indiceSuperposicion;
    }

    public String getTipoDePrenda(){
        return tipoDePrenda;
    }

    public void inicializarSuperposicion(String tipoPrenda){
        switch (tipoPrenda){
            case "Superior":
                switch (descripcion){
                    case "Remera": indiceSuperposicion = 1;
                        break;
                    case "Camisa": indiceSuperposicion = 1;
                        break;
                    case "Buzo": indiceSuperposicion = 2;
                        break;
                    case "Campera": indiceSuperposicion = 3;
                        break;
                    default: indiceSuperposicion = 0;
                }
                break;
                default: indiceSuperposicion = 0;
        }


    }




}


