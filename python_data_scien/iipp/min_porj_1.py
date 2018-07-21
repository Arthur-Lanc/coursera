#http://www.codeskulptor.org/#user43_FNYzS4F4Vn_0.py
#http://www.codeskulptor.org/#user43_7XuqrNHFWbyQZQc.py

# Rock-paper-scissors-lizard-Spock template
import random

# The key idea of this program is to equate the strings
# "rock", "paper", "scissors", "lizard", "Spock" to numbers
# as follows:
#
# 0 - rock
# 1 - Spock
# 2 - paper
# 3 - lizard
# 4 - scissors

# helper functions
def name_to_number(name):
    # delete the following pass statement and fill in your code below

    if name == 'rock':
        return 0
    elif name == 'Spock':
        return 1
    elif name == 'paper':
        return 2
    elif name == 'lizard':
        return 3
    elif name == 'scissors':
        return 4
    else:
        print 'wrong input'


def number_to_name(number):
    # delete the following pass statement and fill in your code below
    if number == 0:
        return 'rock'
    elif number == 1:
        return 'Spock'
    elif number == 2:
        return 'paper'
    elif number == 3:
        return 'lizard'
    elif number == 4:
        return 'scissors'
    else:
        print 'wrong number'
    

def rpsls(player_choice): 
    player_number = name_to_number(player_choice)
    print 'Player chooses', player_choice
    
    comp_number = random.randrange(0, 5)
    comp_choice = number_to_name(comp_number)
    print 'Computer chooses', comp_choice
    
    if (player_number - comp_number) % 5 == 0:
        print "Player and computer tie!"
    elif (player_number - comp_number) % 5 == 1 or (player_number - comp_number) % 5 == 2:
        print "Player wins!"
    elif (player_number - comp_number) % 5 == 3 or (player_number - comp_number) % 5 == 4:
        print  "Computer wins!"
    print ''

    
# test your code - THESE CALLS MUST BE PRESENT IN YOUR SUBMITTED CODE
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")

# always remember to check your completed program against the grading rubric


