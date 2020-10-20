var express = require('express');

const auth = require("../controllers/auth");
const student = require("../controllers/student-handler");
var router = express.Router();

const jwt = require('express-jwt');
var jwtauth = jwt({ secret: process.env.JWT_SECRET, userProperty: 'payload', algorithms: ['HS256'] })

router.route("/users")
    .post(auth.registerUser);

router.post("/auth/login", auth.login);

router.route("/students")
    .get(student.students)
    .post(student.addStudentGrade);

module.exports = router;
