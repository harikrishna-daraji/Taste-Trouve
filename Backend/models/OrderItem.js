const mongoose = require("mongoose");

const orderItemSchema = new mongoose.Schema({
  orderId: { type: Schema.Types.ObjectId, ref: "Order" },
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  productId: { type: Schema.Types.ObjectId, ref: "Product" },
  quantity: { type: Number },
});

module.exports = OrderItem = mongoose.model("OrderItem", orderItemSchema);
