require("dotenv").config()

const _= require('lodash');
const express = require('express');
const http = require("http");
const app = express();
const server = http.createServer(app)

server.listen(3000);
app.set('view engine', 'ejs');


app.get('/', (req,res) => {
  res.render('home')
})

app.get('/about', (req,res) => {
  res.render('about')
})

app.get('/forumpage', (req,res) => {
  res.render('forumpage')
})

app.get('/login', (req,res) => {
  res.render('login')
})

app.get('/signup', (req,res) => {
  res.render('signup')
})

app.get('/createthread', (req,res) => {
  res.render('createthread')
})

app.get('/account', (req,res) => {
  res.render('account')
})


app.use((req, res) => {
  res.status(404).render('404')
})


/* 
app.use(express.json())
app.use(express.urlencoded({ extended: true }))

//Database connection
require("./db").connect()
    .then((_) => {
        console.log("Connected to database!")
    })
    .catch((error) => {
        console.log("Could not connect to database, error: " + error)
    })

//API Requests
app.use("/user", require("./view/user.view"))

server.listen(port, () => {
    console.log("Server is running on port: " + port)
})

*/

