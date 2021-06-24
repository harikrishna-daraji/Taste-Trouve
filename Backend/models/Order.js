const mongoose = require("mongoose");

const orderSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  orderItem: { type: Schema.Types.ObjectId, ref: "OrderItem" },
  address: { type: Schema.Types.ObjectId, ref: "Address" },
  delivery: { type: Number },
  orderStatus: { type: Boolean },
  tax: { type: Number },
  total: { type: Number },
});

module.exports = Order = mongoose.model("Order", orderSchema);
