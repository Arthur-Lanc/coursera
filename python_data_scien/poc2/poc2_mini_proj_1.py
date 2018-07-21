"""
Student portion of Zombie Apocalypse mini-project
"""

import random
import poc_grid
import poc_queue
import poc_zombie_gui

# global constants
EMPTY = 0 
FULL = 1
FOUR_WAY = 0
EIGHT_WAY = 1
OBSTACLE = 5
HUMAN = 6
ZOMBIE = 7


class Apocalypse(poc_grid.Grid):
    """
    Class for simulating zombie pursuit of human on grid with
    obstacles
    """

    def __init__(self, grid_height, grid_width, obstacle_list = None, 
                 zombie_list = None, human_list = None):
        """
        Create a simulation of given size with given obstacles,
        humans, and zombies
        """
        poc_grid.Grid.__init__(self, grid_height, grid_width)
        if obstacle_list != None:
            for cell in obstacle_list:
                self.set_full(cell[0], cell[1])
        if zombie_list != None:
            self._zombie_list = list(zombie_list)
        else:
            self._zombie_list = []
        if human_list != None:
            self._human_list = list(human_list)  
        else:
            self._human_list = []
        
    def clear(self):
        """
        Set cells in obstacle grid to be empty
        Reset zombie and human lists to be empty
        """
        poc_grid.Grid.clear(self)
        self._zombie_list = []
        self._human_list = []
        
    def add_zombie(self, row, col):
        """
        Add zombie to the zombie list
        """
        self._zombie_list.append((row,col))
                
    def num_zombies(self):
        """
        Return number of zombies
        """
        return len(self._zombie_list)
                  
    def zombies(self):
        """
        Generator that yields the zombies in the order they were
        added.
        """
        for item in self._zombie_list:
            yield item

    def add_human(self, row, col):
        """
        Add human to the human list
        """
        self._human_list.append((row,col))
        
    def num_humans(self):
        """
        Return number of humans
        """
        return len(self._human_list)
    
    def humans(self):
        """
        Generator that yields the humans in the order they were added.
        """
        for item in self._human_list:
            yield item
        
    def compute_distance_field(self, entity_type):
        """
        Function computes and returns a 2D distance field
        Distance at member of entity_list is zero
        Shortest paths avoid obstacles and use four-way distances
        """
        height = poc_grid.Grid.get_grid_height(self)
        width = poc_grid.Grid.get_grid_width(self)

        visited = poc_grid.Grid(height,width)
        distance_field = [[width*height for dummy_col in range(width)] 
                       for dummy_row in range(height)]

        boundary = poc_queue.Queue()
        if entity_type == ZOMBIE:
            for item in self._zombie_list:
                boundary.enqueue(item)
        elif entity_type == HUMAN:
            for item in self._human_list:
                boundary.enqueue(item)
        else:
            print 'entity_type error in func:compute_distance_field!!!'
        for b_item in boundary:
            visited.set_full(b_item[0],b_item[1])
            distance_field[b_item[0]][b_item[1]] = 0

        while len(boundary) != 0:
            current_cell = boundary.dequeue()
            for neighbor_cell in poc_grid.Grid.four_neighbors(self,current_cell[0], current_cell[1]):
                if visited.is_empty(neighbor_cell[0],neighbor_cell[1]) and poc_grid.Grid.is_empty(self,neighbor_cell[0],neighbor_cell[1]):
                    visited.set_full(neighbor_cell[0],neighbor_cell[1])
                    boundary.enqueue(neighbor_cell)
                    distance_field[neighbor_cell[0]][neighbor_cell[1]] = distance_field[current_cell[0]][current_cell[1]] + 1
        #print distance_field
        return distance_field
    
    def move_humans(self, zombie_distance_field):
        """
        Function that moves humans away from zombies, diagonal moves
        are allowed
        """
        height = poc_grid.Grid.get_grid_height(self)
        width = poc_grid.Grid.get_grid_width(self)
        for idx in range(len(self._human_list)):
            i_duple = self._human_list[idx]
            max_dis = i_duple
            neighbor_cell_list = poc_grid.Grid.eight_neighbors(self,i_duple[0], i_duple[1])
            for neighbor_cell in neighbor_cell_list:
                if zombie_distance_field[neighbor_cell[0]][neighbor_cell[1]] != width*height and zombie_distance_field[neighbor_cell[0]][neighbor_cell[1]] > zombie_distance_field[max_dis[0]][max_dis[1]]:
                    max_dis = neighbor_cell
            
            if zombie_distance_field[max_dis[0]][max_dis[1]] == width*height:
                self._human_list[idx] = i_duple
                return
                    
            max_dis_list = []
            cell_list = list(neighbor_cell_list)
            cell_list.append(i_duple)
            for k_item in cell_list:
                if zombie_distance_field[k_item[0]][k_item[1]] == zombie_distance_field[max_dis[0]][max_dis[1]]:
                    max_dis_list.append((k_item[0],k_item[1]))
            #print max_dis_list
            my_choice = random.choice(max_dis_list)
            #print my_choice
            self._human_list[idx] = my_choice

    def move_zombies(self, human_distance_field):
        """
        Function that moves zombies towards humans, no diagonal moves
        are allowed
        """
        for idx in range(len(self._zombie_list)):
            i_duple = self._zombie_list[idx]
            min_dis = i_duple
            neighbor_cell_list = poc_grid.Grid.four_neighbors(self,i_duple[0], i_duple[1])
            for neighbor_cell in neighbor_cell_list:
                if human_distance_field[neighbor_cell[0]][neighbor_cell[1]] < human_distance_field[min_dis[0]][min_dis[1]]:
                    min_dis = neighbor_cell
            min_dis_list = []
            cell_list = list(neighbor_cell_list)
            cell_list.append(i_duple)
            for k_item in cell_list:
                if human_distance_field[k_item[0]][k_item[1]] == human_distance_field[min_dis[0]][min_dis[1]]:
                    min_dis_list.append((k_item[0],k_item[1]))
            self._zombie_list[idx] = random.choice(min_dis_list)

# Start up gui for simulation - You will need to write some code above
# before this will work without errors

poc_zombie_gui.run_gui(Apocalypse(3, 3, [(0, 0), (0, 1), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2)], [(0, 2)], [(1, 1)]))
