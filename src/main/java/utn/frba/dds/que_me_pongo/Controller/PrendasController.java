package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendaGuardarroparepository;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaLimitException;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Guardarropa.GetPrendasRequest;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Prenda.DeletePrendaRequest;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Prenda.NuevaPrendaRequest;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Responses.GetPrendasResponse;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Set;

import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;

@RestController
@RequestMapping("/prendas")
public class PrendasController {
    String fileBasePath = "src/main/resources/fotos/";
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    PrendaGuardarroparepository prendaGuardarroparepository;


    @RequestMapping(value = "getPrendas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getPrendasGuardarropas(@RequestBody GetPrendasRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Prenda> prendas = cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas();

        GetPrendasResponse response = new GetPrendasResponse();
        response.setPrendas(prendas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "addPrenda" ,  method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPrendaToGuardarropa(@RequestBody NuevaPrendaRequest request) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(request.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(Integer.parseInt(request.getIdGuardarropa()));
        if(!cliente.getTipoCliente().puedeAgregarPrenda(guardarropa))
            throw new GuardarropaLimitException(HttpStatus.NOT_FOUND,guardarropa.getDescripcion());

        Prenda p = prendaGuardarroparepository.addPrendaToGuardarropa(guardarropa, request.getPrenda());
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("message", "Se ha añadido la prenda con exito");
        resp.put("idPrenda", p.getId());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "deletePrenda" ,  method = RequestMethod.POST)
    public ResponseEntity deletePrenda(@RequestBody DeletePrendaRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        cliente.getGuardarropa(body.getIdGuardarropa()).deletePrenda(body.getIdPrenda());
        clientesRepository.save(cliente);
//        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());
//        prendaGuardarroparepository.eleminiarPrendaDelGuardarropa(guardarropa, body.getIdPrenda());

        return new ResponseEntity<>("Prenda eliminada con exito", HttpStatus.OK);
    }

    @PostMapping(value = "uploadFoto")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/prendas/fotos/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.ok(fileDownloadUri);
    }

    @RequestMapping(value = "/fotos/{file_name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download( @PathVariable("file_name") String fileName) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(fileBasePath + fileName);
        File file = new File(path.toUri());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers;
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }




}
