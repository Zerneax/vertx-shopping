
var eb = vertx.eventBus();

var orderReadyToGo = []

eb.consumer("order.ready.to.go", function (message) {
  console.log("\n\n New order ready to go: " + message.body());
  orderReadyToGo.push(message.body());
});


vertx.setPeriodic(10000, function (id) {
  // This handler will get called every second
  if (orderReadyToGo.length != 0) {
    console.log("\n\n Order " + orderReadyToGo[0] + " has been deliver.")
    eb.send('order.delivered', orderReadyToGo.shift());
  }
});
