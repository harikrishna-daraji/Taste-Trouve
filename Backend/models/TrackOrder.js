const mongoose = require("mongoose");

const trackOrder = new mongoose.Schema({
  deliveryUser: { type: Schema.Types.ObjectId, ref: "DeliveryUser" },
  orderId: { type: Schema.Types.ObjectId, ref: "Order" },
  lat: { type: Number },
  long: { type: Number },
});

module.exports = TrackOrder = mongoose.model("TrackOrder", trackOrder);
