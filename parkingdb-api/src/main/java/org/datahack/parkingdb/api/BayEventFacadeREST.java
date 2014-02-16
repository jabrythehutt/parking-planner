/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.datahack.parkingdb.api;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.datahack.parkingdb.BayEvent;
import org.datahack.parkingdb.ParkingDBUtils;

/**
 *
 * @author djabry
 */
@Stateless
@Path("bayevent")
public class BayEventFacadeREST extends AbstractFacade<BayEvent> {
    //@PersistenceContext(unitName = "PARKING_PU")
    private EntityManager em=ParkingDBUtils.getEntityManager();

    public BayEventFacadeREST() {
        super(BayEvent.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(BayEvent entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, BayEvent entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public BayEvent find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Path("nearest/{bayId}/{t}")
    @Produces({"application/xml", "application/json"})
    public BayEvent findNearest(@PathParam("id") Integer bayId,@PathParam("t") String tString) {
        
        String qString = "SELECT * FROM BAYEVENT b WHERE b.BAY_ID="+bayId+" AND b.EVENTTIME<="+tString+ " ORDER BY EVENTTIME DESC LIMIT 1";
        TypedQuery<BayEvent> q = em.createQuery(qString,BayEvent.class);
        
        return q.getSingleResult();
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<BayEvent> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<BayEvent> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    
}
