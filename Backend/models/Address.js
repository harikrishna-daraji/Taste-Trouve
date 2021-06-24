const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const addressSchema = new mongoose.Schema({
  userId: { type: Schema.Types.ObjectId, ref: "User" },
  streetName: { type: String },
  appartment: { type: String },
  postalCode: { type: Number },
});

module.exports = Address = mongoose.model("Address", addressSchema);
