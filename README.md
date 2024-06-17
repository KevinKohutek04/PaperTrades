# Paper Traders
Paper Traders is a project designed to mimic modern crypto trading platforms without using real money. You can bet on live cryptocurrency without spending a dollar.

# The Logic
Once logged in, you can open a position—either long or short—on the currently selected coin. This position creates an entry in my SQL server, managed by a Spring Boot application. If the position's stop loss or take profit is met, the app will automatically close the position and move it to a closed positions table. You can view all your live and closed positions upon request.

# Users
For user security, I implemented JWT tokens to authenticate requests. Although this is somewhat overkill for the scale of the project, I took it on as a challenge to make it work within the constraints of my system. Additionally, I encrypted passwords on the SQL server to ensure they could never be stolen. Completing the full security of the project taught me more about internet security than reading 10+ books on the topic.

# Conclusion
This project is solid and has provided me with invaluable insights into the workings of a full-stack application. If you want a quick peek at the project, check out the controller directory to find the most important classes and structure.

PS. I did build a fully functional front end but, I decided that it wasnt good enogh to be posted on here so feel free to ask me for it at kohutekkevin7@gmail.com

