#http://www.codeskulptor.org/#user43_38xv9eBr3U_4.py

"""
Monte Carlo Tic-Tac-Toe Player
"""

import random
import poc_ttt_gui
import poc_ttt_provided as provided

# Constants for Monte Carlo simulator
# You may change the values of these constants as desired, but
#  do not change their names.
NTRIALS = 200         # Number of trials to run
SCORE_CURRENT = 2.0 # Score for squares played by the current player
SCORE_OTHER = 1.5   # Score for squares played by the other player
    
# Add your functions here.
def mc_trial(board, player):
    '''This function takes a current board and the next player to move. 
    The function should play a game starting with the given player by making random moves, 
    alternating between players. The function should return when the game is over. 
    The modified board will contain the state of the game, 
    so the function does not return anything. 
    In other words, the function should modify the board input.'''
    while True:
        empty_square_list = board.get_empty_squares()
        row_col_tuple = random.choice(empty_square_list)
        board.move(row_col_tuple[0],row_col_tuple[1],player)
        player = provided.switch_player(player)
        game_state = board.check_win()
        if game_state == None:
            pass
        else:
            # if game_state == provided.PLAYERX:
            #     print "X wins!"
            # elif game_state == provided.PLAYERO:
            #     print "O wins!"
            # elif game_state == provided.DRAW:
            #     print "Tie!"
            # else:
            #     print "Error: unknown winner"
            break
def update_temp_scores_me_win(board_dim,board,temp_scores,player):
    '''update_temp_scores_me_win'''
    for row in range(board_dim):
         for col in range(board_dim):
            if board.square(row,col) == provided.EMPTY:
                temp_scores[row][col] = 0
            elif board.square(row,col) == player:
                temp_scores[row][col] = SCORE_CURRENT
            elif board.square(row,col) == provided.switch_player(player):
                temp_scores[row][col] = -SCORE_OTHER

def update_temp_scores_you_win(board_dim,board,temp_scores,player):
    '''update_temp_scores_you_win'''
    for row in range(board_dim):
         for col in range(board_dim):
            if board.square(row,col) == provided.EMPTY:
                temp_scores[row][col] = 0
            elif board.square(row,col) == player:
                temp_scores[row][col] = -SCORE_CURRENT
            elif board.square(row,col) == provided.switch_player(player):
                temp_scores[row][col] = SCORE_OTHER

def mc_update_scores(scores, board, player):
    '''This function takes a grid of scores (a list of lists) 
    with the same dimensions as the Tic-Tac-Toe board, 
    a board from a completed game, and which player the machine player is. 
    The function should score the completed board and update the scores grid. 
    As the function updates the scores grid directly, it does not return anythin.'''
    board_dim = board.get_dim()
    temp_scores = [[0 for dummycol in range(board_dim)] 
                           for dummyrow in range(board_dim)]
    game_state = board.check_win()
    if game_state == provided.DRAW:
        pass
    elif game_state == player:
        update_temp_scores_me_win(board_dim,board,temp_scores,player)
    elif game_state == provided.switch_player(player):
        update_temp_scores_you_win(board_dim,board,temp_scores,player)

    for row in range(board_dim):
         for col in range(board_dim):
            scores[row][col] += temp_scores[row][col]

def get_best_move(board, scores):
    '''This function takes a current board and a grid of scores. 
    The function should find all of the empty squares 
    with the maximum score and randomly return one of them as a (row, column) tuple. 
    It is an error to call this function 
    with a board that has no empty squares (there is no possible next move), 
    so your function may do whatever it wants in that case. 
    The case where the board is full will not be tested.'''
    empty_square_list = board.get_empty_squares()
    if len(empty_square_list) == 0:
        print 'error in get_best_move: there is no empty square to move'
        return None

    for idx in range(len(empty_square_list)):
        row = empty_square_list[idx][0]
        col = empty_square_list[idx][1]
        if idx == 0:
            max_score = scores[row][col]
        elif scores[row][col] > max_score:
            max_score = scores[row][col]
            # max_row_col = (row,col)

    max_row_col_list = []
    for idx in range(len(empty_square_list)):
        row = empty_square_list[idx][0]
        col = empty_square_list[idx][1]
        if scores[row][col] == max_score:
            max_row_col_list.append((row,col))
    return random.choice(max_row_col_list)

def mc_move(board, player, trials):
    '''This function takes a current board, which player the machine player is, 
    and the number of trials to run. 
    The function should use the Monte Carlo simulation described above to return a move 
    for the machine player in the form of a (row, column) tuple. 
    Be sure to use the other functions you have written!'''
    board_dim = board.get_dim()
    scores = [[0 for dummycol in range(board_dim)] 
                           for dummyrow in range(board_dim)]
    
    for dummy in range(trials):
        board_clone = board.clone()
        mc_trial(board_clone,player)
        mc_update_scores(scores,board_clone,player)

    best_move_tuple = get_best_move(board,scores)
    #print 'get_best_move : '+str(best_move_tuple)
    return best_move_tuple


# Test game with the console or the GUI.  Uncomment whichever 
# you prefer.  Both should be commented out when you submit 
# for testing to save time.

####################################
# # my test
# # func1: mc_trial(board, player)
# print 'func1: mc_trial(board, player)'
# board = provided.TTTBoard(3)
# board.move(1,1,provided.PLAYERX)
# board.move(0,2,provided.PLAYERO)
# print board
# board_clone = board.clone()
# mc_trial(board_clone, provided.PLAYERX)
# print board_clone
# # func2: mc_update_scores(scores, board, player)
# print 'func2: mc_update_scores(scores, board, player)'
# board_dim = board.get_dim()
# scores = [[0 for dummycol in range(board_dim)] 
#                        for dummyrow in range(board_dim)]
# mc_update_scores(scores,board_clone,provided.PLAYERX)
# print scores

# board_clone = board.clone()
# mc_trial(board_clone, provided.PLAYERX)
# print board_clone
# mc_update_scores(scores,board_clone,provided.PLAYERX)
# print scores

# # func3: get_best_move(board, scores)
# print 'func3: get_best_move(board, scores)'
# print get_best_move(board, scores)
# # func4: mc_move(board, player, trials)
# print 'func4: mc_move(board, player, trials)'
# mc_move(board,provided.PLAYERX,1000)
####################################
#provided.play_game(mc_move, NTRIALS, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERX, mc_move, NTRIALS, False)
