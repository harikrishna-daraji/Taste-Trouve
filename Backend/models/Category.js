const mongoose = require("mongoose");

const categorySchema = new mongoose.Schema({
  name: { type: String, required: true },
  image: { type: String },
});

module.exports = Category = mongoose.model("Category", categorySchema);
