const mongoose = require("mongoose");

const productSchema = new mongoose.Schema({
  restaurantId: { type: Schema.Types.ObjectId, ref: "Restaurants" },

  name: { type: String, required: true },
  image: { type: String },
});

module.exports = Product = mongoose.model("Product", productSchema);
