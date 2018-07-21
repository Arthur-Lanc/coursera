#http://www.codeskulptor.org/#user43_FXzSETfCbY_6.py

"""
Planner for Yahtzee
Simplifications:  only allow discard and roll, only score against upper level
"""

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(20)

def gen_all_sequences(outcomes, length):
    """
    Iterative function that enumerates the set of all sequences of
    outcomes of given length.
    """
    
    answer_set = set([()])
    for dummy_idx in range(length):
        temp_set = set()
        for partial_sequence in answer_set:
            for item in outcomes:
                new_sequence = list(partial_sequence)
                new_sequence.append(item)
                temp_set.add(tuple(new_sequence))
        answer_set = temp_set
    return answer_set


def score(hand):
    """
    Compute the maximal score for a Yahtzee hand according to the
    upper section of the Yahtzee score card.

    hand: full yahtzee hand

    Returns an integer score 
    """
    max_score = 0
    sorted_hand_list = sorted(list(set(hand)))
    for i_mem in sorted_hand_list:
        temp_score = 0
        for j_mem in list(hand):
            if i_mem == j_mem:
                temp_score += i_mem
        if temp_score > max_score:
            max_score = temp_score
    return max_score


def expected_value(held_dice, num_die_sides, num_free_dice):
    """
    Compute the expected value based on held_dice given that there
    are num_free_dice to be rolled, each with num_die_sides.

    held_dice: dice that you will hold
    num_die_sides: number of sides on each die
    num_free_dice: number of dice to be rolled

    Returns a floating point expected value
    """
    all_sequences_set = gen_all_sequences(range(1,num_die_sides+1), num_free_dice)
    all_sequences_list = list(all_sequences_set)
    possible_event_list = []
    for i_tuple in all_sequences_list:
        temp_held_dice_list = list(held_dice)
        temp_held_dice_list.extend(list(i_tuple))
        possible_event_list.append(tuple(temp_held_dice_list))
    #print 'possible_event_list:',str(possible_event_list)
    score_event_list = []
    for j_tuple in possible_event_list:
        score_int = score(j_tuple)
        score_event_list.append(score_int)
    #print 'score_event_list:',str(score_event_list)
    score_event_len = len(score_event_list)
    expected_value_float = 0.0
    for k_int in sorted(list(set(score_event_list))):
        expected_value_float += k_int*score_event_list.count(k_int)/float(score_event_len)

    #print 'expected_value_float:',str(expected_value_float)
    return expected_value_float

def get_diff_list(source_list,i_tuple):
    '''get_diff_list'''
    diff_list = list(source_list)
    i_list = list(i_tuple)
    for item in i_list:
        diff_list.remove(item)
    return diff_list

def gen_all_holds(hand):
    """
    Generate all possible choices of dice from hand to hold.

    hand: full yahtzee hand

    Returns a set of tuples, where each tuple is dice to hold
    """
    sorted_hand_list = sorted(list(hand))
    sorted_hand_len = len(sorted_hand_list)
    answer_set = set([])
    for i_idx in range(sorted_hand_len+1):
        if i_idx == 0:
            answer_set.add(tuple())
        elif i_idx == sorted_hand_len:
            answer_set.add(tuple(sorted_hand_list))
        else:
            temp_set = set()
            partial_answer_set = set([a for a in answer_set if len(a) == i_idx-1])
            for j_tuple in partial_answer_set:
                diff_list = get_diff_list(sorted_hand_list,j_tuple)
                for k_mem in diff_list:
                    j_list = list(j_tuple)
                    j_list.append(k_mem)
                    temp_set.add(tuple(j_list))
            answer_set.update(temp_set)
            #print '2:',answer_set
    sorted_answer_set = set([])
    for x_tuple in answer_set:
        sorted_x_tuple = tuple(sorted(list(x_tuple)))
        sorted_answer_set.add(sorted_x_tuple)
    return sorted_answer_set



def strategy(hand, num_die_sides):
    """
    Compute the hold that maximizes the expected value when the
    discarded dice are rolled.

    hand: full yahtzee hand
    num_die_sides: number of sides on each die

    Returns a tuple where the first element is the expected score and
    the second element is a tuple of the dice to hold
    """
    all_possible_holds_set = gen_all_holds(hand)
    all_possible_holds_list = list(all_possible_holds_set)
    #print 'all_possible_holds_list:',all_possible_holds_list
    max_e_v = 0.0
    for idx in range(len(all_possible_holds_list)):
        i_tuple = all_possible_holds_list[idx]
        held_dice = i_tuple
        num_free_dice = len(hand) - len(i_tuple)
        e_v = expected_value(held_dice, num_die_sides, num_free_dice)
        if idx == 0:
            max_e_v = e_v
            best_held_tuple = i_tuple
        else:
            if e_v > max_e_v:
                max_e_v = e_v
                best_held_tuple = i_tuple 
                   
    return (max_e_v, best_held_tuple)


def run_example():
    """
    Compute the dice to hold and expected score for an example hand
    """
    num_die_sides = 6
    hand = (1,3,4,5,5)
    hand_score, hold = strategy(hand, num_die_sides)
    print "Best strategy for hand", hand, "is to hold", hold, "with expected score", hand_score 
    
#run_example()


#import poc_holds_testsuite
#poc_holds_testsuite.run_suite(gen_all_holds)
                                       
    
    
########################################
### func1:gen_all_sequences
#print 'func1:gen_all_sequences'
#outcomes = [1,2,]
#length = 2
#print gen_all_sequences(outcomes,length)
### func2:get_diff_list
#print 'func2:get_diff_list'
#source_list = [1,2,2,3]
#i_tuple = (2,3)
#print get_diff_list(source_list,i_tuple)
### func2:score(hand)
#print 'func2:score(hand)'
#hand1 = (1,4,4,3,2)
#print score(hand1)
#hand2 = (1, 1, 1, 5, 6)
#print score(hand2)
### func2:expected_value
#print 'func2:expected_value'
#held_dice = (1,2,3)
#num_die_sides = 6
#num_free_dice = 5-len(held_dice)
#print expected_value(held_dice, num_die_sides, num_free_dice)
########################################


