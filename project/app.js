require("dotenv").config()

const _= require('lodash');
const flash = require("connect-flash");
const express = require('express');
const http = require("http");
const session = require("express-session");

const app = express();
const PORT = process.env.PORT || 3000;


//Database connection
require("./db").connect();

//App configuration
app.use(express.json())
app.use(express.urlencoded({ extended: true }))

app.set('view engine', 'ejs');

app.use(session({
  secret: 'keyboard cat',
  saveUninitialized: true,
  resave: true
}))
app.use(flash());

//Routes
app.use("/auth", require("./routes/auth.routes"));
app.use("",  require("./routes/home.routes"));
app.use("/posts",  require("./routes/posts.routes"));
app.use("",  require("./routes/account.routes"));
app.use("",  require("./routes/about.routes"));


app.use((req, res) => {
  res.status(404).render('404')
})

const server = http.createServer(app)

server.listen(PORT, () => {
  console.log("Server is running on port: " + PORT);
});




