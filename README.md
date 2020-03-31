# My Personal Project: Game

## Introduction

- **What will the application do?**  
The application is a 2D game with walls that will bounce 
some of the game objects and different types of enemies.

- **Who will use it?**  
Anyone who wants to play an offline game would play it.

- **Why is this project of interest to you?**  
I wanted to make something that would be used by people in 
real life and not just something that I make for this 
course. Also, I wanted it to not have any errors with the 
AutoTest bot we use for this course. Therefore, I decided 
to make an offline program. A game like is the most 
interesting project to me because it is the best offline
program to make with the skills I currently have in my 
opinion.

## User Stories

- As a user, I want to be able to control the player using
the keyboard.

- As a user, I want to be able to view all the objects
in the game at their position and be able to tell the
type of object by looking at it.  

- As a user, I want the player to be able to shoot a
bullet (add X to Y) facing in the direction of the mouse
click.  

- As a user, I want the game to refresh by itself many
times a second.  

- As a user, I want to be able to restart and pause the
 game while the game is running.  

- As a user, I want to be able to save the state of the game
to file.  

- As a user, I want to be to load a saved game.  

- As a user, I want to play against different types of
enemies, one of which should be smart enough to follow me.

- As a user, I want my bullets to reverse their direction
when they hit a wall.

- As a user, I want the game to have several walls that
teleport me to their base (brown dot)

## Instructions for Grader - Phase 3

Start the game by clicking on new game

- You can generate the first required event by pressing space
bar. This will shoot (add to game) a bullet.  

- You can generate the second required event by pressing
backspace. This will remove all the bullets from the game.  

- The entire canvas in which the game is being run a the
visual component.  

- You can save the state of my application by pressing escape
to pause the game and then clicking on save game.  

- You can reload the state of my application by clicking resume
when the game launches. If you click resume after you already
started the game, then it will unpause the current game.  

## Phase 4: Task 2

I have chosen to implement the type hierarchy Java language
construct in my project. The type hierarchy includes the classes
GameObject, MovingObject, Bullet, Player and Wall. Bullet and
Player are subtypes of MovingObject which is a subtype of
GameObject. So, they are also subtypes of GameObject. Wall is
also a subtype of GameObject. The method 
**public void hit(GameObject other)** is the method in GameObject
that all 3 of these classes override while some other classes use
the inherited method.

## Phase 4: Task 3

- Previously, to render the game, I passed the Graphics Context to
the render method in the Game class, which passed it to the
render method in all objects to be rendered. So, the model package
was tied to the implementation of the UI since it would only work
with JavaFX. I refactored it by creating a new GameRenderer class
in UI package and putting the render methods in there. This
improved cohesion because the model classes don't have the
responsibility of rendering. This also reduced coupling by making
the model classes independent of the UI implementation (JavaFX
or Swing).

- Previously, the Game class was handling everything related to collisions
which is not within its responsibility. Anytime, a new type of object
is added to this game, the Game would have to have code for determining
things like which object takes damage. Now, the Game class only has
to loop through all the objects to see if they collide and then what
happens when each type of Object interacts is coded in the class of
the object that collides. This increases cohesion because the Game
class has less responsibility.