const mongoose = require("mongoose");

const deliveryUserSchema = new mongoose.Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  displayname: { type: String },
  fcmToken: { type: String },
  phoneNumber: { type: String },
  dateOfBirth: { type: String },
});
module.exports = DeliveryUser = mongoose.model(
  "DeliveryUser",
  deliveryUserSchema
);
