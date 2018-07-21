'''
degree distributions for graphs
'''
from collections import Counter


EX_GRAPH0 = {
                0: set([1,2]),
                1: set([]),
                2: set([])
            }
EX_GRAPH1 = {
                0: set([1,4,5]),
                1: set([2,6]),
                2: set([3]),
                3: set([0]),
                4: set([1]),
                5: set([2]),
                6: set([])
            }
EX_GRAPH2 = {
                0: set([1,4,5]),
                1: set([2,6]),
                2: set([3,7]),
                3: set([7]),
                4: set([1]),
                5: set([2]),
                6: set([]),
                7: set([3]),
                8: set([1,2]),
                9: set([0,3,4,5,6,7])
            }

def make_complete_graph(num_nodes):
    '''
    make_complete_graph
    '''
    result_dic = {}
    if num_nodes > 0:
        for i_item in range(num_nodes):
            temp_list = []
            for j_item in range(num_nodes):
                if i_item != j_item:
                    temp_list.append(j_item)
            result_dic[i_item] = set(temp_list)
        return result_dic
    else:
        result_dic = {}
        return result_dic

# def compute_in_degrees(digraph):
#     '''
#     compute_in_degrees
#     '''
#     result_dic = {}
#     dig_key_list = digraph.keys()
#     digraph_list = digraph.items()
#     for key_item in dig_key_list:
#         in_degree_num = 0
#         for item in range(len(digraph_list)):
#             #key = digraph_list[item][0]
#             value_set = digraph_list[item][1]
#             value_list = list(value_set)
#             if key_item in value_list:
#                 in_degree_num += 1
#         result_dic[key_item] = in_degree_num
#     return result_dic

def compute_in_degrees(digraph):
    '''
    compute_in_degrees
    '''
    result_dic = {}
    dig_key_list = digraph.keys()
    dig_val_list = digraph.values()
    dig_val_list2 = []
    for item1 in dig_val_list:
        dig_val_list2.extend(list(item1))
    dig_val_list_c_d = dict(Counter(dig_val_list2))
    for key_item in dig_key_list:
        if key_item in dig_val_list_c_d:
            result_dic[key_item] = dig_val_list_c_d[key_item]
        else:
            result_dic[key_item] = 0
    return result_dic

# def in_degree_distribution(digraph):
#     '''
#     in_degree_distribution
#     '''
#     result_dic = {}
#     comp_in_deg_dic = compute_in_degrees(digraph)
#     key_list = sorted(list(set(comp_in_deg_dic.values())))
#     for key_item in key_list:
#         num_of_nodes = 0
#         for dummy_key,value in comp_in_deg_dic.items():
#             if value == key_item:
#                 num_of_nodes += 1
#         result_dic[key_item] = num_of_nodes
#     return result_dic

def in_degree_distribution(digraph):
    '''
    in_degree_distribution
    '''
    result_dic = {}
    comp_in_deg_dic = compute_in_degrees(digraph)
    cidd_list = comp_in_deg_dic.values()
    cidd_list_c_d = dict(Counter(cidd_list))
    key_list = sorted(list(set(cidd_list)))
    for key_item in key_list:
        result_dic[key_item] = cidd_list_c_d[key_item]
    return result_dic


####################################
#####test on make_complete_graph(num_nodes)
# print 'test on make_complete_graph(num_nodes)'
# result_dic = make_complete_graph(3)
# print result_dic
# print make_complete_graph(4)
# print make_complete_graph(1)
# print make_complete_graph(-1)
# #####test on compute_in_degrees(digraph)
# print 'test on compute_in_degrees(digraph)'
# print compute_in_degrees(EX_GRAPH0)
# print compute_in_degrees(EX_GRAPH1)
# #####test on in_degree_distribution(digraph)
# print 'test on in_degree_distribution(digraph)'
# print in_degree_distribution(EX_GRAPH0)
# print in_degree_distribution(EX_GRAPH1)