const mongoose = require("mongoose");
const bcrypt = require("bcrypt");

const saltRounds = 10;

const Schema = mongoose.Schema;

const userSchema = new Schema({
    name: {type: String, required: true},
    email: { type: String, required: true },
    hash: {type: String, required: true}
});

userSchema.methods.setPassword = async function(password){
    this.hash = await bcrypt.hash(password, saltRounds);;
}

userSchema.methods.validPassword = async function(password){
    return await bcrypt.compare(password, this.hash);
}

const User = mongoose.model("User", userSchema);
module.exports = User;