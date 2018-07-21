#http://www.codeskulptor.org/#user43_1VDWHN0zZg_2.py

# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console

import random
import simplegui
import math

secret_number = 0
current_range = 0
remain_guess_num = 0

# helper function to start and restart the game
def new_game():
    # initialize global variables used in your code here
    global secret_number,current_range,remain_guess_num
    current_range = 100
    secret_number = random.randrange(0, 100)
    print 'New game. Range is [0,100)'
    remain_guess_num = int(math.ceil(math.log(101,2)))
    print 'Number of remaining guesses is',remain_guess_num
    #print 'sec_num :',secret_number
    print ''

# define event handlers for control panel
def range100():
    # button that changes the range to [0,100) and starts a new game 
    global secret_number,current_range,remain_guess_num
    current_range = 100
    secret_number = random.randrange(0, 100)
    print 'New game. Range is [0,100)'
    #print 'sec_num :',secret_number
    remain_guess_num = int(math.ceil(math.log(101,2)))
    print 'Number of remaining guesses is',remain_guess_num
    print ''
    

def range1000():
    # button that changes the range to [0,1000) and starts a new game     
    global secret_number,current_range,remain_guess_num
    current_range = 1000
    secret_number = random.randrange(0, 1000)
    print 'New game. Range is [0,1000)'
    #print 'sec_num :',secret_number
    remain_guess_num = int(math.ceil(math.log(1001,2)))
    print 'Number of remaining guesses is',remain_guess_num
    print ''
    
def input_guess(guess):
    global secret_number,remain_guess_num
    guess_number = int(guess)
    print 'Guess was',guess_number
    remain_guess_num -= 1
    print 'Number of remaining guesses is',remain_guess_num

    if guess_number < secret_number:
        print 'Higher!'
        print ''
    elif guess_number > secret_number:
        print 'Lower!'
        print ''
    elif guess_number == secret_number:
        print 'Correct!'
        print ''
        if current_range == 100:
            range100()
        elif current_range == 1000:
            range1000()
        else:
            print 'error in input_guess, current_range invalid!!!'
    else:
        print 'error in func input_guess!!!'
        print ''
        
    if remain_guess_num == 0:
        if current_range == 100:
            range100()
        elif current_range == 1000:
            range1000()
        else:
            print 'error in input_guess, current_range invalid!!!'


# create frame
frame = simplegui.create_frame('Guess the number', 300, 200)
button1 = frame.add_button('Range is [0,100)', range100,120)
button2 = frame.add_button('Range is [0,1000)', range1000,120)
inp = frame.add_input('Your guess number', input_guess,120)

# register event handlers for control elements and start frame
frame.start()

# call new_game 
new_game()


# always remember to check your completed program against the grading rubric
