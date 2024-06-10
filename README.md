# Paper Traders

This project was made to mimic modern crypto trading platforms but without using money so you could bet on live crytpo currecny and not spend a dollar


# The Logic

Once you are logged in you can open a position either long or short the currnt selected coin. This position will create a elemnt in my sql server that my
spring boot will manage. if the positon's stoploss or takeprofit or etc is met, the app will close that position and move it to a closed position catagory.
uopn request you can see all of you live positions and closed positions. 


# Users

for the users and secruity i implemnted jwt tockens as the way to authintace request alltho this is kinda over kill for the scale of the project i did it
as a challange to make it work with the contrants of my system. i also encrypted the pass words on the sql server to enshure it could never be stolen, not
that its a valueble target. but after comepleting the full secuirty of the project i personaly learn wayy more about how internet secruity works than i would
have learn reading 10+ books on it.


# Conculsetion 

this project IS messy but ive learned so much valueble information about how a full stack app works. if you want a quick peek at the project check out the 
controller dir to find the most important classes and structor. 
