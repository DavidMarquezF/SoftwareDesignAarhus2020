const User = require("../models/user");
const passport = require('passport');

module.exports.showLogin = function (req, res, next) {
    res.render('login', { layout: "empty_layout", title: "Login" });
}


module.exports.login = async function (req, res, next) {
    let user;
    try {

        user = await User.findOne({ email: req.body.email }).exec();
        if (!user) {
            return res.status(400).json({ "message": "User doesn't exist" });
        }
        const valid = await user.validPassword(req.body.password);
        if (!valid) {
            return res.status(400).message({ "message": "Incorrect password" });
        }
        return res.status(200).json({ "token": user.generateJwt() });
    }
    catch (err) {
        console.error(err)
        return res.status(500).json({ "message": "Internal error" })
    }
}
function showRegister(res, extraParams) {
    res.render('register', { layout: "empty_layout", title: "Register", ...extraParams });
}

module.exports.showRegister = function (req, res, next) {
    showRegister(res, {});
}

module.exports.loginUser = [passport.authenticate("local", { failureRedirect: "/auth/login" }),
function (req, res) {
    res.redirect("/");
}];

module.exports.registerUser = async function (req, res, next) {
    if (!req.body.name || !req.body.email || !req.body.password) {
        return res.status(400)
            .json({ "message": "Required fields" })
    }
    try {
        const user = new User({ name: req.body.name, email: req.body.email });
        const passwordPlain = req.body.password;
        await user.setPassword(passwordPlain);
        await user.save();
        return res.status(200)
            .send(user);
    }
    catch (err) {
        return res.
            status(500)
            .json({
                "name": err.name,
                "message": "Internal error registering user"
            });
    }
}