//package org.example.citycab.controllers;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import org.example.citycab.entities.Vehicle;
//
//import java.util.Arrays;
//import java.util.List;
//@Path("/vehicles")
//public class VehicleController {
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getVehicles() {
//        List<Vehicle> vehicles = Arrays.asList(
//                new Vehicle("Economye Taxi", "Up to 3 Passengers", "economy.png"),
//                new Vehicle("Standard Class", "Up to 3 Passengers", "standerd.png"),
//                new Vehicle("First Class", "Up to 3 Passengers", "first.png")
////                new Vehicle("SUV", "Up to 6 Passengers", "passanger1.png"),
////                new Vehicle("Van First Class", "Up to 7 Passengers", "1stclass.png"),
////                new Vehicle("Van Standard", "Up to 12 Passengers", "standerd van1.png"),
////                new Vehicle("Minibus", "Up to 16 Passengers", "minibus.png")
//        );
//
//        return Response.ok(vehicles)
//                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
//                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
//                .build();
//    }
//}
