/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.JsonObject;
import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class which handles inserting test data.
 *
 * @author Alexander
 */
@Singleton
@Startup
public class TestDataStorage {

    @Inject
    private CarPositionDao carPositionDao;

    @Inject
    private CartrackerDao cartrackerDao;

    @Inject
    private RoadDao roadDao;

    /**
     * Save testdata.
     */
    @PostConstruct
    public void start() {
        try {
            String testCartracker = "PT123456789";
            if (this.cartrackerDao.find(testCartracker) == null) {
                // Insert test data
                Cartracker cartracker = new Cartracker(testCartracker);
                this.cartrackerDao.create(cartracker);
                Road road = new Road("A1", RoadType.A);
                Road road2 = new Road("B1", RoadType.B);
                Road road3 = new Road("C1", RoadType.C);
                Road road4 = new Road("D1", RoadType.D);
                Road road5 = new Road("E1", RoadType.E);
                Road road6 = new Road(
                        "Foreign Country Road",
                        RoadType.FOREIGN_COUNTRY_ROAD);
                this.roadDao.create(road);
                this.roadDao.create(road2);
                this.roadDao.create(road3);
                this.roadDao.create(road4);
                this.roadDao.create(road5);
                this.roadDao.create(road6);
            }
            List<CarPosition> cps = this.carPositionDao.getAll();
            Client client = ClientBuilder.newClient();

            for (CarPosition cp : cps) {
                // Get response
                Response response = client.target("http://nominatim.openstreetmap.org/reverse")
                        .queryParam("format", "json")
                        .queryParam("lat", cp.getCoordinate().getLat())
                        .queryParam("lon", cp.getCoordinate().getLng())
                        .queryParam("zoom", 18)
                        .queryParam("addressdetails", 10)
                        .request()
                        .get();

                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    throw new RuntimeException("Request not accepted: "
                            + response.getStatus());
                }

                String jsonString = response.readEntity(String.class);
                JSONObject json = new JSONObject(jsonString);
                String road = json.getJSONObject("address").getString("road");
                System.out.println("lat: " + cp.getCoordinate().getLat() + ", lon: " + cp.getCoordinate().getLng() + ", road: " + road);
            }
        } catch (JSONException ex) {
            Logger.getLogger(TestDataStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
