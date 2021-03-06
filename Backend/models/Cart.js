const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const cartSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  productId: { type: Schema.Types.ObjectId, ref: "Product" },
  restaurantId: { type: Schema.Types.ObjectId, ref: "Restaurants" },
  quantity: { type: Number },
});

module.exports = Cart = mongoose.model("Cart", cartSchema);
