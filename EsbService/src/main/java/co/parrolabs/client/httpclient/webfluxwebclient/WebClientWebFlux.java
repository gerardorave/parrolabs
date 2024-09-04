package co.parrolabs.client.httpclient.webfluxwebclient;

import co.parrolabs.client.httpclient.webfluxwebclient.model.CustomerWithOrderDto;
import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.MILLIS;

public class WebClientWebFlux {
    @Value("${service-order-customer.url}")
    private String orderCustomerUrl;

    private final WebClient webClient;

    public WebClientWebFlux( WebClient.Builder client) {
        webClient = client.build();
    }

    /*



-----cHange patH url
 @Bean
public UndertowReactiveWebServerFactory undertowReactiveWebServerFactory(
        @Value("${server.servlet.context-path}") String contextPath) {
    return new UndertowReactiveWebServerFactory() {
        @Override
        public WebServer getWebServer(HttpHandler httpHandler) {
            Map<String, HttpHandler> handlerMap = new HashMap<>();
            handlerMap.put(contextPath, httpHandler);
            return super.getWebServer(new ContextPathCompositeHandler(handlerMap));
        }
    };
}
     */
    @PostConstruct
    public void configureWebClient() {
        webClient.get().uri(orderCustomerUrl);
    }

    public Mono<OrderCustomerDto> getOrderCustomerById(final UUID id) {
        final String url = "/orderCustomer/{id}";
        return webClient.get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(OrderCustomerDto.class);
    }

    public Flux<OrderCustomerDto> fetchOrdersByCustomer(List<UUID> orderCustomerIds) {
        return Flux.fromIterable(orderCustomerIds)
                .flatMap(this::getOrderCustomerById);
    }

    public Mono<CustomerWithOrderDto> fetchCustomerAndOrder(UUID customerId, UUID orderId) {
        Mono<CustomerDto> customerDto = getCustomer(customerId);
        Mono<OrderCustomerDto> orderCustomerDto = getOrderCustomerById(orderId);

        return Mono.zip(customerDto, orderCustomerDto, CustomerWithOrderDto::new);
    }

    public Mono<CustomerDto> getCustomer(final UUID id) {
        final String url = "/customer/{id}";
        return webClient.get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(CustomerDto.class);
    }

    //public OrderCustomerDto saveOrdersCustomer(final OrderCustomerRequest orderCustomerRequest) {
    public Mono<OrderCustomerDto> saveOrdersCustomer(final OrderCustomerRequest orderCustomerRequest) {
        final String url ="/orderCustomer";

        return webClient.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                //.bodyValue(BodyInserters.fromValue(orderCustomerRequest))
                .body(Mono.just(orderCustomerRequest), OrderCustomerRequest.class)
                .retrieve()
                //.toBodilessEntity() si no retorna nada
                .bodyToMono(OrderCustomerDto.class);

    }

    public Mono<OrderCustomerDto> saveOrdersCustomerCallingClient(final OrderCustomerRequest orderCustomerRequest) {

        AtomicReference<Mono<OrderCustomerDto>> monoOrderCustomerDto = new AtomicReference<>(Mono.empty());
        this.saveOrdersCustomer(orderCustomerRequest)
                .subscribe(
                        orderCustomer -> {
                            // Handle the successful response
                            System.out.println("OrderCustomer created: " + orderCustomer);
                            monoOrderCustomerDto.set(Mono.justOrEmpty(orderCustomer));
                        },
                        error -> {
                            // Handle errors
                            System.err.println("Error creating orderCustomer: " + error.getMessage());
                        }
                );
        return monoOrderCustomerDto.get();
    }


    public Flux<List<OrderCustomerDto>> fetchUserAndOtherUser(final UUID customerId) {
        final String url = "/orderCustomer/allActiveOrdersByCustomer/{customerId}";
        return webClient.get()
                .uri(url, customerId)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<List<OrderCustomerDto>>() {})     ;
    }

    public Flux<CustomerDto> fetchCustomerAndOtherCustomer(UUID id1, UUID id2) {
        return Flux.merge(getCustomer(id1), getCustomer(id2));
    }

    public Flux<CustomerDto> fetchCustomerAndOtherCustomer2(UUID id1, UUID id2) {
        return Flux.merge(getCustomer(id1), getCustomer(id2));
    }
    public Flux<CustomerDto> fetchUsers(List<UUID> customerIds) {
        return Flux.fromIterable(customerIds)
                .flatMap(this::getCustomer);
    }
