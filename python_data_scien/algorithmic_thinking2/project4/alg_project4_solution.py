'''
project4
'''

def build_scoring_matrix(alphabet, diag_score, off_diag_score, dash_score):
    '''
    build_scoring_matrix
    '''
    alphabet_list = list(alphabet.union(set(['-'])))
    scoring_matrix = dict()
    for index_i in alphabet_list:
        temp_dict = dict()
        for index_j in alphabet_list:
            if index_i == '-' or index_j == '-':
                temp_dict[index_j] = dash_score
            elif index_i == index_j:
                temp_dict[index_j] = diag_score
            else:
                temp_dict[index_j] = off_diag_score
        scoring_matrix[index_i] = temp_dict
    return scoring_matrix

def compute_alignment_matrix(seq_x, seq_y, scoring_matrix, global_flag):
    '''
    compute_alignment_matrix
    '''
    len_m = len(seq_x)
    len_n = len(seq_y)
    alignment_matrix = [[0 for dummy_col in range(len_n+1)] for dummy_row in range(len_m+1)]
    if global_flag == True:
        for index_i in range(1,len_m+1):
            alignment_matrix[index_i][0] = alignment_matrix[index_i-1][0] + scoring_matrix[seq_x[index_i-1]]['-']
        for index_j in range(1,len_n+1):
            alignment_matrix[0][index_j] = alignment_matrix[0][index_j-1] + scoring_matrix['-'][seq_y[index_j-1]]
        for index_i in range(1,len_m+1):
            for index_j in range(1,len_n+1):
                alignment_matrix[index_i][index_j] = max(
                    alignment_matrix[index_i-1][index_j-1] + scoring_matrix[seq_x[index_i-1]][seq_y[index_j-1]],
                    alignment_matrix[index_i-1][index_j] + scoring_matrix[seq_x[index_i-1]]['-'],
                    alignment_matrix[index_i][index_j-1] + scoring_matrix['-'][seq_y[index_j-1]]
                    )
    else:
        for index_i in range(1,len_m+1):
            alignment_matrix[index_i][0] = max(0,alignment_matrix[index_i-1][0] + scoring_matrix[seq_x[index_i-1]]['-'])
        for index_j in range(1,len_n+1):
            alignment_matrix[0][index_j] = max(0,alignment_matrix[0][index_j-1] + scoring_matrix['-'][seq_y[index_j-1]])
        for index_i in range(1,len_m+1):
            for index_j in range(1,len_n+1):
                alignment_matrix[index_i][index_j] = max(
                    0,
                    alignment_matrix[index_i-1][index_j-1] + scoring_matrix[seq_x[index_i-1]][seq_y[index_j-1]],
                    alignment_matrix[index_i-1][index_j] + scoring_matrix[seq_x[index_i-1]]['-'],
                    alignment_matrix[index_i][index_j-1] + scoring_matrix['-'][seq_y[index_j-1]]
                    )
    return alignment_matrix


def compute_global_alignment(seq_x, seq_y, scoring_matrix, alignment_matrix):
    '''
    compute_global_alignment
    '''
    score = alignment_matrix[len(seq_x)][len(seq_y)]
    index_i = len(seq_x)
    index_j = len(seq_y)

    align_x = ''
    align_y = ''
    while index_i != 0 and index_j != 0:
        if alignment_matrix[index_i][index_j] == alignment_matrix[index_i-1][index_j-1] + scoring_matrix[seq_x[index_i-1]][seq_y[index_j-1]]:
            align_x = seq_x[index_i-1] + align_x
            align_y = seq_y[index_j-1] + align_y
            index_i = index_i - 1
            index_j = index_j - 1
        else:
            if alignment_matrix[index_i][index_j] == alignment_matrix[index_i-1][index_j] + scoring_matrix[seq_x[index_i-1]]['-']:
                align_x = seq_x[index_i-1] + align_x
                align_y = '-' + align_y
                index_i = index_i - 1
            else:
                align_x = '-' + align_x
                align_y = seq_y[index_j-1] + align_y
                index_j = index_j - 1
    while index_i != 0:
        align_x = seq_x[index_i-1] + align_x
        align_y = '-' + align_y
        index_i = index_i - 1
    while index_j != 0:
        align_x = '-' + align_x
        align_y = seq_y[index_j-1] + align_y
        index_j = index_j - 1
    return (score,align_x,align_y)

