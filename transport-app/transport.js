
var eb = vertx.eventBus();

var orderReadyToGo = []

eb.consumer("ready.to.go", function (message) {
  console.log("\n\n New order ready to go: " + message.body());
  orderReadyToGo.push(message.body());
});


vertx.setPeriodic(10000, function (id) {
  // This handler will get called every second
  if (orderReadyToGo.length != 0) {
    eb.send('delivered', orderReadyToGo[0]);

  }
});
