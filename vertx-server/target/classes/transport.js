var eb = vertx.eventBus();

eb.consumer("event", function (message) {
  console.log("I have received a message: " + message.body());
});
