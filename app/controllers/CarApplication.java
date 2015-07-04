package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import models.CarFactory;
import models.Person;
import models.domain.CountRequest;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;

import static play.libs.Json.toJson;

public class CarApplication extends Controller {

    ActorSystem system = null;

    public Result index() throws Exception {
        if(system == null) {
            system = CarFactory.startCarFactory();
        }
        ActorRef actor = system.actorFor("akka://carfactory/user/dataFetcher");
        Timeout timeout = new Timeout(Duration.create(5000, "seconds"));
        Future<Object> ask = Patterns.ask(actor, new CountRequest(), timeout);
        Object result = Await.result(ask, timeout.duration());
        return ok(result.toString());
    }


}
