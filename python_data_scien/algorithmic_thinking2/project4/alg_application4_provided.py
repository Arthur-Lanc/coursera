"""
Provide code and solution for Application 4
"""

DESKTOP = True

import math
import random
import urllib2
from collections import defaultdict

if DESKTOP:
    import matplotlib.pyplot as plt
    import alg_project4_solution as student
else:
    import simpleplot
    import userXX_XXXXXXX as student
    

# URLs for data files
PAM50_URL = "http://storage.googleapis.com/codeskulptor-alg/alg_PAM50.txt"
HUMAN_EYELESS_URL = "http://storage.googleapis.com/codeskulptor-alg/alg_HumanEyelessProtein.txt"
FRUITFLY_EYELESS_URL = "http://storage.googleapis.com/codeskulptor-alg/alg_FruitflyEyelessProtein.txt"
CONSENSUS_PAX_URL = "http://storage.googleapis.com/codeskulptor-alg/alg_ConsensusPAXDomain.txt"
WORD_LIST_URL = "http://storage.googleapis.com/codeskulptor-assets/assets_scrabble_words3.txt"



###############################################
# provided code

def read_scoring_matrix(filename):
    """
    Read a scoring matrix from the file named filename.  

    Argument:
    filename -- name of file containing a scoring matrix

    Returns:
    A dictionary of dictionaries mapping X and Y characters to scores
    """
    scoring_dict = {}
    scoring_file = urllib2.urlopen(filename)
    ykeys = scoring_file.readline()
    ykeychars = ykeys.split()
    for line in scoring_file.readlines():
        vals = line.split()
        xkey = vals.pop(0)
        scoring_dict[xkey] = {}
        for ykey, val in zip(ykeychars, vals):
            scoring_dict[xkey][ykey] = int(val)
    return scoring_dict


def read_protein(filename):
    """
    Read a protein sequence from the file named filename.

    Arguments:
    filename -- name of file containing a protein sequence

    Returns:
    A string representing the protein
    """
    protein_file = urllib2.urlopen(filename)
    protein_seq = protein_file.read()
    protein_seq = protein_seq.rstrip()
    return protein_seq


def read_words(filename):
    """
    Load word list from the file named filename.

    Returns a list of strings.
    """
    # load assets
    word_file = urllib2.urlopen(filename)
    
    # read in files as string
    words = word_file.read()
    
    # template lines and solution lines list of line string
    word_list = words.split('\n')
    print "Loaded a dictionary with", len(word_list), "words"
    return word_list

def compute_percentage(str1,str2):
    len_n = len(str1)
    same_num = 0
    for idx in range(len_n):
        if str1[idx] == str2[idx]:
            same_num += 1
    return float(same_num)/float(len_n)

def random_seq(seq_len,symbol_list):
    result_list = []
    for i in range(seq_len):
        result_list.append(random.choice(symbol_list))
    result_str = ''.join(result_list)
    return result_str

def max_score(alignment_matrix):
    max_value = float("-inf")
    for row_idx, row in enumerate(alignment_matrix):
        for col_idx, col in enumerate(row):
            if col > max_value:
                max_value = col
                max_index = (row_idx, col_idx)
    score = max_value
    index_i,index_j = max_index
    return score

def generate_null_distribution(seq_x, seq_y, scoring_matrix, num_trials):
    scoring_distribution = defaultdict(int)
    seq_y_list = list(seq_y)
    for i in range(num_trials):
        random.shuffle(seq_y_list)
        rand_y = ''.join(seq_y_list)
        alignment_matrix = student.compute_alignment_matrix(seq_x, rand_y, scoring_matrix, False)
        score = max_score(alignment_matrix)
        scoring_distribution[score] += 1
    return dict(scoring_distribution)

def check_spelling(checked_word, dist, word_list):
    result_set = set()
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    diag_score = 2
    off_diag_score = 1
    dash_score = 0
    scoring_matrix = student.build_scoring_matrix(set(alphabet), diag_score, off_diag_score, dash_score)

    len_x = len(checked_word)
    for word in word_list:
        len_y = len(word)
        alignment_matrix = student.compute_alignment_matrix(checked_word, word, scoring_matrix, True)
        score = alignment_matrix[len_x][len_y]
        edit_distance = len_x + len_y - score
        if edit_distance <= dist:
            result_set.add(word)
    return result_set



