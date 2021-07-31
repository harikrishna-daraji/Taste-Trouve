const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const favouriteSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  productId: { type: Schema.Types.ObjectId, ref: "Product" },
});

module.exports = Favourite = mongoose.model("Favourite", favouriteSchema);
