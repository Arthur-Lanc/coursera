'''
Project #2 Description
'''
from collections import deque

GRAPH2 = {1: set([2, 4, 6, 8]),
          2: set([1, 3, 5, 7]),
          3: set([2, 4, 6, 8]),
          4: set([1, 3, 5, 7]),
          5: set([2, 4, 6, 8]),
          6: set([1, 3, 5, 7]),
          7: set([2, 4, 6, 8]),
          8: set([1, 3, 5, 7])}

EX_GRAPH0 = {
                0: set([1,2]),
                1: set([0]),
                2: set([0,4]),
                3: set([]),
                4: set([2])
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

def bfs_visited(ugraph, start_node):
    '''
    bfs_visited
    '''
    que = deque()
    visited = {start_node}
    que.append(start_node)
    while len(que) != 0:
        temp_node = que.popleft()
        for neighbour_node in ugraph[temp_node]:
            if neighbour_node not in visited:
                visited.add(neighbour_node)
                que.append(neighbour_node)
    return visited

def cc_visited(ugraph):
    '''
    cc_visited
    '''
    remaining_nodes = set(ugraph.keys())
    cc_list = list()
    while len(remaining_nodes) != 0:
        start_node = remaining_nodes.pop()
        visited = bfs_visited(ugraph, start_node)
        cc_list.append(visited)
        remaining_nodes.difference_update(visited)
    return cc_list

def largest_cc_size(ugraph):
    '''
    largest_cc_size
    '''
    cc_list = cc_visited(ugraph)
    max_size = 0
    for idx in range(len(cc_list)):
        item = cc_list[idx]
        if idx == 0:
            max_size = len(item)
        else:
            if len(item) > max_size:
                max_size = len(item)
    return max_size

def compute_resilience(ugraph, attack_order):
    '''
    compute_resilience
    '''
    ugraph_copy = ugraph.copy()
    result_list = list()
    result_list.append(largest_cc_size(ugraph_copy))
    for idx in range(len(attack_order)):
        item = attack_order[idx]

        neighbors = ugraph_copy[item]
        ugraph_copy.pop(item)
        for neighbor in neighbors:
            ugraph_copy[neighbor].remove(item)

        # ugraph_copy.pop(item)
        # for key,value in ugraph_copy.items():
        #     value.discard(item)
        #     ugraph_copy[key] = value

        result_list.append(largest_cc_size(ugraph_copy))
    return result_list

####################################
# #####test on bfs_visited(ugraph, i)
# print('test on bfs_visited(ugraph, i)')
# print(bfs_visited(GRAPH2, 1))
# #####test on cc_visited(ugraph)
# print('test on cc_visited(ugraph)')
# print(cc_visited(GRAPH2))
# #####test on largest_cc_size(ugraph)
# print('test on largest_cc_size(ugraph)')
# print(largest_cc_size(GRAPH2))
# #####test on compute_resilience(ugraph, attack_order)
# print('test on compute_resilience(ugraph, attack_order)')
# print(compute_resilience(GRAPH2, [1, 3, 5, 7, 2, 4, 6, 8]))