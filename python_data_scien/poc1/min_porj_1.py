#http://www.codeskulptor.org/#user43_G0rvMbABc9_4.py

"""
Merge function for 2048 game.
"""

def merge(line):
    """
    Function that merges a single row or column in 2048.
    """
    line_t1 = []
    for num in list(line):
        if num != 0:
            line_t1.append(num)
    for dummy_num in range(len(line)-len(line_t1)):
        line_t1.append(0)
    
    line_t2 = []
    idx = 0
    while True:
        if idx == len(line):
            break
        elif idx == len(line) - 1:
            line_t2.append(line_t1[idx])
            break
        
        if line_t1[idx] == line_t1[idx+1]:
            line_t2.append(line_t1[idx]*2)
            idx += 2
        else:
            line_t2.append(line_t1[idx])
            idx += 1
        

            
    for dummy_num in range(len(line)-len(line_t2)):
        line_t2.append(0)
    
    return line_t2


#line_test1 = [2,0,2,4]
#line_test2 = [0, 0, 2, 2]
#line_test3 = [2, 2, 0, 0]
#line_test4 = [2, 2, 2, 2, 2]
#line_test5 = [8, 16, 16, 8]
#print merge(line_test1)
#print merge(line_test2)
#print merge(line_test3)
#print merge(line_test4)
#print merge(line_test5)

