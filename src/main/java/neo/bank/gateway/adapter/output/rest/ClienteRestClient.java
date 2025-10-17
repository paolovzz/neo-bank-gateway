package neo.bank.gateway.adapter.output.rest;


import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoEmailRequest;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoResidenzaRequest;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoTelefonoRequest;

@Path("/clienti")
@RegisterRestClient(configKey = "cliente-service")
public interface ClienteRestClient {

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperaDatiCliente(@PathParam(value = "username") String username);

    @GET
    @Path("/{username}/iban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperaListaIbanCliente(@PathParam(value = "username") String username);


    @Path("/{username}/residenza")
    @Tag(name="Endpoints Clienti")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaResidenza(@PathParam(value = "username") String username,  RichiediAggiornamentoResidenzaRequest req);

    @Path("/{username}/telefono")
    @Tag(name="Endpoints Clienti")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaTelefono(@PathParam(value = "username") String username,  RichiediAggiornamentoTelefonoRequest req);

    @Path("/{username}/email")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Tag(name="Endpoints Clienti")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaEmail(@PathParam(value = "username") String username,  RichiediAggiornamentoEmailRequest req);
    

}
