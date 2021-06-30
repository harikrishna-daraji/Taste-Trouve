const router = require("express").Router();
const admin = require("firebase-admin");
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
      visibleStatus: true,
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

router.post("/getProductsByRestaurant", async (req, res) => {
  const { restaurantId } = req.body;

  const product = await Product.find({
    restaurantId,
    visibleStatus: true,
  });
  res.json(product);
});

router.put("/deleteProduct", async (req, res) => {
  let { productId } = req.body;
  Product.updateOne(
    { _id: productId },
    { visibleStatus: false },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
        res.send("Updated");
      }
    }
  );
});

router.put("/update", async (req, res) => {
  let {
    productId,
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

  Product.updateOne(
    { _id: productId },
    {
      name,
      image,
      price,
      description,
      calories,
      quantity,
      kidSection,
      popular,
      DeliveryTime,
    },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
        var payload = {
          data: {
            title: "Product Updated",
            message: "This product has been Updated",
          },
        };
        admin
          .messaging()
          .sendToDevice(
            "fCcVB03BQfW6oQvz-NkC8F:APA91bFIfTmzIwzvct2rEp5PQAclGmF1By3FBDdtq4rssLPVwEp-LOtLrVIStWjcJgX6LPlC2y-tfjgQx37iDD08l0hmFaWCWr_Wp8ggLzerHWJ5MlTgqLbHr4BvLFwVpCLJBbKI82CZ",
            payload
          );
        res.send("Updated");
      }
    }
  );
});

router.get("/get", async (req, res) => {
  const product = await Product.find();
  res.json(product);
});

module.exports = router;
