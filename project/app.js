require("dotenv").config()

const _= require('lodash');
const express = require('express');
const http = require("http");

const app = express();
const PORT = process.env.PORT || 3000;

//Database connection
require("./db").connect();

//App configuration
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.set('view engine', 'ejs');

//Routes
app.use("/auth", require("./routes/auth.routes"));

app.use((req, res) => {
  res.status(404).render('404')
})

const server = http.createServer(app)

server.listen(PORT, () => {
  console.log("Server is running on port: " + PORT);
});




