const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const trackOrder = new mongoose.Schema(
  {
    deliveryUser: { type: Schema.Types.ObjectId, ref: "DeliveryUser" },
    orderId: { type: Schema.Types.ObjectId, ref: "Order" },
    restroId: { type: Schema.Types.ObjectId, ref: "Restaurants" },
    status: {
      type: String,
      enum: [
        "accepted",
        "declined",
        "assigned",
        "accepted by driver",
        "rejected by driver",
        "delivered",
        "confirmed",
      ],
    },
  },
  { timestamps: true }
);

module.exports = TrackOrder = mongoose.model("TrackOrder", trackOrder);
