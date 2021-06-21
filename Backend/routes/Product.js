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

    const CategoryObjectId = categoryObject[0]._id;
    const subcategoryObject = await SubCategory.find({ name: subCategoryId });
    const subCategoryObjectId = subcategoryObject[0]._id;

    const newProduct = new Product({
      restaurantId,
      categoryId: CategoryObjectId,
      subCategoryId: subCategoryObjectId,
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

router.post("/getProductsByMainCategory", async (req, res) => {
  const { categoryId } = req.body;

  const product = await Product.find({
    categoryId,
  });
  res.json(product);
});

module.exports = router;
