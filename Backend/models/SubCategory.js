const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const subCategorySchema = new mongoose.Schema({
  name: { type: String, required: true },
  image: { type: String },
  categoryId: { type: Schema.Types.ObjectId, ref: "Category" },
});

module.exports = SubCategory = mongoose.model("SubCategory", subCategorySchema);
