package utn.frba.dds.que_me_pongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

@EnableScheduling
@SpringBootApplication
public class QueMePongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);
        System.out.println();
}



}
