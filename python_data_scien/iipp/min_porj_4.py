#http://www.codeskulptor.org/#user43_IqvFLyCUVz_0.py

# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400       
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True
ball_pos = []
ball_vel = []
paddle1_vel = 0
paddle2_vel = 0
p1_a=[0,HEIGHT/2.0-HALF_PAD_HEIGHT]
p1_b=[PAD_WIDTH,HEIGHT/2.0-HALF_PAD_HEIGHT]
p1_c=[PAD_WIDTH,HEIGHT/2.0+HALF_PAD_HEIGHT]
p1_d=[0,HEIGHT/2.0+HALF_PAD_HEIGHT]
paddle1_pos = [p1_a,p1_b,p1_c,p1_d]
p2_a=[0+(WIDTH-PAD_WIDTH),HEIGHT/2.0-HALF_PAD_HEIGHT+paddle1_vel]
p2_b=[PAD_WIDTH+(WIDTH-PAD_WIDTH),HEIGHT/2.0-HALF_PAD_HEIGHT+paddle1_vel]
p2_c=[PAD_WIDTH+(WIDTH-PAD_WIDTH),HEIGHT/2.0+HALF_PAD_HEIGHT+paddle1_vel]
p2_d=[0+(WIDTH-PAD_WIDTH),HEIGHT/2.0+HALF_PAD_HEIGHT+paddle1_vel]
paddle2_pos = [p2_a,p2_b,p2_c,p2_d]
score1 = 0
score2 = 0
playtimes = 0

# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos = [WIDTH/2,HEIGHT/2]
    if direction == True:
        ball_vel = [random.randrange(120, 240)/60.0,-1*random.randrange(60, 180)/60.0]
    else:
        ball_vel = [-1*random.randrange(120, 240)/60.0,-1*random.randrange(60, 180)/60.0]


# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are numbers
    global score1, score2  # these are ints
    global playtimes
    if playtimes % 2 == 0:
        dire = RIGHT
    else:
        dire = LEFT
    spawn_ball(dire)
    playtimes+=1

def draw(canvas):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel
 
    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
        
    # update ball
    if ball_pos[0] + ball_vel[0] < PAD_WIDTH+BALL_RADIUS or ball_pos[0] + ball_vel[0] > WIDTH-PAD_WIDTH-BALL_RADIUS:
        ball_vel[0] += ball_vel[0]*0.1
        ball_vel[0] = -1*ball_vel[0]
    if ball_pos[1] + ball_vel[1] < 0+BALL_RADIUS or ball_pos[1] + ball_vel[1] > HEIGHT-BALL_RADIUS:
        ball_vel[1] = -1*ball_vel[1]
    ball_pos[0] += ball_vel[0]
    ball_pos[1] += ball_vel[1]

    # draw ball
    canvas.draw_circle(ball_pos, BALL_RADIUS, 1, 'White', 'White')
    # update paddle's vertical position, keep paddle on the screen
    if paddle1_pos[0][1] + paddle1_vel >= 0 and paddle1_pos[3][1] + paddle1_vel <= HEIGHT:
        paddle1_pos[0][1] += paddle1_vel
        paddle1_pos[1][1] += paddle1_vel
        paddle1_pos[2][1] += paddle1_vel
        paddle1_pos[3][1] += paddle1_vel
    if paddle2_pos[0][1] + paddle2_vel >= 0 and paddle2_pos[3][1] + paddle2_vel <= HEIGHT:
        paddle2_pos[0][1] += paddle2_vel
        paddle2_pos[1][1] += paddle2_vel
        paddle2_pos[2][1] += paddle2_vel
        paddle2_pos[3][1] += paddle2_vel

    # draw paddles
    canvas.draw_polygon(paddle1_pos, 1, 'White', 'White')
    canvas.draw_polygon(paddle2_pos, 1, 'White', 'White')
    
    # determine whether paddle and ball collide    
    if (ball_pos[0]+ball_vel[0]<PAD_WIDTH+BALL_RADIUS) and (ball_pos[1]<paddle1_pos[0][1]-3 or ball_pos[1]>paddle1_pos[3][1]+3):
        #print 'player2 +1'
        new_game()
        score2 += 1
    elif (ball_pos[0]+ball_vel[0]>WIDTH-PAD_WIDTH-BALL_RADIUS) and (ball_pos[1]<paddle2_pos[0][1]-3 or ball_pos[1]>paddle2_pos[3][1]+3):
        #print 'player1 +1'
        new_game()
        score1 += 1
    # tt = ''
    # if ball_pos[0]+ball_vel[0]>WIDTH-PAD_WIDTH-BALL_RADIUS:
        # tt += ' t1'
    # if ball_pos[1]<paddle1_pos[0][1]:
        # tt += ' t2'
    # if ball_pos[1]>paddle1_pos[3][1]:
        # tt += ' t3'
    # print tt
    
    # draw scores
    canvas.draw_text(str(score1), [(WIDTH/2.0)/2.0, 50], 30, 'White')
    canvas.draw_text(str(score2), [((WIDTH/2.0)/2.0)+(WIDTH/2.0), 50], 30, 'White')
    
def keydown(key):
    global paddle1_vel, paddle2_vel
    speed = 7
    if key==simplegui.KEY_MAP["w"]:
        paddle1_vel = -1*speed
    elif key==simplegui.KEY_MAP["s"]:
        paddle1_vel = 1*speed
        
    if key==simplegui.KEY_MAP["up"]:
        paddle2_vel = -1*speed
    elif key==simplegui.KEY_MAP["down"]:
        paddle2_vel = 1*speed
   
def keyup(key):
    global paddle1_vel, paddle2_vel
    if key==simplegui.KEY_MAP["w"]:
        paddle1_vel = 0
    elif key==simplegui.KEY_MAP["s"]:
        paddle1_vel = 0
        
    if key==simplegui.KEY_MAP["up"]:
        paddle2_vel = 0
    elif key==simplegui.KEY_MAP["down"]:
        paddle2_vel = 0
        
def restart_handler():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel,p1_a,p1_b,p1_c,p1_d,p2_a,p2_b,p2_c,p2_d  # these are numbers
    global score1, score2  # these are ints
    paddle1_pos = [p1_a,p1_b,p1_c,p1_d]
    paddle2_pos = [p2_a,p2_b,p2_c,p2_d]
    score1 = 0
    score2 = 0
    new_game()


# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
button1 = frame.add_button('Restart', restart_handler)


# start frame
new_game()
frame.start()
