#http://www.codeskulptor.org/#user43_pZhqbpJHlu_1.py

# template for "Stopwatch: The Game"
import simplegui
import math

# define global variables
time_interval = 0
width = 300
higth = 200
stop_count = 0
success_count = 0
running_status = False

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    min = str((t // 10) // 60)
    sec = str((t // 10) % 60)
    if len(sec) == 1:
        sec = '0' + sec
    else:
        sec
    tenths_of_sec = str(t % 10)
    #print t
    #print min+':'+sec+'.'+tenths_of_sec
    return min+':'+sec+'.'+tenths_of_sec
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start_handler():
    global running_status
    timer.start()
    running_status = True
    
def stop_handler():
    global stop_count,success_count,time_interval,running_status
    timer.stop()
    if running_status == True:
        stop_count += 1
        #print str(time_interval % 10)
        if str(time_interval % 10) == '0':
            success_count += 1
        
    running_status = False
    
def reset_handler():
    global stop_count,success_count,time_interval,running_status
    timer.stop()
    time_interval = 0
    stop_count = 0
    success_count = 0
    running_status = False

# define event handler for timer with 0.1 sec interval
def timer_handler():
    global time_interval
    time_interval += 1

# define draw handler
def draw_handler(canvas):
    global time_interval,width,higth,stop_count,success_count
    ti_str = format(time_interval)
    canvas.draw_text(ti_str, (width/3.0, higth/2.0), 40, 'White')
    
    score = str(success_count)+'/'+str(stop_count)
    canvas.draw_text(score, (250, 30), 20, 'Lime')
    
# create frame
frame = simplegui.create_frame('Testing', width, higth)


# register event handlers
frame.set_draw_handler(draw_handler)
button1 = frame.add_button('Start', start_handler,120)
button2 = frame.add_button('Stop', stop_handler,120)
button3 = frame.add_button('Reset', reset_handler,120)
timer = simplegui.create_timer(100, timer_handler)


# start frame
frame.start()

# Please remember to review the grading rubric