scoring_matrix = read_scoring_matrix(PAM50_URL)
human_protein_seq = read_protein(HUMAN_EYELESS_URL)
fruitfly_protein_seq = read_protein(FRUITFLY_EYELESS_URL)
alignment_matrix = student.compute_alignment_matrix(human_protein_seq, fruitfly_protein_seq, scoring_matrix, False)
local_alignment_tuple = student.compute_local_alignment(human_protein_seq, fruitfly_protein_seq, scoring_matrix, alignment_matrix)
#print local_alignment_tuple


consensus_pax_seq = read_protein(CONSENSUS_PAX_URL)
human_protein_align = local_alignment_tuple[1]
fruitfly_protein_align = local_alignment_tuple[2]
human_protein_align = human_protein_align.replace("-","")
fruitfly_protein_align = fruitfly_protein_align.replace("-","")
alignment_matrix1 = student.compute_alignment_matrix(human_protein_align, consensus_pax_seq, scoring_matrix, True)
alignment_matrix2 = student.compute_alignment_matrix(fruitfly_protein_align, consensus_pax_seq, scoring_matrix, True)
global_alignment_tuple1 = student.compute_global_alignment(human_protein_align, consensus_pax_seq, scoring_matrix, alignment_matrix1)
global_alignment_tuple2 = student.compute_global_alignment(fruitfly_protein_align, consensus_pax_seq, scoring_matrix, alignment_matrix2)
percentage1 = compute_percentage(global_alignment_tuple1[1],global_alignment_tuple1[2])
percentage2 = compute_percentage(global_alignment_tuple2[1],global_alignment_tuple2[2])
# print percentage1
# print percentage2


# amino_acid_symbol = list("ACBEDGFIHKMLNQPSRTWVYXZ")
# human_protein_seq_rdm = random_seq(len(human_protein_seq),amino_acid_symbol)
# fruitfly_protein_seq_rdm = random_seq(len(fruitfly_protein_seq),amino_acid_symbol)
# print human_protein_seq_rdm
# print fruitfly_protein_seq_rdm
# alignment_matrix = student.compute_alignment_matrix(human_protein_seq_rdm, fruitfly_protein_seq_rdm, scoring_matrix, False)
# local_alignment_tuple = student.compute_local_alignment(human_protein_seq_rdm, fruitfly_protein_seq_rdm, scoring_matrix, alignment_matrix)
# print local_alignment_tuple
# human_protein_align = local_alignment_tuple[1]
# fruitfly_protein_align = local_alignment_tuple[2]
# human_protein_align = human_protein_align.replace("-","")
# fruitfly_protein_align = fruitfly_protein_align.replace("-","")
# alignment_matrix1 = student.compute_alignment_matrix(human_protein_align, consensus_pax_seq, scoring_matrix, True)
# alignment_matrix2 = student.compute_alignment_matrix(fruitfly_protein_align, consensus_pax_seq, scoring_matrix, True)
# global_alignment_tuple1 = student.compute_global_alignment(human_protein_align, consensus_pax_seq, scoring_matrix, alignment_matrix1)
# global_alignment_tuple2 = student.compute_global_alignment(fruitfly_protein_align, consensus_pax_seq, scoring_matrix, alignment_matrix2)
# percentage1 = compute_percentage(global_alignment_tuple1[1],global_alignment_tuple1[2])
# percentage2 = compute_percentage(global_alignment_tuple2[1],global_alignment_tuple2[2])
# print percentage1
# print percentage2

