const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Category = require("../models/Category");
const SubCategory = require("../models/SubCategory");
const User = require("../models/userModels");
const Favourite = require("../models/favourite");

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
      specialOffer,
      specialType,
    } = req.body;

    const categoryObject = await Category.find({ name: categoryId });

    const CategoryObjectId = categoryObject[0]._id;
    const subcategoryObject = await SubCategory.find({ name: subCategoryId });
    const subCategoryObjectId = subcategoryObject[0]._id;

    if (kidSection == "true") {
      var payload = {
        data: {
          title: "New Product Added",
          message: name + " has been added in the kids section",
        },
      };

      const user = await User.find({ isDriver: false }).select("fcmToken");

      let fcmArray = user.map(function (element) {
        return element.fcmToken;
      });

      admin.messaging().sendToDevice(fcmArray, payload);
    }

    if (popular == "true") {
      var payload = {
        data: {
          title: "New Product Added",
          message: name + " has been added in the popular section",
        },
      };

      const user = await User.find({ isDriver: false }).select("fcmToken");

      let fcmArray = user.map(function (element) {
        return element.fcmToken;
      });

      admin.messaging().sendToDevice(fcmArray, payload);
    }

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
      DeliveryTime: DeliveryTime.toString(),
      specialOffer,
      specialType,
    });

    const savedProduct = await newProduct.save();

    res.json(savedProduct);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getProducts", async (req, res) => {
  const { restaurantId, categoryId, subCategoryId, userId } = req.body;

  const product = await Product.find({
    restaurantId,
    categoryId,
    subCategoryId,
  });

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }

  res.json(product);
});

router.post("/getDrinksProducts", async (req, res) => {
  const { restaurantId, userId } = req.body;

  const product = await Product.find({
    restaurantId,
    categoryId: "60c845afb91443700feb8e6f",
  });

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }

  res.json(product);
});

router.post("/getSpecialOfferProducts", async (req, res) => {
  const { userId, specialType } = req.body;

  const product = await Product.find({
    specialOffer: true,
    specialType,
  });

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }

  res.json(product);
});

router.post("/getProductsByMainCategory", async (req, res) => {
  const { categoryId, userId } = req.body;

  const product = await Product.find({
    categoryId,
  });

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }

  res.json(product);
});

router.post("/getProductsByRestaurant", async (req, res) => {
  const { restaurantId, userId } = req.body;

  const product = await Product.find({
    restaurantId,
    visibleStatus: true,
  });

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }
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
        res.send("Updated");
      }
    }
  );
});

router.post("/get", async (req, res) => {
  const { userId } = req.body;
  const product = await Product.find();

  for (var key in product) {
    const fav = await Favourite.findOne({
      userId,
      productId: product[key]._id,
    });

    product[key] = {
      ...product[key]._doc,
      favourite: fav == null ? "false" : "true",
    };
  }
  res.json(product);
});

module.exports = router;
