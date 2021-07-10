const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const orderSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  addressId: { type: Schema.Types.ObjectId, ref: "Address" },
  restaurantId: { type: Schema.Types.ObjectId, ref: "Restaurants" },
  delivery: { type: Number },
  orderStatus: {
    type: String,
    enum: ["pending", "accepted", "declined", "delivered", "confirmed"],
  },
  tax: { type: Number },
  total: { type: Number },
  products: [
    {
      id: { type: String },
      name: { type: String },
      image: { type: String },
      price: { type: String },
      quantity: { type: String },
    },
  ],
  orderDate: { type: String },
});

module.exports = Order = mongoose.model("Order", orderSchema);
