var express = require('express');
const auth = require("../controllers/auth");
var router = express.Router();


router.get("/login", auth.showLogin);
router.get("/register", auth.showRegister);

router.post('/login', ...auth.loginUser);

router.get("/logout", function (req, res) {
  req.logout();
  res.redirect("/");
})

router.post("/register", auth.registerUser);
module.exports = router;
