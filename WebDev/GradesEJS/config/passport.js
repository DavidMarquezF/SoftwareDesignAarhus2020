const passport = require("passport");
const LocalStrategy = require('passport-local').Strategy;
const User = require("../models/user");

passport.serializeUser(function (user, cb) {
    cb(null, user._id);
});

passport.deserializeUser(function (id, cb) {
    console.log("Deserializing")

    User.findById(id, function (err, user) {
        console.log("Deserializing")
        console.log(user)
        if (err) return cb(err);
        cb(null, user);
    })
});

passport.use(new LocalStrategy({
    usernameField: 'email'
},
    async function (username, password, done) {
        let user;
        try {
            user = await User.findOne({ email: username }).exec();
            if (!user) {
                return done(null, false, { message: 'Incorrectusername.' });
            }
        }
        catch (err) {
            return done(err);
        }
        const valid = await user.validPassword(password);
        if (!valid) {
            return done(null, false, { message: 'Incorrectpassword.' });
        }
        return done(null, user);
    })
);

