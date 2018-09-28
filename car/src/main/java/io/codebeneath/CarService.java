package io.codebeneath;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static final String MANIFEST_JARPATH = "manifest-from-jarpath";
    private static final List<Car> cars = new ArrayList<>();
    static {
        cars.add(new Car("Camaro", new Engine("V12")));
    }
    
    public static void main(String[] args) throws Exception {
        String manifestUsed = (args.length== 1) ? args[0] : MANIFEST_JARPATH;
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/cars", new CarHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("CarService started on port 8080, with endpoint /cars, pushed with manifest " + manifestUsed);
    }

    static class CarHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "The first car is: " + cars.get(0);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
