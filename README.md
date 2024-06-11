# Paper Traders
This project was created to mimic modern crypto trading platforms without using money. You can bet on live cryptocurrency without spending a dollar.

# The Logic
Once you are logged in, you can open a position, either long or short, on the currently selected coin. This position will create an element in my SQL server that my Spring Boot application will manage. If the position's stop loss or take profit is met, the app will close that position and move it to a closed position table. Upon request, you can see all of your live positions and closed positions.

# Users
For user security, I implemented JWT tokens as the way to authenticate requests. Although this is somewhat overkill for the scale of the project, I did it as a challenge to make it work within the constraints of my system. I also encrypted the passwords on the SQL server to ensure they could never be stolen, not that it's a valuable target. However, after completing the full security of the project, I personally learned far more about how internet security works than I would have learned from reading 10+ books on the topic.

# Conclusion
This project is solid, and I have learned so much valuable information about how a full-stack app works. If you want a quick peek at the project, check out the controller directory to find the most important classes and structure.
