const router = require("express").Router();

const Product = require("../models/Product");
const Category = require("../models/Category");
const SubCategory = require("../models/SubCategory");

router.get("/getHomeProduct", async (req, res) => {
  try {
    const categoryObject = await Category.find();

    const kidsSection = await Product.find({ kidSection: true });
    const popular = await Product.find({ popular: true });

    const HomeData = {
      categoryObject,
      kidsSection,
      popular,
    };
    res.send(HomeData);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
