const _= require('lodash');
const express = require('express');

const app = express();

app.listen(3000);
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

