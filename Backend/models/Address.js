const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const addressSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  address: { type: String },
  lat: { type: String },
  long: { type: String },
});

module.exports = Address = mongoose.model("Address", addressSchema);
