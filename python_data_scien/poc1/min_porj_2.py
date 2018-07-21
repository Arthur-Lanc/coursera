#http://www.codeskulptor.org/#user43_Z6HO4MuEFr_2.py

"""
Clone of 2048 game.
"""

import poc_2048_gui
import random

# Directions, DO NOT MODIFY
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

# Offsets for computing tile indices in each direction.
# DO NOT MODIFY this dictionary.
OFFSETS = {UP: (1, 0),
           DOWN: (-1, 0),
           LEFT: (0, 1),
           RIGHT: (0, -1)}

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

class TwentyFortyEight:
    """
    Class to run the game logic.
    """

    def __init__(self, grid_height, grid_width):
        self._grid_height = grid_height
        self._grid_width = grid_width
        self._grid = []
        self.reset()
        up_list = [(0,y) for y in range(grid_width)]
        down_list = [(grid_height-1,y) for y in range(grid_width)]
        left_list = [(x,0) for x in range(grid_height)]
        right_list = [(x,grid_width-1) for x in range(grid_height)]
        self._init_tiles_dic = {UP: up_list,
                           DOWN: down_list,
                           LEFT: left_list,
                           RIGHT: right_list}
        #print self._init_tiles_dic
        
    def reset(self):
        """
        Reset the game so the grid is empty except for two
        initial tiles.
        """
        self._grid = []
        for dummy_i in range(self._grid_height):
            list_temp1 = []
            for dummy_j in range(self._grid_width):
                list_temp1.append(0)
            self._grid.append(list_temp1)
        self.new_tile()
        self.new_tile()

    def __str__(self):
        """
        Return a string representation of the grid for debugging.
        """
        #str_temp = '\r\n'+str(self._grid[0])+'\r\n'+str(self._grid[1])+'\r\n'+str(self._grid[2])+'\r\n'+str(self._grid[3])
        return str(self._grid)

    def get_grid_height(self):
        """
        Get the height of the board.
        """
        return self._grid_height

    def get_grid_width(self):
        """
        Get the width of the board.
        """
        return self._grid_width

    def move(self, direction):
        """
        Move all tiles in the given direction and add
        a new tile if any tiles moved.
        """
        move_flag = False
        init_tiles_list = self._init_tiles_dic[direction]
        #print len(init_tiles_list)
        for tilelist_idx in range(len(init_tiles_list)):
            before_line = []
            ord_list = []
            start_point = list(init_tiles_list[tilelist_idx])
            ord_list.append(list(start_point))
            #print tilelist_idx
            before_line.append(self.get_tile(start_point[0],start_point[1]))
            if direction == UP or direction == DOWN:
                times = self._grid_height
            elif direction == LEFT or direction == RIGHT:
                times = self._grid_width
            for dummy_j in range(times-1):
                start_point[0] += OFFSETS[direction][0]
                start_point[1] += OFFSETS[direction][1]
                ord_list.append(list(start_point))
                #print j
                before_line.append(self.get_tile(start_point[0],start_point[1]))
            
            after_line = merge(before_line)
            if after_line != before_line:
                move_flag = True
            
            for ord_idx in range(len(ord_list)):
                self.set_tile(ord_list[ord_idx][0],ord_list[ord_idx][1],after_line[ord_idx])
            #print tilelist_idx
            #print 'before_line:',before_line,' after_line:',after_line,' ord_list:',ord_list
            #print tilelist_idx
                
        if move_flag == True:
            self.new_tile()
            
        #print 'before_line:',before_line,' after_line:',after_line

    def new_tile(self):
		"""
        Create a new tile in a randomly selected empty
        square.  The tile should be 2 90% of the time and
        4 10% of the time.
        """
        list_temp1 = []
        for ord_x in range(self._grid_height):
            for ord_y in range(self._grid_width):
                if self._grid[ord_x][ord_y] == 0:
                    list_temp1.append((ord_x,ord_y))
        rand_idx = random.randrange(len(list_temp1))
        rand_x,rand_y = list_temp1[rand_idx]
        rand_val = random.choice([2, 2, 2, 2, 2, 2, 2, 2, 2, 4])
        self._grid[rand_x][rand_y] = rand_val

    def set_tile(self, row, col, value):
        """
        Set the tile at position row, col to have the given value.
        """
        self._grid[row][col] = value

    def get_tile(self, row, col):
        """
        Return the value of the tile at position row, col.
        """
        return self._grid[row][col]



#obj = TwentyFortyEight(2, 2)
#print obj
#obj.reset()

#obj = TwentyFortyEight(4, 5)
#obj.set_tile(0, 0, 8)
#obj.set_tile(0, 1, 16)
#obj.set_tile(0, 2, 8)
#obj.set_tile(0, 3, 16)
#obj.set_tile(0, 4, 8)
#obj.set_tile(1, 0, 16)
#obj.set_tile(1, 1, 8)
#obj.set_tile(1, 2, 16)
#obj.set_tile(1, 3, 8)
#obj.set_tile(1, 4, 16)
#obj.set_tile(2, 0, 8)
#obj.set_tile(2, 1, 16)
#obj.set_tile(2, 2, 8)
#obj.set_tile(2, 3, 16)
#obj.set_tile(2, 4, 8)
#obj.set_tile(3, 0, 16)
#obj.set_tile(3, 1, 8)
#obj.set_tile(3, 2, 16)
#obj.set_tile(3, 3, 8)
#obj.set_tile(3, 4, 16)
#print obj
#obj.move(LEFT)