const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const productSchema = new mongoose.Schema({
  restaurantId: { type: Schema.Types.ObjectId, ref: "Restaurants" },
  categoryId: { type: Schema.Types.ObjectId, ref: "Category" },
  subCategoryId: { type: Schema.Types.ObjectId, ref: "SubCategory" },

  name: { type: String, required: true },
  image: { type: String },
  price: { type: Number },
  description: { type: String },
  calories: { type: String },
  quantity: { type: Number },
  kidSection: { type: Boolean },
  popular: { type: Boolean },
  visibleStatus: { type: Boolean },
  DeliveryTime: { type: String },
  specialOffer: { type: Boolean },
  specialType: { type: String },
});

module.exports = Product = mongoose.model("Product", productSchema);
