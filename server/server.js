const express=require('express')
const mongoose =require('mongoose');

mongoose.connect('mongodb+srv://sarathchandranm:Sarath@9747@cluster0.castp.mongodb.net/')
.then(()=> console.log("MONGO DB CONNECTED"))
.catch((error) => console.log(error));
const app=express()
const port=process.env.PORT || 5000