def compute_local_alignment(seq_x, seq_y, scoring_matrix, alignment_matrix):
    '''
    compute_local_alignment
    '''
    max_value = float("-inf")
    for row_idx, row in enumerate(alignment_matrix):
        for col_idx, col in enumerate(row):
            if col > max_value:
                max_value = col
                max_index = (row_idx, col_idx)
    score = max_value
    index_i,index_j = max_index

    align_x = ''
    align_y = ''
    while index_i != 0 and index_j != 0:
        if alignment_matrix[index_i][index_j] == alignment_matrix[index_i-1][index_j-1] + scoring_matrix[seq_x[index_i-1]][seq_y[index_j-1]]:
            align_x = seq_x[index_i-1] + align_x
            align_y = seq_y[index_j-1] + align_y
            index_i = index_i - 1
            index_j = index_j - 1
        else:
            if alignment_matrix[index_i][index_j] == alignment_matrix[index_i-1][index_j] + scoring_matrix[seq_x[index_i-1]]['-']:
                align_x = seq_x[index_i-1] + align_x
                align_y = '-' + align_y
                index_i = index_i - 1
            else:
                align_x = '-' + align_x
                align_y = seq_y[index_j-1] + align_y
                index_j = index_j - 1
        if alignment_matrix[index_i][index_j] == 0:
            return (score,align_x,align_y)
    while index_i != 0:
        align_x = seq_x[index_i-1] + align_x
        align_y = '-' + align_y
        index_i = index_i - 1
        if alignment_matrix[index_i][index_j] == 0:
            return (score,align_x,align_y)
    while index_j != 0:
        align_x = '-' + align_x
        align_y = seq_y[index_j-1] + align_y
        index_j = index_j - 1
        if alignment_matrix[index_i][index_j] == 0:
            return (score,align_x,align_y)
    return (score,align_x,align_y)


##################################################
#print build_scoring_matrix(set(['a','t','c','g']), 4, 1, -2)
# bsm = build_scoring_matrix(set(['a','t','c','g']), 4, 1, -2)
# s = compute_alignment_matrix('cga', 'actgac', bsm, True)
# # print compute_alignment_matrix('cga', 'actgac', bsm, False)
# print compute_global_alignment('cga', 'actgac', bsm, s)
# print compute_local_alignment('cga', 'actgac', bsm, s)

# bsm = build_scoring_matrix(set(['a','t','c','g']), 4, 1, -2)
# s = compute_alignment_matrix('', '', bsm, True)
# print s
# print compute_global_alignment('', '', bsm, s)
# print compute_local_alignment('', '', bsm, s)

# compute_local_alignment('', '', {'A': {'A': 6, 'C': 2, '-': -4, 'T': 2, 'G': 2}, 'C': {'A': 2, 'C': 6, '-': -4, 'T': 2, 'G': 2}, '-': {'A': -4, 'C': -4, '-': -4, 'T': -4, 'G': -4}, 'T': {'A': 2, 'C': 2, '-': -4, 'T': 6, 'G': 2}, 'G': {'A': 2, 'C': 2, '-': -4, 'T': 2, 'G': 6}}, [[0]]) 
# expected ({'A': {'A': 6, 'C': 2, '-': -4, 'T': 2, 'G': 2}, 'C': {'A': 2, 'C': 6, '-': -4, 'T': 2, 'G': 2}, '-': {'A': -4, 'C': -4, '-': -4, 'T': -4, 'G': -4}, 'T': {'A': 2, 'C': 2, '-': -4, 'T': 6, 'G': 2}, 'G': {'A': 2, 'C': 2, '-': -4, 'T': 2, 'G': 6}}, 0, '', '', False)