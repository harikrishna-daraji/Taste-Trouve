const router = require("express").Router();

const Product = require("../models/Product");
const Category = require("../models/Category");
const SubCategory = require("../models/SubCategory");

router.post("/add", async (req, res) => {
  try {
    const {
      restaurantId,
      categoryId,
      subCategoryId,
      name,
      image,
      price,
      description,
      calories,
      quantity,
      kidSection,
      popular,
      DeliveryTime,
    } = req.body;

    const categoryObject = await Category.find({ name: categoryId });
    const CategoryObjectId = categoryObject._id;

    const subcategoryObject = await SubCategory.find({ name: subCategoryId });
    const subCategoryObjectId = subcategoryObject._id;

    const newProduct = new Product({
      restaurantId,
      CategoryObjectId,
      subCategoryObjectId,
      name,
      image,
      price,
      description,
      calories,
      quantity,
      kidSection,
      popular,
      DeliveryTime,
    });

    const savedProduct = await newProduct.save();
    res.json(savedProduct);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getProducts", async (req, res) => {
  const { restaurantId, categoryId, subCategoryId } = req.body;

  const product = await Product.find({
    restaurantId,
    categoryId,
    subCategoryId,
  });
  res.json(product);
});

module.exports = router;
