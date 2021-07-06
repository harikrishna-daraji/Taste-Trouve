const mongoose = require("mongoose");

const orderSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  addressId: { type: Schema.Types.ObjectId, ref: "Address" },
  delivery: { type: Number },
  orderStatus: {
    type: String,
    enum: ["pending", "accepted", "declined", "delivered", "confirmed"],
  },
  tax: { type: Number },
  total: { type: Number },
  products: [
    {
      name: { type: String },
      image: { type: String },
      price: { type: String },
    },
  ],
});

module.exports = Order = mongoose.model("Order", orderSchema);
