package models.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RandomRoutingLogic;
import akka.routing.Router;
import models.domain.Car;
import models.domain.CoachWork;
import models.domain.CountRequest;
import models.domain.CountResponse;
import models.domain.Engine;
import models.domain.Wheel;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;


public class AssembleCarActor extends UntypedActor {
    Router router;
    {

        ActorRef r1 = getContext().actorFor("akka://carfactory/user/paintBlueActor");
        ActorRef r2 = getContext().actorFor("akka://carfactory/user/paintGreenActor");
        ActorRef r3 = getContext().actorFor("akka://carfactory/user/paintRedActor");
        router = new Router(new RandomRoutingLogic(), Arrays.asList(new ActorRefRoutee(r1), new ActorRefRoutee(r2), new ActorRefRoutee(r3)));
    }

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    private AtomicLong assembledCars = new AtomicLong();
    private BlockingQueue<Wheel> wheels = new ArrayBlockingQueue<Wheel>(10000);
    private BlockingQueue<Engine> engines = new ArrayBlockingQueue<Engine>(10000);
    private BlockingQueue<CoachWork> coachWorks = new ArrayBlockingQueue<CoachWork>(10000);
    private Long serialNumber = 1L;


    @Override
    public void onReceive(Object message) throws Exception {
        LOG.info("Car Assembler");
        if(message instanceof Wheel){
            LOG.info("Received wheel current count {} wheels, {} coachworks and {} engines", wheels.size(), coachWorks.size(), engines.size());
            wheels.add((Wheel)message);
            assemble();
        }
        else if(message instanceof Engine){
            LOG.info("Received engine current count {} wheels, {} coachworks and {} engines", wheels.size(), coachWorks.size(), engines.size());
            engines.add((Engine)message);
            assemble();
        }
        else if(message instanceof CoachWork){
            LOG.info("Received coachwork current count {} wheels, {} coachworks and {} engines", wheels.size(), coachWorks.size(), engines.size());
            coachWorks.add((CoachWork)message);
            assemble();
        }
        else if(message instanceof CountRequest) {
            getContext().sender().tell(new CountResponse(assembledCars.get()), getContext().self());
        }
    }

    private void assemble() throws InterruptedException {
        if(wheels.size() >= 4 && engines.size() >= 1 && coachWorks.size() >= 1) {
            Car car = new Car(serialNumber++, engines.take(), coachWorks.take(), Arrays.asList(wheels.take(), wheels.take(), wheels.take(), wheels.take()));
            router.route(car, ActorRef.noSender());
            assembledCars.incrementAndGet();
        }
    }
}