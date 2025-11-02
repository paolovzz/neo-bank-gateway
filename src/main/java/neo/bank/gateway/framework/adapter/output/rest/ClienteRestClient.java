package neo.bank.gateway.framework.adapter.output.rest;


import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoEmailRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoResidenzaRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoTelefonoRequest;

@Path("/clienti")
@RegisterRestClient(configKey = "cliente-service")
public interface ClienteRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperaDatiCliente(@HeaderParam("X-Authenticated-User") String authenticatedUser);

    @GET
    @Path("/iban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperaListaIbanCliente(@HeaderParam("X-Authenticated-User") String authenticatedUser);


    @Path("/residenza")
    @Tag(name="Endpoints Clienti")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaResidenza( @HeaderParam("X-Authenticated-User") String authenticatedUser,  RichiediAggiornamentoResidenzaRequest req);

    @Path("/telefono")
    @Tag(name="Endpoints Clienti")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaTelefono( @HeaderParam("X-Authenticated-User") String authenticatedUser,  RichiediAggiornamentoTelefonoRequest req);

    @Path("/email")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Tag(name="Endpoints Clienti")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaEmail( @HeaderParam("X-Authenticated-User") String authenticatedUser,  RichiediAggiornamentoEmailRequest req);
    

}
