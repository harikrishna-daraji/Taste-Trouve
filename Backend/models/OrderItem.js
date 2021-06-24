const mongoose = require("mongoose");

const orderItemSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  productId: { type: Schema.Types.ObjectId, ref: "Product" },
  quantity: { type: Number },
});

module.exports = OrderItem = mongoose.model("OrderItem", orderItemSchema);
