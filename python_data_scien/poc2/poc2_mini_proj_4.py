"""
Loyd's Fifteen puzzle - solver and visualizer
Note that solved configuration has the blank (zero) tile in upper left
Use the arrows key to swap this tile with its neighbors
"""

import poc_fifteen_gui

class Puzzle:
    """
    Class representation for the Fifteen puzzle
    """

    def __init__(self, puzzle_height, puzzle_width, initial_grid=None):
        """
        Initialize puzzle with default height and width
        Returns a Puzzle object
        """
        self._height = puzzle_height
        self._width = puzzle_width
        self._grid = [[col + puzzle_width * row
                       for col in range(self._width)]
                      for row in range(self._height)]

        if initial_grid != None:
            for row in range(puzzle_height):
                for col in range(puzzle_width):
                    self._grid[row][col] = initial_grid[row][col]

    def __str__(self):
        """
        Generate string representaion for puzzle
        Returns a string
        """
        ans = ""
        for row in range(self._height):
            ans += str(self._grid[row])
            ans += "\n"
        return ans

    #####################################
    # GUI methods

    def get_height(self):
        """
        Getter for puzzle height
        Returns an integer
        """
        return self._height

    def get_width(self):
        """
        Getter for puzzle width
        Returns an integer
        """
        return self._width

    def get_number(self, row, col):
        """
        Getter for the number at tile position pos
        Returns an integer
        """
        return self._grid[row][col]

    def set_number(self, row, col, value):
        """
        Setter for the number at tile position pos
        """
        self._grid[row][col] = value

    def clone(self):
        """
        Make a copy of the puzzle to update during solving
        Returns a Puzzle object
        """
        new_puzzle = Puzzle(self._height, self._width, self._grid)
        return new_puzzle

    ########################################################
    # Core puzzle methods

    def current_position(self, solved_row, solved_col):
        """
        Locate the current position of the tile that will be at
        position (solved_row, solved_col) when the puzzle is solved
        Returns a tuple of two integers        
        """
        solved_value = (solved_col + self._width * solved_row)

        for row in range(self._height):
            for col in range(self._width):
                if self._grid[row][col] == solved_value:
                    return (row, col)
        assert False, "Value " + str(solved_value) + " not found"

    def update_puzzle(self, move_string):
        """
        Updates the puzzle state based on the provided move string
        """
        zero_row, zero_col = self.current_position(0, 0)
        for direction in move_string:
            if direction == "l":
                assert zero_col > 0, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col - 1]
                self._grid[zero_row][zero_col - 1] = 0
                zero_col -= 1
            elif direction == "r":
                assert zero_col < self._width - 1, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col + 1]
                self._grid[zero_row][zero_col + 1] = 0
                zero_col += 1
            elif direction == "u":
                assert zero_row > 0, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row - 1][zero_col]
                self._grid[zero_row - 1][zero_col] = 0
                zero_row -= 1
            elif direction == "d":
                assert zero_row < self._height - 1, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row + 1][zero_col]
                self._grid[zero_row + 1][zero_col] = 0
                zero_row += 1
            else:
                assert False, "invalid direction: " + direction

    ##################################################################
    # Phase one methods

    def lower_row_invariant(self, target_row, target_col):
        """
        Check whether the puzzle satisfies the specified invariant
        at the given position in the bottom rows of the puzzle (target_row > 1)
        Returns a boolean
        """
        if 0 != self.get_number(target_row,target_col):
            return False

        for idx_i in range(target_row+1,self.get_height()):
            for idx_j in range(0,self.get_width()):
                if (idx_i,idx_j) != self.current_position(idx_i,idx_j):
                    return False

        for idx_k in range(target_col+1,self.get_width()):
            if (target_row,idx_k) != self.current_position(target_row,idx_k):
                return False

        return True

    def is_same_row(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0:
            (c_row,dummy_c_col) = self.current_position(target_row,target_col)
            if c_row == target_row:
                return True
            else:
                return False
        elif status == 1:
            (c_row,dummy_c_col) = self.current_position(target_row+1,target_col-1)
            if c_row == target_row:
                return True
            else:
                return False
        elif status == 2:
            (c_row,dummy_c_col) = self.current_position(target_row-1,target_col+1)
            if c_row == target_row:
                return True
            else:
                return False

    def is_same_col(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0:
            (dummy_c_row,c_col) = self.current_position(target_row,target_col)
            if c_col == target_col:
                return True
            else:
                return False
        elif status == 1:
            (dummy_c_row,c_col) = self.current_position(target_row+1,target_col-1)
            if c_col == target_col:
                return True
            else:
                return False
        elif status == 2:
            (dummy_c_row,c_col) = self.current_position(target_row-1,target_col+1)
            if c_col == target_col:
                return True
            else:
                return False

    def is_move_over(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0: 
            (c_row,c_col) = self.current_position(target_row,target_col)
            if c_row == target_row and c_col == target_col:
                return True
            else:
                return False
        elif status == 1:
            (c_row,c_col) = self.current_position(target_row+1,target_col-1)
            if c_row == target_row+1 and c_col == target_col-1:
                return True
            else:
                return False
        elif status == 2:
            (c_row,c_col) = self.current_position(target_row-1,target_col+1)
            if c_row == target_row-1 and c_col == target_col+1:
                return True
            else:
                return False

    def get_diff_col(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0:
            (dummy_c_row,c_col) = self.current_position(target_row,target_col)
            return target_col-c_col
        elif status == 1:
            (dummy_c_row,c_col) = self.current_position(target_row+1,target_col-1)
            return target_col-c_col
        elif status == 2:
            (dummy_c_row,c_col) = self.current_position(target_row-1,target_col+1)
            return target_col-c_col

    def get_diff_row(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0:
            (c_row,dummy_c_col) = self.current_position(target_row,target_col)
            return target_row-c_row
        elif status == 1:
            (c_row,dummy_c_col) = self.current_position(target_row+1,target_col-1)
            return target_row-c_row
        elif status == 2:
            (c_row,dummy_c_col) = self.current_position(target_row-1,target_col+1)
            return target_row-c_row

    def get_relative_pos(self,target_row,target_col,status):
        """
        help func
        """
        if status == 0:
            (c_row,c_col) = self.current_position(target_row,target_col)
            return (target_row-c_row,target_col-c_col)
        elif status == 1:
            (c_row,c_col) = self.current_position(target_row+1,target_col-1)
            return (target_row-c_row,target_col-c_col)
        elif status == 2:
            (c_row,c_col) = self.current_position(target_row-1,target_col+1)
            return (target_row-c_row,target_col-c_col)

    def is_prepered_pos(self,target_row,target_col,status):
        """
        help func
        """
        if status == 1: 
            (c_row,c_col) = self.current_position(target_row+1,target_col-1)
            if c_row == target_row and c_col == target_col:
                return True
            else:
                return False
        elif status == 2:
            (c_row,c_col) = self.current_position(target_row-1,target_col+1)
            if c_row == target_row and c_col == target_col:
                return True
            else:
                return False

    def finish_last_step(self,target_row,target_col,status):
        """
        help func
        """
        if status == 1: 
            move_str = 'rulddrulurddlu' + 'r' * (self.get_width() - 1)
            self.update_puzzle(move_str)
            return move_str
        elif status == 2:
            move_str = 'dlurrdluldrruld'
            self.update_puzzle(move_str)
            return move_str

    def trans_pos_to_magic_str1(self,relative_pos):
        """
        help func
        """
        (dummy_relative_row,relative_col) = relative_pos[0],relative_pos[1]
        if relative_col >= 0:
            str1 = 'l'*abs(relative_col)+'u'+'r'*abs(relative_col)+'d'
            return str1
        else:
            str1 = 'r'*abs(relative_col)+'u'+'l'*abs(relative_col)+'d'
            return str1

    def trans_pos_to_magic_str2(self,relative_pos):
        """
        help func
        """
        (relative_row,relative_col) = relative_pos[0],relative_pos[1]
        zero_tile_row,zero_tile_col = self.current_position(0,0)
        target_row = zero_tile_row - relative_row
        dummy_target_col = zero_tile_col - relative_col
        if relative_col >= 0:
            dir_str = 'l'
            o_dir_str = 'r'
            str1 = 'u'*relative_row+dir_str*abs(relative_col)+'d'*relative_row+o_dir_str*abs(relative_col)
        else:
            dir_str = 'r'
            o_dir_str = 'l'
            if target_row == 0:
                str1 = 'u'*relative_row+dir_str*abs(relative_col)+'d'+o_dir_str*abs(relative_col)+'u'+'d'*relative_row
            else:
                str1 = 'u'*relative_row+dir_str*abs(relative_col)+'u'+o_dir_str*abs(relative_col)+'d'+'d'*relative_row
        return str1

    def trans_pos_to_magic_str3(self,relative_pos):
        """
        help func
        """
        (relative_row,dummy_relative_col) = relative_pos[0],relative_pos[1]
        str1 = 'u'*relative_row+'l'+'d'*relative_row+'r'
        return str1

    def move_to_same_col(self,result_str,target_row,target_col,status):
        """
        help func
        """
        if self.is_same_row(target_row,target_col,status):
            if self.get_diff_col(target_row,target_col,status) == 1:
                move_str = 'l'
                result_str += move_str
                self.update_puzzle(move_str)
                return str(result_str)
            elif self.get_diff_col(target_row,target_col,status) == -1:
                move_str = 'rulld'
                result_str += move_str
                self.update_puzzle(move_str)
                return str(result_str)
            else:
                relative_pos = self.get_relative_pos(target_row,target_col,status)
                magic_str = self.trans_pos_to_magic_str1(relative_pos)
                result_str += magic_str
                self.update_puzzle(magic_str)
                return self.move_to_same_col(result_str,target_row,target_col,status)
        else:
            if self.is_same_col(target_row,target_col,status):
                return str(result_str)
            else:
                relative_pos = self.get_relative_pos(target_row,target_col,status)
                magic_str = self.trans_pos_to_magic_str2(relative_pos)
                result_str += magic_str
                self.update_puzzle(magic_str)
                return self.move_to_same_col(result_str,target_row,target_col,status)

    def move_to_same_row(self,result_str,target_row,target_col,status):
        """
        help func
        """
        if self.get_diff_row(target_row,target_col,status) == 1:
            move_str = 'uld'
            result_str += move_str
            self.update_puzzle(move_str)
            return str(result_str)
        else:
            relative_pos = self.get_relative_pos(target_row,target_col,status)
            magic_str = self.trans_pos_to_magic_str3(relative_pos)
            result_str += magic_str
            self.update_puzzle(magic_str)
            return self.move_to_same_row(result_str,target_row,target_col,status)

    def solve_interior_tile(self, target_row, target_col):
        """
        Place correct tile at target position
        Updates puzzle and returns a move string
        """
        empyt_str = ''
        result_str = ''
        result_str += self.move_to_same_col(empyt_str,target_row,target_col,0)
        if self.is_move_over(target_row,target_col,0):
            return result_str
        else:
            result_str += self.move_to_same_row(empyt_str,target_row,target_col,0)
            return result_str

    def solve_col0_tile(self, target_row):
        """
        Solve tile in column zero on specified row (> 1)
        Updates puzzle and returns a move string
        """
        empyt_str = ''
        target_row -= 1
        target_col = 1
        result_str = ''
        result_str += 'ur'
        self.update_puzzle(result_str)
        if self.is_move_over(target_row,target_col,1):
            move_str = 'r'*(self.get_width()-2)
            result_str += move_str
            self.update_puzzle(move_str)
            return result_str
        else:
            result_str += self.move_to_same_col(empyt_str,target_row,target_col,1)
            if self.is_prepered_pos(target_row,target_col,1):
                result_str += self.finish_last_step(target_row,target_col,1)
                return result_str
            else:
                result_str += self.move_to_same_row(empyt_str,target_row,target_col,1)
                result_str += self.finish_last_step(target_row,target_col,1)
                return result_str


    #############################################################
    # Phase two methods
    def is_right_pos(self,target_row,target_col):
        """
        help func
        """
        (c_row,c_col) = self.current_position(target_row,target_col)
        if c_row == target_row and c_col == target_col:
            return True
        else:
            return False

    def move_to_same_col_p2(self,result_str,target_row,target_col,status):
        """
        help func
        """
        if self.is_same_row(target_row,target_col,status):
            if self.get_diff_col(target_row,target_col,status) == 1:
                move_str = 'lur'
                result_str += move_str
                self.update_puzzle(move_str)
                return str(result_str)
            # elif self.get_diff_col(target_row,target_col,status) == -1:
            #     move_str = 'rulld'
            #     result_str += move_str
            #     self.update_puzzle(move_str)
            #     return str(result_str)
            else:
                relative_pos = self.get_relative_pos(target_row,target_col,status)
                magic_str = self.trans_pos_to_magic_str1(relative_pos)
                result_str += magic_str
                self.update_puzzle(magic_str)
                return self.move_to_same_col_p2(result_str,target_row,target_col,status)
        else:
            if self.is_same_col(target_row,target_col,status):
                return str(result_str)
            else:
                relative_pos = self.get_relative_pos(target_row,target_col,status)
                magic_str = self.trans_pos_to_magic_str2(relative_pos)
                result_str += magic_str
                self.update_puzzle(magic_str)
                return self.move_to_same_col_p2(result_str,target_row,target_col,status)

    def move_to_same_row_p2(self,result_str,target_row,target_col,status):
        """
        help func
        """
        if self.get_diff_row(target_row,target_col,status) == 1:
            move_str = 'u'
            result_str += move_str
            self.update_puzzle(move_str)
            return str(result_str)
        # else:
        #     relative_pos = self.get_relative_pos(target_row,target_col,status)
        #     magic_str = self.trans_pos_to_magic_str3(relative_pos)
        #     result_str += magic_str
        #     self.update_puzzle(magic_str)
        #     return self.move_to_same_row_p2(result_str,target_row,target_col,status)

    def row0_invariant(self, target_col):
        """
        Check whether the puzzle satisfies the row zero invariant
        at the given column (col > 1)
        Returns a boolean
        """
        if self.get_number(0,target_col) != 0:
            return False
        for row in range(target_col+1,self.get_width()):
            if self.is_right_pos(0,row):
                pass
            else:
                return False
        for col in range(target_col,self.get_width()):
            if self.is_right_pos(1,col):
                pass
            else:
                return False
        for row in range(2,self.get_height()):
            for col in range(0,self.get_width()):
                if self.is_right_pos(row,col):
                    pass
                else:
                    return False
        return True

    def row1_invariant(self, target_col):
        """
        Check whether the puzzle satisfies the row one invariant
        at the given column (col > 1)
        Returns a boolean
        """
        if self.get_number(1,target_col) != 0:
            return False
        for row in range(target_col+1,self.get_width()):
            if self.is_right_pos(0,row):
                pass
            else:
                return False
        for col in range(target_col+1,self.get_width()):
            if self.is_right_pos(1,col):
                pass
            else:
                return False
        for row in range(2,self.get_height()):
            for col in range(0,self.get_width()):
                if self.is_right_pos(row,col):
                    pass
                else:
                    return False
        return True

    def solve_row0_tile(self, target_col):
        """
        Solve the tile in row zero at the specified column
        Updates puzzle and returns a move string
        """
        empyt_str = ''
        target_row = 1
        target_col -= 1
        result_str = ''
        result_str += 'ld'
        self.update_puzzle(result_str)
        if self.is_move_over(target_row,target_col,2):
            # move_str = 'r'*(self.get_width()-2)
            # result_str += move_str
            # self.update_puzzle(move_str)
            return result_str
        else:
            result_str += self.move_to_same_col_p2(empyt_str,target_row,target_col,2)
            if self.is_prepered_pos(target_row,target_col,2):
                result_str += self.finish_last_step(target_row,target_col,2)
                return result_str
            else:
                result_str += self.move_to_same_row_p2(empyt_str,target_row,target_col,2)
                result_str += self.finish_last_step(target_row,target_col,2)
                return result_str

    def solve_row1_tile(self, target_col):
        """
        Solve the tile in row one at the specified column
        Updates puzzle and returns a move string
        """
        empyt_str = ''
        target_row = 1
        result_str = ''
        result_str += self.move_to_same_col_p2(empyt_str,target_row,target_col,0)
        if self.is_move_over(target_row,target_col,0):
            return result_str
        else:
            result_str += self.move_to_same_row_p2(empyt_str,target_row,target_col,0)
            return result_str

    ###########################################################
    # Phase 3 methods
    def move_to_solve_tbt(self,result_str):
        """
        help func
        """
        if self.row0_invariant(0):
            return result_str
        else:
            move_str = 'drul'
            result_str += move_str
            self.update_puzzle(move_str)
            return self.move_to_solve_tbt(result_str)

    def solve_2x2(self):
        """
        Solve the upper left 2x2 part of the puzzle
        Updates the puzzle and returns a move string
        """
        empyt_str = ''
        result_str = ''
        result_str += 'ul'
        self.update_puzzle(result_str)
        if self.row0_invariant(0):
            return result_str
        else:
            result_str += self.move_to_solve_tbt(empyt_str)
            return result_str

    def move_tile_to_end(self):
        """
        help func
        """
        zero_tile_row,zero_tile_col = self.current_position(0,0)
        width = self.get_width()
        height =self.get_height()
        target_row = height - 1
        target_col = width - 1
        relative_row = target_row - zero_tile_row
        relative_col = target_col - zero_tile_col

        if zero_tile_row == target_row and zero_tile_col == relative_col:
            move_str = ''
            return move_str
        else:
            move_str = 'd'*relative_row+'r'*relative_col
            self.update_puzzle(move_str)
            return move_str

    def solve_puzzle(self):
        """
        Generate a solution string for a puzzle
        Updates the puzzle and returns a move string
        """
        width = self.get_width()
        height =self.get_height()
        result_str = ''
        result_str += self.move_tile_to_end()

        
        if self.lower_row_invariant(height-1, width-1) == False: 
            #print 'phase1 start failed!'
            return
        #print 'phase1 start!'
        #print self
        for target_row in range(height-1, 1, -1):
            for target_col in range(width-1,-1,-1):
                    if target_col != 0 and self.lower_row_invariant(target_row, target_col):
                        result_str += self.solve_interior_tile(target_row, target_col)
                    elif self.lower_row_invariant(target_row, target_col):
                        #print 'solve_col0_tile:',target_row,target_col
                        #print self.lower_row_invariant(target_row, target_col)
                        result_str += self.solve_col0_tile(target_row)
                    #print self

        if self.row1_invariant(width-1) == False: 
            #print 'phase2 start failed!'
            return
        #print 'phase2 start!'
        #print self
        for target_col in range(width-1,1,-1):
            for target_row in [1,0]:
                if target_row == 1 and self.row1_invariant(target_col):
                    result_str += self.solve_row1_tile(target_col)
                elif self.row0_invariant(target_col):
                    result_str += self.solve_row0_tile(target_col)
                #print self

        if self.row1_invariant(1) == False: 
            #print 'phase3 start failed!'
            return
        #print 'phase3 start!'
        result_str += self.solve_2x2()
        #print 'phase3 end!!!'

        return result_str

# Start interactive simulation
#poc_fifteen_gui.FifteenGUI(Puzzle(4, 4))


#################################################
###### test on lower_row_invariant(self, target_row, target_col)
# print 'test on lower_row_invariant(self, target_row, target_col)'

# p=Puzzle(4,4,
#     [
#     [0,1,2,3],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )

# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,10],
#     [9,1,0,11],
#     [12,13,14,15]
#     ]
#     )
# print p.lower_row_invariant(2,2)
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,10],
#     [9,1,0,11],
#     [12,14,13,15]
#     ]
#     )
# print p.lower_row_invariant(2,2)
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,11],
#     [9,1,0,10],
#     [12,13,14,15]
#     ]
#     )
# print p.lower_row_invariant(2,2)
##### test on move_to_same_col(self, target_row, target_col)
# print 'test on move_to_same_col(self, target_row, target_col)'
# p=Puzzle(3,6,
#     [
#     [3, 11, 7, 5, 8, 12],
#     [4, 6, 1, 10, 13, 9],
#     [2, 0, 14, 15, 16, 17],
#     ]
#     )
# result_str = ''
# result_str = p.move_to_same_col(result_str,2,1,0)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,10],
#     [9,1,13,11],
#     [12,15,14,0]
#     ]
#     )
# result_str = ''
# result_str = p.move_to_same_col(result_str,3,3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,15,5,10],
#     [9,1,13,11],
#     [12,6,14,0]
#     ]
#     )
# result_str = ''
# result_str = p.move_to_same_col(result_str,3,3)
# print result_str
# print p
##### test on move_to_same_row(self, target_row, target_col)
# print 'test on move_to_same_row(self, target_row, target_col)'
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,15],
#     [9,1,13,11],
#     [12,10,14,0]
#     ]
#     )
# result_str = ''
# result_str = p.move_to_same_row(result_str,3,3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,14,11],
#     [9,1,6,13],
#     [12,10,0,15]
#     ]
#     )
# result_str = ''
# result_str = p.move_to_same_row(result_str,3,2)
# print result_str
# print p
##### test on solve_interior_tile(self, target_row, target_col)
# print 'test on solve_interior_tile(self, target_row, target_col)'
# p=Puzzle(3,6,
#     [
#     [3, 11, 7, 5, 8, 12],
#     [4, 6, 1, 10, 13, 9],
#     [2, 0, 14, 15, 16, 17],
#     ]
#     )
# result_str = p.solve_interior_tile(2,1)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,5,6,15],
#     [9,1,13,11],
#     [12,10,14,0]
#     ]
#     )
# result_str = p.solve_interior_tile(3,3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,15,6,5],
#     [9,1,13,11],
#     [12,10,14,0]
#     ]
#     )
# result_str = p.solve_interior_tile(3,3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,2,3,7],
#     [8,14,6,5],
#     [9,1,13,11],
#     [12,10,0,15]
#     ]
#     )
# result_str = p.solve_interior_tile(3,2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [9,1,2,3],
#     [4,5,6,7],
#     [8,0,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_interior_tile(2,1)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [3,1,2,7],
#     [4,5,6,9],
#     [8,0,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_interior_tile(2,1)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [3,1,2,9],
#     [4,5,6,7],
#     [8,0,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_interior_tile(2,1)
# print result_str
# print p
# # ##### test on solve_col0_tile(self, target_row)
# print 'test on solve_col0_tile(self, target_row)'
# p=Puzzle(3,6,
#     [
# [3, 11, 7, 5, 8, 12],
# [2, 4, 1, 14, 16, 9],
# [0, 13, 10, 6, 15, 17]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [8,1,2,3],
#     [4,5,6,7],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,1,2,3],
#     [8,5,6,7],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,1,2,3],
#     [5,8,6,7],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,8,2,3],
#     [5,1,6,7],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,3,2,8],
#     [5,1,6,7],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [4,3,2,7],
#     [5,1,6,8],
#     [0,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_col0_tile(2)
# print result_str
# print p
# ##### test on row0_invariant(self, target_col)
# print 'test on row0_invariant(self, target_col)'
# p=Puzzle(4,4,
#     [
#     [1,3,2,0],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row0_invariant(3)
# p=Puzzle(4,4,
#     [
#     [1,3,2,0],
#     [4,5,7,6],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row0_invariant(3)
# p=Puzzle(4,4,
#     [
#     [1,0,2,3],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row0_invariant(1)
# p=Puzzle(4,4,
#     [
#     [1,3,0,2],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row0_invariant(2)
# ##### test on row1_invariant(self, target_col)
# print 'test on row1_invariant(self, target_col)'
# p=Puzzle(4,4,
#     [
#     [5,1,2,3],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row1_invariant(1)
# p=Puzzle(4,4,
#     [
#     [5,2,1,3],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row1_invariant(1)
# p=Puzzle(4,4,
#     [
#     [3,2,1,5],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row1_invariant(1)
# p=Puzzle(4,4,
#     [
#     [1,5,2,3],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row1_invariant(1)
# p=Puzzle(4,4,
#     [
#     [1,5,2,3],
#     [4,7,6,0],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# print p.row1_invariant(3)
# print 
# ##### test on solve_row1_tile(self, target_col)
# print 'test on solve_row1_tile(self, target_col)'
# p=Puzzle(4,4,
#     [
#     [1,3,2,7],
#     [4,5,6,0],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row1_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,3,2,6],
#     [4,5,7,0],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row1_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,3,2,6],
#     [7,4,5,0],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row1_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [7,3,2,6],
#     [1,4,5,0],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row1_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [6,1,2,3],
#     [4,5,0,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row1_tile(2)
# print result_str
# print p
# ##### test on solve_row0_tile(self, target_col)
# print 'test on solve_row0_tile(self, target_col)'
# p=Puzzle(4,4,
#     [
#     [1,3,2,0],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,3,2,0],
#     [6,5,4,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [3,6,2,0],
#     [1,5,4,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,6,2,0],
#     [3,5,4,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(3)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,4,0,3],
#     [2,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(2)
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [2,4,0,3],
#     [5,1,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_row0_tile(2)
# print result_str
# print p
# ##### test on solve_2x2(self)
# print 'test on solve_2x2(self)'
# p=Puzzle(4,4,
#     [
#     [1,5,2,3],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_2x2()
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [5,4,2,3],
#     [1,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_2x2()
# print result_str
# print p
# ##### test on move_tile_to_end(self)
# print 'test on move_tile_to_end(self)'
# p=Puzzle(4,4,
#     [
#     [1,5,2,3],
#     [4,0,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.move_tile_to_end()
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [1,5,2,3],
#     [4,15,6,7],
#     [8,9,10,11],
#     [12,13,14,0]
#     ]
#     )
# result_str = p.move_tile_to_end()
# print result_str
# print p
##### test on solve_puzzle(self)
# print 'test on solve_puzzle(self)'
# p=Puzzle(4,4,
#     [
#     [1,0,2,3],
#     [4,5,6,7],
#     [8,9,10,11],
#     [12,13,14,15]
#     ]
#     )
# result_str = p.solve_puzzle()
# print result_str
# print p
# p=Puzzle(4,4,
#     [
#     [5,12,3,14],
#     [2,1,0,6],
#     [13,11,4,10],
#     [8,15,7,9]
#     ]
#     )
# result_str = p.solve_puzzle()
# print result_str
# print p
# p=Puzzle(3, 6, [[16, 7, 13, 17, 5, 9], [3, 0, 14, 10, 12, 6], [4, 15, 2, 11, 8, 1]])
# result_str = p.solve_puzzle()
# print result_str
# print p
#################################################