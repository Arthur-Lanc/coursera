#http://www.codeskulptor.org/#user43_Evb0MEACCw_0.py

# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

# initialize some useful global variables
in_play = False
outcome = ""
score = 0

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5',
         '6', '7', '8', '9', 'T',
         'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5,
          '6':6, '7':7, '8':8, '9':9, 'T':10,
          'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, 
                          card_loc, 
                          CARD_SIZE, 
                          [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], 
                          CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        # create Hand object
        self.hand_list = []

    def __str__(self):
        # return a string representation of a hand
        ans = "Hand contains "
        for i in range(len(self.hand_list)):
            ans = ans + str(self.hand_list[i]) + ' '
        return ans

    def add_card(self, card):
        # add a card object to a hand
        self.hand_list.append(card)

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        # compute the value of the hand, see Blackjack video
        have_ace = False
        for i in range(len(self.hand_list)):
            if self.hand_list[i].get_rank() == 'A':
                have_ace = True
                break
                
        hand_value = 0
        for i in range(len(self.hand_list)):
            hand_value += VALUES[self.hand_list[i].get_rank()]
            
        if have_ace == False:
            return hand_value
        else:
            if hand_value + 10 <= 21:
                return hand_value + 10
            else:
                return hand_value
            
    def draw(self, canvas, pos):
        # draw a hand on the canvas, use the draw method for cards
        for i in range(len(self.hand_list)):
            self.hand_list[i].draw(canvas,[pos[0]+i*CARD_SIZE[0],pos[1]])
        
# define deck class 
class Deck:
    def __init__(self):
        # create a Deck object
        global SUITS,RANKS
        self.deck_list = []
        for i in range(len(SUITS)):
            for j in range(len(RANKS)):
                card = Card(SUITS[i], RANKS[j])
                self.deck_list.append(card)

    def shuffle(self):
        # shuffle the deck 
        random.shuffle(self.deck_list)

    def deal_card(self):
        # deal a card object from the deck
        return self.deck_list.pop(-1)
    
    def __str__(self):
        # return a string representing the deck
        ans = "Deck contains "
        for i in range(len(self.deck_list)):
            ans = ans + str(self.deck_list[i]) + ' '
        return ans


#define event handlers for buttons
def deal():
    global outcome, in_play,dect1,dealer_hand,player_hand,score
    if in_play == True:
        score -= 1
        
    outcome = ''
    in_play = True
    dect1 = Deck()
    dect1.shuffle()
    #print dect1
    dealer_hand = Hand()
    player_hand = Hand()
    dealer_hand.add_card(dect1.deal_card())
    dealer_hand.add_card(dect1.deal_card())
    player_hand.add_card(dect1.deal_card())
    player_hand.add_card(dect1.deal_card())
    #print 'dealer_hand :',dealer_hand
    #print 'player_hand :',player_hand

def hit():
    global outcome, in_play,dect1,dealer_hand,player_hand,score,outcome
    # if the hand is in play, hit the player
    # if busted, assign a message to outcome, update in_play and score
    #print 'in_play :',in_play
    if in_play == True:
        player_hand.add_card(dect1.deal_card())
        #print 'player_hand :',player_hand
        if player_hand.get_value() > 21:
            in_play = False
            score -= 1
            outcome = 'You have busted,you lose'
            #print 'outcome :',outcome
            #print 'score :',score

def stand():
    global outcome, in_play,dect1,dealer_hand,player_hand,score,outcome
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more
    # assign a message to outcome, update in_play and score
    if in_play == True:
        while dealer_hand.get_value() < 17:
            dealer_hand.add_card(dect1.deal_card())
        #print 'dealer_hand :',dealer_hand

        if dealer_hand.get_value() > 21:
            in_play = False
            score += 1
            outcome = 'deal have busted,you win'
            #print 'outcome :',outcome
            #print 'score :',score
        else:
            if player_hand.get_value() > dealer_hand.get_value():
                in_play = False
                score += 1
                outcome = 'you win'
                #print 'outcome :',outcome
                #print 'score :',score
            else:
                in_play = False
                score -= 1
                outcome = 'you lose'
                #print 'outcome :',outcome
                #print 'score :',score   
        #print 'in_play :',in_play
            
# draw handler    
def draw(canvas):
    global outcome, in_play,dect1,dealer_hand,player_hand,score,outcome
    # test to make sure that card.draw works, replace with your code below
    dealer_pos = [80, 200]
    player_pos = [80, 400]
    dealer_hand.draw(canvas, dealer_pos)
    player_hand.draw(canvas, player_pos)
    if in_play == True:
        prompt1 = 'Hit or stand?'
        card_loc = dealer_pos
        canvas.draw_image(card_back, 
                          CARD_BACK_CENTER, 
                          CARD_BACK_SIZE, 
                          [card_loc[0] + CARD_BACK_CENTER[0], card_loc[1] + CARD_BACK_CENTER[1]], 
                          CARD_BACK_SIZE)
    else:
        prompt1 = 'New deal?'
    canvas.draw_text(prompt1, [200,380], 20, 'Black')
    canvas.draw_text('Player', [80,380], 20, 'Black')
    canvas.draw_text(outcome, [200,180], 20, 'Black')
    canvas.draw_text('Dealer', [80,180], 20, 'Black')
    
    canvas.draw_text('Score '+str(score), [400,100], 20, 'Black')
    canvas.draw_text('Blackjack', [80,100], 30, 'Blue')
    

# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling
deal()
frame.start()


# remember to review the gradic rubric