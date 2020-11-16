require('dotenv').config();
var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var session = require('express-session');
var logger = require('morgan');
var expressLayouts = require('express-ejs-layouts');
var cors = require('cors')
const compression=require("compression");

const _app_folder='../StudentsApp/dist/StudentsApp';

require("./models/db");

var indexRouter = require('./routes/index');
var authRouter = require('./routes/auth');
var apiRouter = require("./routes/api");

var app = express();

app.use(cors())

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(compression());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(session({
  secret: 'keyboard cat',
  resave: false,
  saveUninitialized: true,
}));
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(_app_folder)));

app.use(expressLayouts);



app.use('/api', apiRouter);
app.get("*", (req, res) => {
  res.sendFile( path.join(_app_folder,"index.html"))
})
// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handlers
// Catch unauthorised errors
app.use(function(err, req, res, next) {
  if(err.name=== 'UnauthorizedError') {
    res.status(401);
    res.json({"message":err.name+ ": "+ err.message});
  }
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
