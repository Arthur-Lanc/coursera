# http://www.codeskulptor.org/#user43_fAwSFN88Y7_0.py

# implementation of card game - Memory

import simplegui
import random

# helper function to initialize globals
def new_game():
    global list1,exposed,state,turns,pre_index1,pre_index2
    list1 = range(0, 8)
    list2 = range(0, 8)
    list1.extend(list2)
    random.shuffle(list1)
    exposed = []
    for i in range(16):
        exposed.append(False) 
    state = 0
    turns = 0
    pre_index1 = 0
    pre_index2 = 0
     
# define event handlers
def mouseclick(pos):
    global list1,exposed,state,turns,pre_index1,pre_index2
    index = pos[0]//50
    if state == 0:
        if exposed[index] == False:
            exposed[index] = True
            state = 1
            turns += 1
            pre_index1 = index
    elif state == 1:
        if exposed[index] == False:
            exposed[index] = True
            state = 2
            pre_index2 = index
    else:
        if exposed[index] == False:
            exposed[index] = True
            state = 1
            turns += 1
            if list1[pre_index1] != list1[pre_index2]:
                exposed[pre_index1] = False
                exposed[pre_index2] = False  
            pre_index1 = index
                      
# cards are logically 50x100 pixels in size    
def draw(canvas):
    global list1,exposed,turns
    for i in range(16):
        if exposed[i] == True:
            canvas.draw_text(str(list1[i]), (15+i*50, 60), 35, 'White')
        else:
            point1 = [0,0]
            canvas.draw_polygon([[point1[0]+i*50,point1[1]],
                                 [point1[0]+50+i*50,point1[1]],
                                 [point1[0]+50+i*50,point1[1]+100],
                                 [point1[0]+i*50,point1[1]+100]
                                ],
                                1, 'Red', 'Green')
    new_label = 'Turns = '+str(turns)        
    label.set_text(new_label)


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()


# Always remember to review the grading rubric