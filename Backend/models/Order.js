const mongoose = require("mongoose");

const orderSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  orderItemId: { type: Schema.Types.ObjectId, ref: "OrderItem" },
  addressId: { type: Schema.Types.ObjectId, ref: "Address" },
  delivery: { type: Number },
  orderStatus: { type: Boolean },
  tax: { type: Number },
  total: { type: Number },
});

module.exports = Order = mongoose.model("Order", orderSchema);
