var express = require('express');
var router = express.Router();
const homeController = require('../controllers/main');
const studentController = require('../controllers/student-handler');
const auth= require("connect-ensure-login");

router.get('/', homeController.index);
router.get('/students', studentController.students);

router.post('/add-student-grade',auth.ensureLoggedIn("/auth/login"), studentController.addStudentGrade);
router.delete('/students/:id', studentController.deleteStudent);

module.exports = router;