/*
---COMPONENTS
Components of Spring WebFlux
<b>Mono and Flux: Mono:</b> Represents a stream with zero or one element.
It is commonly used for asynchronous operations that produce a single result.
Flux: Represents a stream with zero to many elements. It is suitable
for handling asynchronous operations that produce multiple results.
<b>Handler Functions:</b> In WebFlux, handlers are functions that handle incoming requests
and produce responses. They operate on reactive types like Mono and Flux.
<b>Router Functions:</b> Router functions define how requests are routed to handler
functions based on certain criteria. They provide a flexible and functional way to
define the routing configuration.


Advantages of Spring WebFlux
Reactive Nature  : Enables the development of highly scalable and responsive
applications by handling a large number of concurrent connections.
Non-blocking I/O  : Uses non-blocking I/O to handle many requests efficiently
with fewer threads, improving resource usage.
Backpressure Support  : Reactive Streams support backpressure, allowing applications
to handle bursts of data without overwhelming downstream components.
Functional Style  : Embraces a functional programming style with immutable
 data structures, making it well-suited for modern, declarative programming paradigms.

    The key components are Publishers, Subscribers, and Processors.
 */
    //Example of backpreassure implementation
    public Flux<List<OrderCustomerDto>> fetchUserAndOtherUserSubscriber(final UUID customerId, int limit) {
        //Process 10 to 10 qith limit max related to Back pressure
        int max = 10;
        int cont = limit%max>0?limit%max+1:limit%max;
        this.fetchUserAndOtherUser(customerId).subscribe(
                System.out::println,
                err-> err.printStackTrace(),
                ()->  System.out.println("OrderCustomer created: "),
                subscription -> {
                    for(int i=0;i<cont;i++){
                        subscription.request(max);
                    }
                }
        );
        return null;
    }
    /*********************************Processors **************************************/
    /*
    DirectProcessor connects a processor to a subscriber, and then directly invokes
    the Subscriber.onNext() method. The processor does not offer any
    backpressure handling.
    can have multiple consumers, and supports multiple producers. However,
    all producers must produce messages on the same Thread.
     */
    public static void directProcessor(){
        DirectProcessor<Long> data = DirectProcessor.create();
        data.subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Finished 1"));
        data.onNext(10L);
        data.onComplete();
        data.subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Finished 2"));
        data.onNext(12L);



    }

    /*
        UnicastProcessor has the backpressure functionality. Internally,
        it creates a queue to hold undelivered events. We can provide an optional
        queue to buffer the events. After the buffer is full, the processor starts
        to reject elements. The processor also makes it possible to perform cleanup for
        every rejected element.
        UnicastProcessor allows multiplexing of the events which means that it supports
        multiple producers

        UnicastProcessor accepts only one subscriber that will work with it.
        If we add one more subscriber to UnicastProcessor, program will throw an exception.
     */
    public static void unicastProcessor() {
        UnicastProcessor<Long> data = UnicastProcessor.create();
        data.subscribe(t -> {
            System.out.println(t);
        });
        data.sink().next(10L);
    }

    /*
    EmitterProcessor is a processor that can be used with several subscribers.
    Multiple subscribers can ask for the next value event, based on their individual
    rate of consumption.
    The processor provides the necessary backpressure support for each subscriber.
     The processor is also capable of publishing events from an external publisher.
     It consumes an event from the injected publisher and synchronously passes it
     to the subscribers.
     */
    public static void emitterProcessor () throws InterruptedException {
        EmitterProcessor<Long> data = EmitterProcessor.create(1);
        data.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        data.subscribe(t -> System.out.println(t));
        sink.next(13L);
        sink.next(14L);
        sink.next(15L);
        EmitterProcessor<String> emitter = EmitterProcessor.create();
        FluxSink<String> sinkString= emitter.sink(FluxSink.OverflowStrategy.LATEST);
        emitter.publishOn(Schedulers.single())
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("HELLO"))
                .delayElements(Duration.of(1000, MILLIS))
                .subscribe(System.out::println);

        sinkString.next("Hello World!");
        sinkString.next("Goodbye World");
        sinkString.next("Again");
        Thread.sleep(3000);
    }

    /*
    ReplayProcessor is a special-purpose processor,
    capable of caching and replaying events to its subscribers.
    The processor also has the capability of publishing events from an external publisher.
    It consumes an event from the injected publisher and synchronously passes it to
    the subscribers.
    ReplayProcessor can cache events for some scenarios:
        All events
        A limited count of events
        Events bounded by a specified time period
        Events bounded by a count and a specified time period
        The last event only
     Once cached, all events are replayed when a subscriber is added.
     */
    public static void replayProcessor() {
        ReplayProcessor<Long> data = ReplayProcessor.create(3);
        data.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        sink.next(13L);
        sink.next(14L);
        data.subscribe(t -> System.out.println(t));
    }
    /*
    TopicProcessor is a processor capable of working with multiple subscribers, using
    an event loop architecture.
    The processor delivers events from a publisher to the attached subscribers in
    an asynchronous manner, and honors backpressure for each subscriber by using
    the RingBuffer data structure.
    The processor is also capable of listening to events from multiple publishers.
    Unlike the processors that deliver events in an ordered manner,
    TopicProcessor is capable of delivering events to subscribers in a concurrent manner.
    This is governed by the number of threads created in the processor.
     */
    public static void topicProcessor() {
        TopicProcessor<Long> data = TopicProcessor.<Long>builder()
                .executor(Executors.newFixedThreadPool(2)).build();
        data.subscribe(t -> System.out.println(t));
        data.subscribe(t -> System.out.println(t));
        data.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink= data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
    }
    /*
    The WorkQueueProcessor type is similar to the TopicProcessor, in that it
    can connect to multiple subscribers. However, it does not deliver all
    events to each subscriber. The demand from every subscriber is added to a
    queue, and events from a publisher are sent to any of the subscribers.
    The model is more like having listeners on a JMS queue; each listener
    consumes a message when finished. The processor delivers messages to each
    of the subscribers in a round-robin manner. The processor is also capable of
    listening to events from multiple publishers.
     */
    public static void workQueueProcessor() {
        WorkQueueProcessor<Long> data = WorkQueueProcessor.<Long>builder().build();
        data.subscribe(t -> System.out.println("1. "+t));
        data.subscribe(t -> System.out.println("2. "+t));
        data.subscribe(t -> System.out.println("3. "+t));
        FluxSink<Long> sink= data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
    }
    public static void main(String[] args) throws Exception {
        System.out.println("/*************************Direct PROCESSOR**************/");
        directProcessor();
        System.out.println("/*************************Unicast PROCESSOR**************/");
        unicastProcessor();
        System.out.println("/*************************EmitterProcessor PROCESSOR**************/");
        emitterProcessor();
        System.out.println("/*************************Replay PROCESSOR**************/");
        replayProcessor();
        System.out.println("/*************************Topic PROCESSOR**************/");
        topicProcessor();
        System.out.println("/*************************WorkQueue PROCESSOR**************/");
        workQueueProcessor();
    }

}
