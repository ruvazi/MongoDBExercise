# MongoDBExercise
________________________________________________
## by Rune 

Your application has to be able to answer queries corresponding to the following questions:

    How many Twitter users are in our database?
    Which Twitter users link the most to other Twitter users? (Provide the top ten.)
    Who is are the most mentioned Twitter users? (Provide the top five.)
    Who are the most active Twitter users (top ten)?
    Who are the five most grumpy (most negative tweets) and the most happy (most positive tweets)? (Provide five users for each group)

Your application can be written in the language of your choice. It must have a form of UI but it is not important if it is a CLI UI, a GUI, or a Web-based UI.

------------------------------------------------------

## Solution
The MongoDB have been set up following these two instruktions in vagrant:
- [link for exercise](https://github.com/HelgeCPH/db_course_nosql/blob/master/lecture_notes/MongoDB%20Exercise.ipynb) 
- [link for mongo intro](https://github.com/HelgeCPH/db_course_nosql/blob/master/lecture_notes/04-Intro%20to%20MongoDB.ipynb)

The solution have been made with Java RESTfull orm frame work in backend and JavaScript frontend. the solution can be run if the mongodb have been installed and the javascript in "script.js" in this solution have been run on the db.

There have been some problems with getting the data presentet in the frontend, it havent been fixed yet. 

## Answers:

1. Amount of distinct users: 659178
2. Top ten users: [lost_dog, 549, webwoke, 345, tweetpet, 310, SallytheShizzle, 281, VioletsCRUK, 279, mcraddictal, 276, tsarnick, 248, what_bugs_u, 246, Karen230683, 238, DarkPiano, 236]
3. Top ten mentioning users: [lost_dog, 549, tweetpet, 310, VioletsCRUK, 251, what_bugs_u, 246, tsarnick, 245, SallytheShizzle, 229, mcraddictal, 217, Karen230683, 216, keza34, 211, DarkPiano, 202]
4. Top five grumpiest users: [lost_dog, 549, 0, tweetpet, 310, 0, TheAmazingCat, 86, 0, nova937music, 67, 0, Nathan133, 51, 0]
5. Top five happiest users: [wowlew, 212, 8, Fanny_Ingabout, 60, 4, Haarlz, 49, 4, ggimmickgirl, 42, 4, SANCHEZJAMIE, 42, 4]
6. Top five mentioned users: [@mileycyrus, 4500.0, @tommcfly, 3886.0, @ddlovato, 3467.0, @DavidArchie, 1298.0, @Jonasbrothers, 1287.0]
