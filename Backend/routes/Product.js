const router = require("express").Router();

const Product = require("../models/Product");

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

    const newProduct = new Product({
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
    });
    const savedProduct = await newProduct.save();
    res.json(savedProduct);
  } catch (err) {
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
