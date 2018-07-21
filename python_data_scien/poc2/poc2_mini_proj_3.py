"""
Mini-max Tic-Tac-Toe Player
"""

import poc_ttt_gui
import poc_ttt_provided as provided

# Set timeout, as mini-max can take a long time
import codeskulptor
codeskulptor.set_timeout(60)

# SCORING VALUES - DO NOT MODIFY
SCORES = {provided.PLAYERX: 1,
          provided.DRAW: 0,
          provided.PLAYERO: -1}

def mm_move(board, player):
    """
    Make a move on the board.
    
    Returns a tuple with two elements.  The first element is the score
    of the given board and the second element is the desired move as a
    tuple, (row, col).
    """
    if board.check_win() == provided.PLAYERX:
        return SCORES[provided.PLAYERX],(-1,-1)
    elif board.check_win() == provided.PLAYERO:
        return SCORES[provided.PLAYERO],(-1,-1)
    elif board.check_win() == provided.DRAW:
        return SCORES[provided.DRAW],(-1,-1)
    else:
        empty_tuple_list = board.get_empty_squares()
        score_pos_tuple_list = []
        best_score = None
        best_pos = None
        for idx1 in range(len(empty_tuple_list)):
            empty_tuple = empty_tuple_list[idx1]
            board_clone = board.clone()
            board_clone.move(empty_tuple[0],empty_tuple[1],player)
            score_pos_tuple = mm_move(board_clone,provided.switch_player(player))
            score_pos_tuple_list.append(score_pos_tuple)

            #decide best score and pos fast!!!
            if score_pos_tuple[0]*SCORES[player] == 1:
                return (score_pos_tuple[0],empty_tuple)

        #decide best score and pos
        for idx2 in range(len(score_pos_tuple_list)):
            if idx2 == 0:
                best_score = score_pos_tuple_list[idx2][0]
                best_pos = empty_tuple_list[idx2]
            else:
                if score_pos_tuple_list[idx2][0]*SCORES[player] > best_score*SCORES[player]:
                    best_score = score_pos_tuple_list[idx2][0]
                    best_pos = empty_tuple_list[idx2]

        return (best_score,best_pos)

def move_wrapper(board, player, trials):
    """
    Wrapper to allow the use of the same infrastructure that was used
    for Monte Carlo Tic-Tac-Toe.
    """
    move = mm_move(board, player)
    assert move[1] != (-1, -1), "returned illegal move (-1, -1)"
    return move[1]

# Test game with the console or the GUI.
# Uncomment whichever you prefer.
# Both should be commented out when you submit for
# testing to save time.

# provided.play_game(move_wrapper, 1, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERO, move_wrapper, 1, False)


#print mm_move(
#    provided.TTTBoard(
#        3, False, [
#    [provided.PLAYERX, provided.EMPTY, provided.EMPTY], 
#    [provided.PLAYERO, provided.PLAYERO, provided.EMPTY], 
#    [provided.EMPTY, provided.PLAYERX, provided.EMPTY]
#    ]
#    ),
#provided.PLAYERX)
#print mm_move(
#    provided.TTTBoard(
#        3, False, [
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERX], 
#    [provided.PLAYERO, provided.PLAYERX, provided.EMPTY], 
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERX]
#    ]
#    ),
#provided.PLAYERX)
#print mm_move(
#    provided.TTTBoard(
#        3, False, [
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERX], 
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERO], 
#    [provided.PLAYERX, provided.PLAYERO, provided.PLAYERX]
#    ]
#    ),
#provided.PLAYERX)
#print mm_move(
#    provided.TTTBoard(
#        3, False, [
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERX], 
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERO], 
#    [provided.EMPTY, provided.PLAYERO, provided.PLAYERX]
#    ]
#    ),
#provided.PLAYERX)
#print mm_move(
#    provided.TTTBoard(
#        3, False, [
#    [provided.PLAYERO, provided.PLAYERX, provided.PLAYERX], 
#    [provided.PLAYERO, provided.PLAYERX, provided.EMPTY], 
#    [provided.EMPTY, provided.PLAYERO, provided.PLAYERX]
#    ]
#    ),
#provided.PLAYERO)