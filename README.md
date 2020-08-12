# FlappyBunny

## The funny game you shouldn't miss for killing time

A *bulleted* list:
- **simple and cute game**
- **relax and killing time**
- **for all ages**

*This desktop application is a simple game. It would be helpful for killing time and it doesn't involve complicated 
    operation, so anyone could play it. The reason for why I design this game is because, when I was a child, 
    I don't just want to play game, I also want to design game. Even though this project would be a simple small
    game, I would like to design it just for completing my childhood dream .* 


### User Stories:
- *As a user, I want to be able to have a login scene to login to this game.*
- *As a user, I want to be able to add more players account to my player manager (register account).*
- *As a user, I want to be able show all the player accounts.*
- *As a user, I want to be able to sort the players according to their records.*
- *As a user, I want to be able to know my bunny hp, velocity, positions and the obstacles's position during the game.*

- *As a user, I want to be able to save my new account to a file*
- *As a user, I want to be able to load my players account from a file*

### instruction for grader:

- *You can generate the first required event by clicking the login button (assume you have the right username and password)*
- *You can generate the second required event by clicking the register button (the username can't be already registered)*
- *You can locate my visual component by when you run the application, and more could be seen when you play the game.*
- *You can save the state of my application by clicking yes button when the game is end and asking if you want to save your game record.*
- *You can reload the state of my application by clicking view button when you login/register successful. (viewing all accounts)*

### Phase 4: Task 2

- *Include a type hierarchy in my code. The GraphicModel class is the super class of Bunny, Cactus, FloatingEnemy and 
Floor class. The three methods in GraphicModel are override in its child class in different ways.*

### Phase 4: Task 3

- *The First problem is the checkFloatingAlien method in GameModelController class, there is poor cohesion because it 
perform two actions, so I divide it to two methods, checkFloatingAlien and checkAlienOut.*
- *The Second problem is about the sound effect of the application, at first, the sound effect when a button is clicked 
is identical code that would appear is many ui class, but these codes are identical and make poor cohesion 
of these ui class, so I make another class which is SoundEffect that deals with sound effect*