# num_trials = 1000
# gnd = generate_null_distribution(human_protein_seq, fruitfly_protein_seq, scoring_matrix, num_trials)
# print gnd
gnd = {37: 1, 40: 8, 41: 15, 42: 17, 43: 28, 44: 45, 45: 54, 46: 57, 47: 41, 48: 77, 49: 83, 50: 69, 51: 58, 52: 51, 53: 65, 54: 55, 55: 40, 56: 34, 57: 38, 58: 39, 59: 20, 60: 13, 61: 18, 62: 13, 63: 10, 64: 8, 65: 10, 66: 4, 67: 3, 68: 4, 69: 4, 70: 4, 71: 4, 73: 3, 74: 2, 75: 2, 76: 2, 82: 1}
gnd_item_list = gnd.items()
gnd_item_list.sort(key = lambda item: item[0])
xvals = []
yvals = []
for key,value in gnd_item_list:
    xvals.append(key)
    yvals.append(float(value)/float(num_trials))
plt.bar(xvals, yvals, align='center', label='generate_null_distribution')
plt.legend(loc='upper right')
plt.xlabel('score')
plt.ylabel('the fraction of total trials')
plt.title('a distribution with 1000 trials(desktop Python)')
plt.grid(True)
plt.show()


# gnd_item_list_refact = []
# for item_tuple in gnd_item_list:
#     element = item_tuple[0]
#     times = item_tuple[1]
#     for i in range(times):
#         gnd_item_list_refact.append(element)
# temp_score = 0
# for item in gnd_item_list_refact:
#     temp_score += item
# miu = float(temp_score)/float(num_trials)
# print miu
# temp_sum = 0
# for item in gnd_item_list_refact:
#     temp_sum += (item - miu)**2
# sigma = math.sqrt(float(temp_sum)/float(num_trials))
# print sigma
# z = (875-miu)/float(sigma)
# print z


# word_list = read_words(WORD_LIST_URL)
# #print word_list
# cs1 = check_spelling('humble', 1, word_list)
# cs2 = check_spelling('firefly', 2, word_list)
# print cs1
# print cs2

###################################################################################################
# #question1
# the score of the local alignment: 875
# local alignment of the sequences of HumanEyelessProtein: 'HSGVNQLGGVFVNGRPLPDSTRQKIVELAHSGARPCDISRILQVSNGCVSKILGRYYETGSIRPRAIGGSKPRVATPEVVSKIAQYKRECPSIFAWEIRDRLLSEGVCTNDNIPSVSSINRVLRNLASEK-QQ'
# local alignment of the sequences of FruitflyEyelessProtein: 'HSGVNQLGGVFVGGRPLPDSTRQKIVELAHSGARPCDISRILQVSNGCVSKILGRYYETGSIRPRAIGGSKPRVATAEVVSKISQYKRECPSIFAWEIRDRLLQENVCTNDNIPSVSSINRVLRNLAAQKEQQ'

# #question2
# the global alignments of local human vs. consensus PAX domain: 0.729
# the global alignments of local fruitfly vs. consensus PAX domain: 0.701

# #question3
# it is not due to chance
# when i comparing two random sequences of amino acids of length similar to that of HumanEyelessProtein and FruitflyEyelessProtein,
# i got the score of the local alignment: 51
# local alignment of the random sequence 1: 'HMLBWLCW-D-MHXXHY'
# local alignment of the random sequence 2: 'QMFBWL-WNDGKH-RHF'
# the global alignments of local random sequence 1 vs. consensus PAX domain: 0.0480
# the global alignments of local random sequence 2 vs. consensus PAX domain: 0.0640

# #question4
# # image

# #question5
# mean: 51.603
# standard deviation: 6.470192501
# z-score: 127.260046725

# #question6
# It's not due to chance.
# As we can see the score range from question 4 is [37,82], but we got the score of the local alignment is 875, 
# so it is not fall within three multiples of the standard deviation for such distributions,
# which means that it is not due to chance.

# #question7
# diag_score: 2
# off_diag_score: 1
# dash_score: 0

# #question8
# the set of words within an edit distance of one from the string "humble": 
# set(['bumble', 'humbled', 'tumble', 'humble', 'rumble', 'humbler', 'humbles', 'fumble', 'humbly', 'jumble', 'mumble'])
# the set of words within an edit distance of two from the string "firefly" : 
# set(['firefly', 'tiredly', 'freely', 'fireclay', 'direly', 'finely', 'firstly', 'liefly', 'fixedly', 'refly', 'firmly'])

###################################################################################################
#test
#print compute_percentage('acdf','acef')