package spark;

import static spark.Spark.*;

public class AppMainTest {

    public static void main(String[] args) {
        get("/test/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                System.out.println("test");
                return "test";
            }
        });

        path("/v1", () -> {
            get("/wallet/:id", new Route() {
                @Override
                public Object handle(Request request, Response response) throws Exception {
                    return "wallet "+ request.pathName();
                }
            });

            get("/user/:id/find", new Route() {
                @Override
                public Object handle(Request request, Response response) throws Exception {
                    return "user " + request.pathName();
                }
            });

        });

        before(new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                System.out.println("before request path "+ request.pathName());
            }
        });
/*
        after(new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                System.out.println("after request path "+ request.pathName());
            }
        });
*/
    }



}
